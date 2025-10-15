package rpg.core;

import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Class provides a sockets implementation of a MessageRouter.
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 6:09:56 PM)
 *
 * @author
 *
 * @tag    Date          Flag        Description
 */
public class SocketMessageRouter extends MessageRouter
{
    final static String COPYRIGHT =  "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    String server = null;
    int port = -1;
    Socket s = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    /**
     * Default constructor.
     *
     */
    public SocketMessageRouter()
    {
    }

    /**
     * Constructs a message router with the specified input and output streams.
     *
     * @param DataInputStream the input stream to receive from
     * @param DataOutputStream the output stream to send to
     */
    public SocketMessageRouter(DataInputStream inputstream,
                               DataOutputStream outputstream)
    {
        in = inputstream;
        out = outputstream;
    }

    /**
     * Construct a message router using the specified server name and port.
     *
     * @param String server name
     * @param int server port number
     */
    public SocketMessageRouter(String servername,
                               int serverport)
    {
        server = servername;
        port = serverport;
    }
    /**
     * Construct a message router using a server name and port specified in a Hashtable.
     *
     * @param Hashtable properties for this router
     */
    public SocketMessageRouter(Hashtable properties)
    {
        server = (String)properties.get("SocketServerAddress");
        port = Integer.parseInt((String)properties.get("SocketServerPort"));
    }
    /**
     * Constructs a message router with the specified socket.
     *
     * @param Socket the socket to use
     */
    public SocketMessageRouter(Socket socket)
    {
        s = socket;
    }
    /**
     * Close the socket based message router.
     * This method closes the input and output data streams and the socket
     * connection.
     *
     * @exception IOException
     */
    public void close() throws IOException
    {
        if (in != null)
            in.close();
        in = null;
        if (out != null)
            out.close();
        out = null;
        if (s != null)
            s.close();
        s = null;
    }
    /**
     * Flushes the output data stream.
     *
     * @exception IOException
     */
    public void flush() throws IOException
    {
        out.flush();
    }
    /**
     * Opens the connection.
     * Call this method after creating a SocketMessageRouter to open the
     * message router connections.  If the socket is null, a new one is created
     * using the server name and port number.  New input and output data streams
     * are created if either of them is null.
     *
     * @exception IOException
     */
    public void open() throws IOException
    {
        if ((in == null) || (out == null))
        {
            if (s == null)
            {
                if ((server == null) || (port == -1))
                {
                    throw new IOException("Server name or port not provided");
                }
                s = new Socket(server, port);
            }
            if (in == null)
            {
                in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            }
            if (out == null)
            {
                out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            }
        }
    }
    /**
     * Receive a message from the input data stream.
     *
     * @return byte array - the message received
     * @exception  IOException
     */
    public byte[] receive() throws IOException
    {
        byte[] message;

        if (in == null)
            open();

        message = new byte[in.readShort()];
        in.readFully(message);

        return message;
    }
    /**
     * Send a message to the output data stream.
     *
     * @param byte array - the message to send
     * @exception IOException
     */
    public void send(byte[] message) throws IOException
    {
        if (out == null)
            open();

        out.writeShort(message.length);
        out.write(message);
    }
    /**
     * Set the InputStream for this router.
     *
     * @param inputstream a DataInputStream to be used for receiving messages.
     */
    public void setInputStream(DataInputStream inputstream)
    {
        in = inputstream;
    }
    /**
     * Get the InputStream for this router.
     *
     * @return input stream
     */
    public DataInputStream getInputStream()
    {
        return in;
    }
    /**
     * Set the OutputStream for this router.
     *
     * @param outputstream a DataOutputStream to be used for sending messages.
     */
    public void setOutputStream(DataOutputStream outputstream)
    {
        out = outputstream;
    }
    /**
     * Sets the server name and port number.
     *
     * @param String the server name
     * @param int the port number
     */
    public void setServer(String servername, int serverport)
    {
        server = servername;
        port = serverport;
    }
    /**
     * Set the socket for this router.
     *
     * @param socket a Socket to be used for sending and receiving messages.
     */
    public void setSocket(Socket socket)
    {
        s = socket;
    }


    public String toString() {
        return s != null ? s.toString() : super.toString();
    }

}
