package rpg.core;

import java.util.*;
import java.io.*;

/**
 * The MessageHandler class can contain information about the connection
 * to be used for incoming and outgoing messages, or about the package that
 * contains the message classes, or both.
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 2:52:56 PM)
 *
 * @author ?
 *
 * @tag    Date          Flag        Description
 */

public class MessageHandler
{
    final static String COPYRIGHT =  "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    MessageRouter router = null;
    String messagepackage = null;
    Hashtable classtable = new Hashtable();
    Hashtable readertable = new Hashtable();

    /**
     * Construct a MessageHandler object that does not contain information about
     * either the connection or the package.  These items can be set later.
     */
    public MessageHandler()
    {
    }

    /**
     * Construct a MessageHandler that contains information about a connection.
     *
     * @exception IOException
     */
    public MessageHandler(MessageRouter messagerouter) throws IOException
    {
        router = messagerouter;
        router.open();
    }

    /**
     * Construct a MessageHandler that contains information about the router
     * and the package name for the Message objects.
     *
     * @param messagerouter the MessageRouter object
     * @param packagename the name of the package containing the message objects.
     * @exception IOException
     */
    public MessageHandler(MessageRouter messagerouter,
                          String packagename)	throws IOException
    {
        router = messagerouter;
        messagepackage = packagename;
        router.open();
    }

    /**
     * Construct a MessageHandler that contains the package name for message classes
     * to be loaded and cached.
     *
     * @param packagename the name of the package containing the message classes.
     */
    public MessageHandler(String packagename)
    {
        messagepackage = packagename;
    }

    /**
     * Flushes the router.
     *
     * @exception IOException
     */
    public void flush() throws IOException
    {
        router.flush();
    }

    /**
     * Returns the MessageReader associated with the formatname of a MessageRecord
     *
     * @return MessageReader
     */

    public MessageReader getMessageReader(String formatname) throws IOException
    {
        Class readerclass;
        MessageReader reader = null;
        Object entry;
        String classname = formatname + "Reader";

        entry = readertable.get(formatname);

        if (entry == null)
        {
            try
            {
                if (messagepackage == null)
                    readerclass = Class.forName(classname);
                else
                    readerclass = Class.forName(messagepackage + '.' + classname);

                try
                {
                    reader = (MessageReader)readerclass.newInstance();
                }
                catch (InstantiationException e)
                {
                    throw new IOException("Unable to access reader");
                }
                catch (IllegalAccessException e)
                {
                    throw new IOException("Illegal access for reader");
                }

                readertable.put(formatname, reader);
            }
            catch (ClassNotFoundException e)
            {
                readertable.put(formatname, "Not found");
            }
        }

        if (entry instanceof MessageReader)
            reader = (MessageReader)entry;

        return reader;
    }

    /**
     * Get an empty instance of a specific subclass of MessageRecord.
     *
     * @param formatname The up to 10 character format name used to create the MessageRecord subclass.
     *
     * @return an empty instance of the specified concrete subclass of MessageRecord.
     *
     * @exception ClassNotFoundException the class could not be found.
     * @exception IllegalAccessException if the class or initializer is not accessible.
     * @exception InstantiationException if an application tries to instantiate an
     *             abstract class or an interface, or if the instantiation fails for some other reason.
     *
     */
    public MessageRecord getMessageRecord(String formatname)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        MessageRecord message;
        ClassCache entry;

        entry = (ClassCache)classtable.get(formatname);

        if (entry == null)
        {
            entry = new ClassCache(messagepackage, formatname + "Message");
            classtable.put(formatname, entry);
        }

        message = (MessageRecord)entry.getInstance();
        if (message != null)
            message.setHandler(this);

        return message;
    }

    /**
     * Receive the next message (a concrete subclass of MessageRecord) from the default
     * input for this MessageHandler.
     *
     * @return a message object which is a concrete subclass of MessageRecord.
     *
     * @exception IOException if there is an error reading the message or constructing
     * the message object.
     */
    public MessageRecord receiveMessage() throws IOException
    {
        return receiveMessage(router);
    }

    /**
     * Receive the next message (a concrete subclass of MessageRecord) from the specified
     * DataInputStream.
     *
     * @param MessageRouter on which a message can be received.
     *
     * @return a message object which is a concrete subclass of MessageRecord.
     *
     * @exception IOException if there is an error reading the message or constructing
     * the message object.
     */
    public MessageRecord receiveMessage(MessageRouter messagerouter) throws IOException
    {
        byte[] messagebytes;
        MessageRecord message;
        String formatname;
        MessageReader reader;

        flush();

        messagebytes = messagerouter.receive();

        if (messagebytes.length < 10)
            throw new IOException("Message length too short.");

        formatname = new String(messagebytes, 0, 10).trim();

        try
        {
            message = getMessageRecord(formatname);
        }
        catch (Exception e)
        {
            throw new IOException("Message class error" + e);
        }

        if (message == null)
            throw new IOException("Message format " + formatname + " not found.");

        if (messagebytes.length > 10)
        {

            message.setMessageBytes(messagebytes);
            message.setEmpty(false);
            reader = getMessageReader(formatname);
            if (reader != null)
                reader.readDetail(message, this, messagerouter);
        }
        else
        {
            message.setEmpty(true);
        }

        return message;
    }

    /**
     * Releases the MessageRouter from this MessageHandler
     * Sets this MessageHandler's MessageRouter to null
     *
     * @return com.ibm.as400.ctc.ffm.routers.MessageRouter
     */
    public MessageRouter releaseMessageRouter()
    {
        MessageRouter rtr = router;
        router = null;

        return rtr;
    }

    /**
     * Return an instance of a message to the appropriate cache.
     *
     * @param message An instance of a MessageRecord subclass that is no longer required.
     */
    public void returnMessageRecord(MessageRecord message)
    {
        ClassCache entry;

        entry = (ClassCache)classtable.get(message.getFormatName());
        if (entry != null)
        {
            message.initialize();
            entry.returnInstance(message);
        }
    }

    /**
     * Send a message over the current output stream.
     *
     * @param message an instance of a MessageRecord subclass.
     *
     * @exception IOException if there is any problem sending the message.
     */
    public void sendMessage(MessageRecord message) throws IOException
    {
        message.send(router);
    }

    /**
     * Returns the Message Router for this handler
     * @return Get the Message Router for this handler.
     */
    public MessageRouter getMessageRouter() {
        return router;
    }

    /**
     * Set the MessageRouter for this context.
     *
     * @param messagerouter a MessageRouter to be used for sending and receiving messages.
     *
     * @exception IOException
     */
    public void setMessageRouter(MessageRouter messagerouter)	throws IOException
    {
        try
        {
            if (router != null)
                router.close();
        }
        catch (IOException e)
        {}

        router = messagerouter;
    }

    /**
     * Set the package for this context.
     *
     * @param package the name of the package containing the message classes for
     *        this MessageHandler.
     */
    public void setPackage(String packagename) {
        messagepackage = packagename;
    }

    /**
     * get the package for this context.
     *
     * @return the name of the package containing the message classes for
     *        this MessageHandler.
     */
    public String getPackage() {
        return messagepackage;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return router != null ? "MessageRouter=" + router.toString() + ":MessagePackage=" + messagepackage : super.toString();
    }

}
