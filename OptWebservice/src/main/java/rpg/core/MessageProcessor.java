package rpg.core;

/*
 * Created on Jun 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;



/**
 * @author fernandez
 */
public class MessageProcessor {

    private final static Logger log = Logger.getLogger(MessageProcessor.class.getName());

    private MessageHandler mh;
    private MessageRecord message;

    /**
     *
     */
    public MessageProcessor(MessageHandler mh) {
        super();
        this.mh = mh;
    }

    /**
     *
     */
    public MessageProcessor(MessageContext mc) {
        super();
        this.mh = mc.getHandler();
    }

    /**
     *
     */
    public MessageProcessor(String key) throws IOException {
        super();
        this.mh = getMessageHandler(key);
    }

    /**
     * This method returns the Program Name used by the Message Header
     * in the sockets protocol.
     *
     * @return the formatname of the MessageRecord class, as a String.
     *
     */
    public final static String getProgramName(String formatname) {
        String result = "";
        if (formatname != null && formatname.length() > 7) {
            result = formatname.substring(0, 7);
        } else if (formatname != null) {
            result = formatname;
        }
        return result;
    }

    /**
     * This method returns the Time Stamp String used by the Header Message
     * in the sockets protocol.
     *
     * @return TimeStamp, as a String, in this very moment.
     *
     */
    private final static String getTimeStamp() {

        Calendar calendar = Calendar.getInstance();
        String year = "" + calendar.get(Calendar.YEAR);
        String month = "" + (calendar.get(Calendar.MONTH) + 1);
        String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
        String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);
        String minute = "" + calendar.get(Calendar.MINUTE);
        String second = "" + calendar.get(Calendar.SECOND);

        year = year.substring(2);
        month = month.length() == 1 ? "0" + month : month;
        day = day.length() == 1 ? "0" + day : day;
        hour = hour.length() == 1 ? "0" + hour : hour;
        minute = minute.length() == 1 ? "0" + minute : minute;
        second = second.length() == 1 ? "0" + second : second;

        return (year + month + day + hour + minute + second);

    }

    /**
     * @return
     */
    public MessageHandler getMessageHandler() {
        return mh;
    }

    public MessageHandler getMessageHandler(String key) throws IOException {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        MessageHandler mh = null;
        String host = serviceLocator.getSocketIp(key);
        int port = serviceLocator.getSocketPort(key);
        int timeout = serviceLocator.getSocketTimeOut(key);
        String packageName = serviceLocator.getSocketMsgPackage(key);
        String routerClass = serviceLocator.getSocketMsgRouter(key);
        try {
            MessageRouter router = (MessageRouter) Class.forName(routerClass)
                    .getConstructor(new Class[] { String.class, int.class, int.class })
                    .newInstance(new Object[] { host, new Integer(port), new Integer(timeout) });
            mh = new MessageHandler(router, packageName);
            return mh;
        } catch (Exception e) {
            if (mh == null) {
                throw new IOException("Socket not Open(Host = " + host + ", Port " + port + "). " +
                        "Error: " + e.toString());
            } else {
                throw new IOException(mh.toString() +
                        "Error: " + e.toString());
            }
        }
    }

    public MessageRecord getMessageRecord(String messageName) {
        MessageRecord message = null;
        try {
            message = mh.getMessageRecord(messageName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing " + messageName);
        }
        return message;
    }

    public MessageRecord getMessageRecord(
            String messageName,
            String user,
            String opCode) {
        MessageRecord message = null;
        try {
            message = getMessageRecord(messageName);
            java.util.Enumeration enu = message.fieldEnumeration();
            MessageField field = null;
            String value = null;
            while (enu.hasMoreElements()) {
                field = (MessageField) enu.nextElement();

                if (field.getTag().indexOf("USERID") >= 0) {
                    field.setString(user);
                } else if (field.getTag().indexOf("PROGRM") >= 0) {
                    field.setString(getProgramName(message.getFormatName()));
                } else if (field.getTag().indexOf("TIMSYS") >= 0) {
                    field.setString(getTimeStamp());
                } else if (
                        opCode != "" && field.getTag().indexOf("OPECOD") >= 0) {
                    field.setString(opCode);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing " + messageName);
        }
        return message;
    }


    private void showERROR(MessageRecord m) {
        log("ERROR received.");
        int num = Integer.parseInt(m.getFieldString("ERRNUM"));
        log("ERROR number:" + num);
        for (int i = 1; i <= num; i++) {
            String sufix = i < 10 ? "0" + i : "" + i;
            log("ERR0" + sufix + " = " + m.getFieldString("ERNU" + sufix)
                    + " desc: " + m.getFieldString("ERDS" + sufix));
        }
    }

    /**
     * Receives a message from the current input stream.
     * @return the MessageRecord class.
     * @exception IOException
     * if there is any problem receiving the message.
     */
    public MessageRecord receiveMessageRecord()
            throws IOException {
        try {
            MessageRecord newmessage = mh.receiveMessage();
            if (message != null) {
                if (!newmessage.getFormatName().equals(message.getFormatName())) {
                    log(newmessage.getFormatName() + "Message received.");
                }
            } else {
                log(newmessage.getFormatName() + "Message received.");
            }
            message = newmessage;
            return newmessage;
        } catch (IOException e) {
            throw new IOException("On MessageHandler: " + mh.toString() + " : Cause By: " + e.toString());
        }
    }

    /**
     * Receives a message from the current input stream.
     * @return the MessageRecord class.
     * @exception IOException
     * if there is any problem receiving the message.
     */
    public MessageRecord receiveMessageRecord(String formatname)
            throws IOException, ClassCastException {

        MessageRecord newmessage = receiveMessageRecord();
        if (!newmessage.getFormatName().equals(formatname)) {
            throw new ClassCastException("Message " + newmessage.getFormatName() + " is not " + formatname);
        }
        return newmessage;
    }

    /**
     * Receive the a message list from the default DataInputStream for the
     * current MessageHandler.
     * @param controlField The EndOfList Control field
     * @param positionField The Position Field - Should be need it for paginated lists
     * (This should be the correct behavior)
     * @param formatname The Format name to be received, If specified
     * a ClassCastException it's thrown if the MessageRecord it's not of the specified format.
     * In case of an Error Message received a one record List with an error MessageRecord it's returned.
     * @param size
     * @return a List with MessageRecord objects as the objects List.
     * @throws IOException if there is an error reading a message object.
     */
    public JBObjList receiveMessageRecordList(
            String controlField,
            String positionField,
            String formatname,
            int size)
            throws IOException {

        JBObjList beanList = new JBObjList();
        int pos = 0;

        try {
            MessageRecord newmessage = mh.receiveMessage();
            if (newmessage.getFormatName().equals("ELEERR")) {
                MessageRecord firstMessage = newmessage;
                log(newmessage.getFormatName() + "Message received.");
                if(hasError(newmessage)){
                    beanList.addRow(firstMessage);
                    return beanList;
                } else {
                    newmessage = mh.receiveMessage();
                }
            }

            boolean firstTime = true;
            boolean paginated = false;
            if (positionField != null && !positionField.equals("")) {
                paginated = true;
            }
            String marker = "";
            int counter = size;
            while ((size == 0 ? true : counter-- > 0)) {
                if ((formatname == null || formatname.equals(""))
                        || newmessage.getFormatName().equals(formatname)) {
                    try {
                        marker = newmessage.getField(controlField).getString();
                    } catch (Exception e) {
                    }
                    if (paginated && !marker.equals("*")){
                        try {
                            pos = Integer.parseInt(newmessage.getField(positionField).getString());
                        } catch (Exception e) {
                            pos = 0;
                        }
                    }
                    if (firstTime) {
                        log(newmessage.getFormatName() + " was First Message received.");
                        firstTime = false;
                        if (paginated) beanList.setFirstRec(pos);
                    }
                    if (marker.equals("*")) {
                        log(newmessage.getFormatName() + " was Last Message received out of "+beanList.size());
                        beanList.setShowNext(false);
                        beanList.setLastRec(pos > beanList.getFirstRec() ? pos : (pos + beanList.size()));
                        break;
                    } else {
                        beanList.addRow(newmessage);
                        if (marker.equals("+")) {
                            log(newmessage.getFormatName() + " was Last Message received out of "+beanList.size());
                            beanList.setShowNext(true);
                            if (paginated)
                                beanList.setLastRec(pos > beanList.getFirstRec() ? pos : (pos + beanList.size()));
                            break;
                        }
                    }
                    newmessage = mh.getMessageRecord(newmessage.getFormatName());
                    newmessage = mh.receiveMessage();

                } else {
                    throw new ClassCastException("Message " + newmessage.getFormatName() + " is not " + formatname);
                }
            }
        } catch (IOException e) {
            throw new IOException("On MessageHandler: " + mh.toString() + " : Cause By: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }

    /**
     * Receive the a message list from the default DataInputStream for the
     * current MessageHandler.
     * @param controlField The EndOfList Control field
     * @param positionField The Position Field - Should be need it for paginated lists
     * (This should be the correct behavior)
     * @param size
     * @return a List with MessageRecord objects as the objects List.
     * @throws IOException if there is an error reading a message object.
     */
    public JBObjList receiveMessageRecordList(
            String controlField,
            String positionField,
            int size)
            throws IOException {
        return receiveMessageRecordList(controlField, positionField, null, size);
    }

    /**
     * Receive the a message list from the default DataInputStream for the
     * current MessageHandler.
     * @param controlField The EndOfList Control field
     * @param positionField The Position Field - Should be need it for paginated lists
     * (This should be the correct behavior)
     * @return a List with MessageRecord objects as the objects List.
     * @throws IOException if there is an error reading a message object.
     */
    public JBObjList receiveMessageRecordList(
            String controlField,
            String positionField)
            throws IOException {
        return receiveMessageRecordList(controlField, positionField, null, 1000);
    }

    /**
     * Receive the a message list from the default DataInputStream for the
     * current MessageHandler.
     * @param controlField The EndOfList Control field
     * (This should be the correct behavior)
     * @return a List with MessageRecord objects as the objects List.
     * @throws IOException if there is an error reading a message object.
     */
    public JBObjList receiveMessageRecordList(
            String controlField)
            throws IOException {
        return receiveMessageRecordList(controlField, null, null, 1000);
    }


    /**
     * Send a message over the current output stream.
     * @param message
     * an instance of a MessageRecord subclass.
     * @exception IOException
     * if there is any problem sending the message.
     */
    public void sendMessage(MessageRecord message)
            throws IOException {
        try {
            mh.sendMessage(message);
            message.destroy();
            log(message.getFormatName() + "Message Sent.");
        } catch (IOException e) {
            throw new IOException("On MessageHandler: " + mh.toString() + " : Cause By: " + e.toString());
        }
    }

    /**
     * Sends a list of MessageRecord subclasses
     */
    public void sendMessageList(
            List messageList,
            String formatname,
            String controlField,
            String user)
            throws IOException {

        try {
            MessageRecord message = null;
            Iterator iter = messageList.listIterator();
            while (iter.hasNext()) {
                message = (MessageRecord) iter.next();
                message.getField("H01USERID").setString(user);
                message.getField("H01PROGRM").setString(getProgramName(formatname));
                message.getField("H01TIMSYS").setString(getTimeStamp());
                //message.getField(controlField).setString("+");
                mh.sendMessage(message);
                log(message.getFormatName() + "Message Sent.");
            }
            message = getMessageRecord(formatname);
            message.getField("H01USERID").setString(user);
            message.getField("H01PROGRM").setString(getProgramName(formatname));
            message.getField("H01TIMSYS").setString(getTimeStamp());
            message.getField(controlField).setString("*");
            mh.sendMessage(message);
            message.destroy();
            log(message.getFormatName() + "Message Sent.");
        } catch (IOException e) {
            throw new IOException("On MessageHandler: " + mh.toString() + " : Cause By: " + e.toString());
        }

    }

    public MessageRecord getError(JBObjList list) {
        list.initRow();
        return list.getNextRow() ? (MessageRecord)list.getRecord() : null;
    }

    public boolean hasError(JBObjList list) {
        return hasError(getError(list));
    }

    public boolean hasError(MessageRecord message) {
        if (message != null && message.getFormatName().equals("ELEERR")) {
            boolean error = !message.getFieldString("ERRNUM").equals("0");
            if (error) {
                showERROR(message);
            }
            return error;
        } else {
            return false;
        }
    }

    public void close() throws IOException {
        message = null;
        if(mh != null) {
            mh.flush();
            mh.releaseMessageRouter().close();
        }
    }

    private void log(String message){
        log.info(message);
    }

}
