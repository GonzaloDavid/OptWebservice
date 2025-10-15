package rpg.core;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 * The MessageContext class can contain information about the connection
 * to be used for incoming and outgoing messages, or about the package that
 * contains the message classes, or both.
 */
public class MessageContext
{
    MessageHandler handler = null;
    MessageRouter router = null;

    /**
     * Construct a MessageContext object that does not contain information about
     * either the connection or the package.  These items can be set later.
     */
    public MessageContext()
    {
        router = new SocketMessageRouter();
        buildHandler(null);
    }

    /**
     * Construct a MessageContext that contains information about a connection
     * in terms of the input and output streams.
     *
     * @param inputstream a stream from which new messages can be received.
     * @param outputstream a stream on which messages can be sent.
     */
    public MessageContext(DataInputStream inputstream,
                          DataOutputStream outputstream)
    {
        router = new SocketMessageRouter(inputstream, outputstream);
        buildHandler(null);
    }

    /**
     * Construct a MessageContext that contains information about a connection
     * in terms of the MessageHandler.
     */
    public MessageContext(MessageHandler mh)
    {
        this.handler = mh;
        router = mh.router;
    }

    /**
     * Construct a MessageContext that contains the package name for message classes
     * to be loaded and cached.
     *
     * @param packagename the name of the package containing the message classes.
     */
    public MessageContext(String packagename)
    {
        router = new SocketMessageRouter();
        buildHandler(packagename);
    }

    /**
     * Construct a MessageContext that contains information about a connection
     * in terms of the input and output streams and the package name for the
     * Message objects.
     *
     * @param inputstream a stream from which new messages can be received.
     * @param outputstream a stream on which messages can be sent.
     * @param packagename the name of the package containing the message objects.
     */
    public MessageContext(DataInputStream inputstream,
                          DataOutputStream outputstream,
                          String packagename)
    {
        router = new SocketMessageRouter(inputstream, outputstream);
        buildHandler(packagename);
    }

    private void buildHandler(String packagename)
    {
        handler = new MessageHandler(packagename);
        try
        {
            handler.setMessageRouter(router);
        }
        catch (IOException e) {};
    }

    /**
     * Set the InputStream for this context.
     *
     * @param inputstream a DataInputStream to be used for receiving messages.
     */
    public void setInputStream(DataInputStream inputstream)
    {
        ((SocketMessageRouter)router).setInputStream(inputstream);
    }

    /**
     * Set the OutputStream for this context.
     *
     * @param outputstream a DataOutputStream to be used for sending messages.
     */
    public void setOutputStream(DataOutputStream outputstream)
    {
        ((SocketMessageRouter)router).setOutputStream(outputstream);
    }

    /**
     * Set the package for this context.
     *
     * @param package the name of the package containing the message classes for this MessageContext.
     */
    public void setPackage(String packagename)
    {
        handler.setPackage(packagename);
    }

    /**
     * Receive the next message (a concrete subclass of MessageRecord) from the specified
     * DataInputStream.
     *
     * @param inputstream a DataInputStream on which a message can be received.
     *
     * @return a message object which is a concrete subclass of MessageRecord.
     *
     * @exception IOException if there is an error reading the message or constructing
     * the message object.
     */
    public MessageRecord receiveMessage(DataInputStream inputstream) throws IOException
    {
        DataInputStream holdin = ((SocketMessageRouter)router).getInputStream();
        MessageRecord mr = null;

        ((SocketMessageRouter)router).setInputStream(inputstream);
        mr = handler.receiveMessage();
        ((SocketMessageRouter)router).setInputStream(holdin);

        return mr;
    }

    /**
     * Receive the next message (a concrete subclass of MessageRecord) from the default
     * DataInputStream for this MessageContext.
     *
     * @return a message object which is a concrete subclass of MessageRecord.
     *
     * @exception IOException if there is an error reading the message or constructing
     * the message object.
     */
    public MessageRecord receiveMessage() throws IOException
    {
        return handler.receiveMessage();
    }

    /**
     * Get the MessageHandler for this context.
     *
     * @return the MessageHandler for this context
     */
    public MessageHandler getHandler()
    {
        return handler;
    }

    /**
     * Get an instance of a specific subclass of MessageRecord.
     *
     * @param formatname the up to 10 character format name used to create the MessageRecord subclass.
     *
     * @return an instance of the specified concrete subclass of MessageRecord.
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
        return handler.getMessageRecord(formatname);
    }

    /**
     * Return an instance of a message to the appropriate cache.
     *
     * @param message an instance of a MessageRecord subclass that is no longer required.
     */
    public void returnMessageRecord(MessageRecord message)
    {
        handler.returnMessageRecord(message);
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
     * Close the MessageContext through his MessageRouter
     * @throws IOException
     */
    public void close() throws IOException
    {
        if(handler != null) handler.releaseMessageRouter().close();
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return handler.toString();
    }

}
