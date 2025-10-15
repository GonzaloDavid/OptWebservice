package rpg.core;


import java.io.*;
import java.util.*;

/**
 * The MessageRecord class is the abstract superclass of all of the
 * message objects created by the AS/400 CRTCLASS command.
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 5:07:39 PM)
 *
 * @author ?
 *
 * @tag    Date          Flag        Description
 */
public abstract class MessageRecord extends MessageMap implements Serializable
{
    final static String COPYRIGHT =  "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    MessageHandler myhandler = null;
    boolean isempty = false;
    Vector detail = new Vector();

    /** Format name set by the concrete subclass. */
    protected String formatname = null;

    /** Byte array to contain sent and received messages. */
    protected byte[] message = null;

    /** Byte array for empty message */
    protected byte[] emessage = null;

    /** Array of all the fields for this message. */
    protected MessageField[] fields = null;

    /** Array of all the field names for this message. */
    protected String[] fieldnames = null;

    /** Array of all the tag fields for this message. */
    protected String[] tagnames = null;

    /** Hashtable of tag names for fields. */
    protected Hashtable taglookup = null;

    /** Hashtable of fields names. */
    protected Hashtable namelookup = null;

    /** Size of message. */
    protected int recordsize = 0;

    /** File ID of generating file */
    protected String fileid = null;

    /** Record format level ID of generating file */
    protected String recordid = null;

    /** The format name is always 10 characters. */
    protected final int FORMATNAMESIZE = 10;

    /** The header is the same size as the format name (for now). */
    protected final int HEADERSIZE = FORMATNAMESIZE;

    /**
     * Add a detail subrecord to this record.
     *
     * @param newdetail the new detail record to add.
     */
    public void addDetail(MessageRecord newdetail)
    {
        detail.addElement(newdetail);
    }

    /**
     * Remove all the detail records from this record.
     */
    public void clearDetail()
    {
        detail.removeAllElements();
    }

    /**
     * All the created classes must implement the createFields method to
     * define the message field structure.
     */
    abstract protected void createFields();

    /**
     * Return this message to the MessageHandler cache if there is a MessageHandler
     * set.
     */
    public void destroy()
    {
        if (myhandler != null)
            myhandler.returnMessageRecord(this);
    }

    /**
     * Return the number of detail records contained by this record.
     *
     * @return The number of detail records.
     */
    public int detailCount()
    {
        return detail.size();
    }

    /**
     * Return an Enumeration of the detail records contained by this record.
     *
     * @return an Enumeration of the detail records.
     */
    public Enumeration details()
    {
        return detail.elements();
    }

    /**
     * Returns an enumeration of the fields of this record.
     *
     * @return an enumeration of the fields.
     */
    public Enumeration fieldEnumeration()
    {
        return new MessageEnumerator(this);
    }

    /**
     * Get the total length of this message in bytes.
     *
     * @return total length of this message.
     */
    public int getByteLength()
    {
        return HEADERSIZE + recordsize;
    }

    /**
     * Get a detail subrecord from this record.
     *
     * @param index the index number of the detail record to return.
     *
     * @return a reference to the detail record.
     */
    public MessageRecord getDetail(int index)
    {
        return (MessageRecord)(detail.elementAt(index));
    }

    /**
     * Get a message field by field number.
     *
     * @param number the field number for the desired field.
     *
     * @return a reference to the MessageField within this message.
     */
    public MessageField getField(int number)
    {
        return fields[number];
    }

    /**
     * Get a message field by tag name or field name.
     *
     * @param tag the tag name for the desired field.
     *
     * @return a reference to the MessageField within this message.
     */
    public MessageField getField(String key)
    {
        return fields[getFieldIndex(key)];
    }

    /**
     * Get a message field index by tag name or field name.
     *
     * @param tag the tag name for the desired field.
     *
     * @return a reference to the MessageField within this message.
     */
    public int getFieldIndex(String key)
    {
        Object index = taglookup.get(key);
        if (index == null) {
            index = namelookup.get(key);
        }
        if (index == null) {
            throw new RuntimeException("No Message Field found for Key : " + key);
        }
        return ((Integer)index).intValue();
    }

    /**
     * Gets a message field name by tag name.
     *
     * @param tag the tag name for the desired field.
     *
     * @return the field name.
     */
    public String getFieldName(String tag)
    {
        return fieldnames[getFieldIndex(tag)];
    }

    /**
     * Get the number of fields in this message.
     *
     * @return the number of fields in this message.
     */
    public int getFieldCount()
    {
        return fields.length;
    }

    /**
     * A convenience method that returns the string value for a field by tag name.
     *
     * @param tag the tag name for the desired field.
     *
     * @return a string containing the current value of the field.
     */
    public String getFieldString(String tag)
    {
        return getField(tag).getString();
    }

    /**
     * Get the file id of the generating file.
     *
     * @return file identifier.
     */
    public String getFileID()
    {
        return fileid;
    }

    /**
     * Get the format name as a String.
     *
     * @return the format name for this message.
     */
    public String getFormatName()
    {
        return formatname;
    }

    /**
     * Get the record level id of the generating file.
     *
     * @return record level id.
     */
    public String getRecordLevelID()
    {
        return recordid;
    }

    /**
     * Initialize all the fields of this message.
     */
    public void initialize()
    {
        if (message == null)
            message = new byte[getByteLength()];

        setFormatNameBytes();
        isempty = false;

        if (namelookup == null){
            namelookup = new Hashtable(fieldnames.length);
            for (int i = 0; i < fieldnames.length; i++)
                namelookup.put(fieldnames[i], new Integer(i));
        }

        if (tagnames == null) /* Older version of generated class */
        {
            tagnames = new String[fields.length];

            for (int i = 0; i < fields.length; i++)
            {
                tagnames[i] = fieldnames[i];
                fields[i].setTag(tagnames[i]);
            }
        }

        if (taglookup == null) /* Older version of generated class */
        {
            taglookup = new Hashtable(tagnames.length);

            for (int i = 0; i < tagnames.length; i++)
                taglookup.put(tagnames[i], new Integer(i));
        }

        for (int i = 0; i < fields.length; i++)
            fields[i].initialize();
    }

    /**
     * Return a boolean value indicating whether this message is empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty()
    {
        return isempty;
    }

    /**
     * Reset all the fields of this message.
     */
    public void reset()
    {
        for (int i = 0; i < fields.length; i++)
            fields[i].reset(message);

        clearDetail();
    }

    /**
     * Send this message over the datastream of the current MessageHandler.
     *
     * @exception IOException if the message cannot be sent over that datastream.
     */
    public void send() throws IOException
    {
        if (myhandler != null)
            myhandler.sendMessage(this);
        else
            throw new IOException("No MessageHandler set for this message.");
    }

    /**
     * Send this message over the specified MessageRouter.
     *
     * @param MessageRouter is the router over which this message should be sent.
     *
     * @exception IOException if the message cannot be sent over that message router.
     */
    public void send(MessageRouter messagerouter) throws IOException
    {
        if (message != null)
        {
            if (isempty)
            {
                messagerouter.send(emessage);
            }
            else
            {
                messagerouter.send(message);
            }
        }
    }

    /**
     * Set the handler for this message.  The handler can be used to set
     * default data streams and to provide message object caching.
     *
     * @param context the MessageHandler for this message.
     */
    public void setHandler(MessageHandler handler)
    {
        myhandler = handler;
    }

    /**
     * Set the context for this message.  The context can be used to set
     * default data streams and to provide message object caching.
     *
     * @param context the MessageContext for this message.
     */
    public void setContext(MessageContext context)
    {
        myhandler = context.getHandler();
    }

    /**
     * Set the empty state of this message.
     *
     * @param emptystate true if empty, false otherwise.
     */
    public void setEmpty(boolean emptystate)
    {
        isempty = emptystate;
    }

    public Object clone()
    {
        MessageRecord myclone = null;

        try
        {
            myclone = myhandler.getMessageRecord(formatname);
            myclone.setEmpty(isempty);
            myclone.setMessageBytes((byte[])message.clone());
        }
        catch (Exception e)
        {
        }

        return myclone;
    }

    /**
     * Set the format name bytes in the message byte array.
     */
    protected void setFormatNameBytes()
    {
        byte[] fbytes = formatname.getBytes();
        emessage = new byte[FORMATNAMESIZE];

        for (int i = 0; i < FORMATNAMESIZE; i++)
            emessage[i] = message[i] = (i < fbytes.length) ? fbytes[i] : (byte)' ';
    }

    /**
     * Set the message byte array to a new value.
     *
     * @param messagebytes an array of bytes of the correct length for this message.
     *
     * @exception IOException if the array is not the correct length for this message.
     */
    public void setMessageBytes(byte[] messagebytes) throws IOException
    {
        if (messagebytes.length != getByteLength()){
            if(messagebytes.length > 10){
                throw new IOException("Message length mismatch for: " + new String(messagebytes, 0, 10).trim());
            } else {
                throw new IOException("Message length mismatch");
            }
        }
        message = messagebytes;
        reset();
    }

  /*
  private void writeObject(java.io.ObjectOutputStream out) throws IOException {
	  if (isempty) {
		  out.writeObject(emessage);
	  } else {
		  out.writeObject(message);
	  }
  }

  private void readObject(java.io.ObjectInputStream in)
	  throws IOException, ClassNotFoundException {
	  	detail = new Vector();
	  	createFields();
		message = (byte[]) in.readObject();
	  	initialize();
	  	if (message.length < 10)
	  		throw new IOException("Message is not valid: " + message);
	  	if (message.length > 10){
	  		setMessageBytes(message);
	  		setEmpty(false);
	  	} else {
	  		setEmpty(true);
	  	}
  }
  */

    /**
     * This method is used to assist in debug of applications.  It returns
     * a String that contains the contents of all the fields of this message.
     *
     * @return a String that contains the format name and all field names and values.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer(1000);

        sb.append(formatname);
        sb.append("Message");

        if (isempty)
            sb.append(":Empty");
        else
        {
            for (int i = 0; i < fields.length; i++)
            {
                sb.append(":");
                sb.append(fieldnames[i]);
                sb.append("=<");
                sb.append(fields[i].getString());
                sb.append(">");
            }
        }

        return sb.toString();
    }

    public Object get(Object key) {
        if (key instanceof String) {
            return getField((String)key);
        }
        throw new ClassCastException("This Message Map only supports String keys");
    }

    public int size() {
        return getFieldCount();
    }

    public boolean containsKey(Object key) {
        return taglookup.containsKey(key)
                || namelookup.containsKey(key);
    }

}
