package rpg.core;

/*
 * Created on Jun 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author ogarcia
 *
 */
public class JMSMessageRouter extends MessageRouter {

    public static final String JMS_MESSAGE_CORRELATION_ID_PREFIX = "CORR:TF";

    public static final String PROPERTY_SIZE_NAME = "size";

    private InitialContext initialContext = null;

    private String queueConnectionFactory = null;
    private String sendQueue = null;
    private String receiveQueue = null;

    private QueueSession session = null;
    private QueueConnection connection = null;

    private QueueSender queueSender = null;
    private QueueReceiver queueReceiver = null;

    private QueueConnectionFactory qcf = null;
    private Queue outQueue = null;
    private Queue inQueue = null;

    private String selector = null;

    private int jmsTimeOut = 15000;

    public JMSMessageRouter() {
        super();
    }

    public JMSMessageRouter(
            String queueConnectionFactory,
            String sendQueue,
            String receiveQueue) {
        this.queueConnectionFactory = queueConnectionFactory;
        this.sendQueue = sendQueue;
        this.receiveQueue = receiveQueue;
        init();
    }

    public JMSMessageRouter(
            String queueConnectionFactory,
            String sendQueue,
            String receiveQueue,
            int timeout) {
        this.queueConnectionFactory = queueConnectionFactory;
        this.sendQueue = sendQueue;
        this.receiveQueue = receiveQueue;
        this.jmsTimeOut = timeout;
        init();
    }

    public void init() {
        try {
            initialContext = new InitialContext();
            // Getting factory and queues
            qcf = (QueueConnectionFactory) getJMS(queueConnectionFactory);
            if (qcf == null) {
                throw new JMSException("JMSMessageRouter: Failed to retrieve a QueueConnectionFactory from JNDI");
            }

            outQueue = (Queue) getJMS(sendQueue);
            inQueue = (Queue) getJMS(receiveQueue);

            selector = JMS_MESSAGE_CORRELATION_ID_PREFIX + (new java.util.Random()).nextInt(999999);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.datapro.sockets.routers.MessageRouter#close()
     */
    public void close() throws IOException {
        try {
            // Call the close() method on all of the objects.
            queueReceiver.close();
            queueSender.close();
            session.close();
            session = null;
            connection.close();
            connection = null;
        } catch (JMSException e) {
            StringBuffer sb =
                    new StringBuffer("JMSMessageRouter: JMS failed with " + e);
            Exception le = e.getLinkedException();
            if (le != null) {
                sb.append("linked exception " + le);
            }
            throw new IOException(sb.toString());
        } finally {
            try {
                if (session != null)
                    session.close();
                if (connection != null)
                    connection.close();
            } catch (JMSException e) {
                throw new IOException(
                        "JMSMessageRouter: Unexpected error, failed with " + e);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.datapro.sockets.routers.MessageRouter#flush()
     */
    public void flush() throws IOException {
        // It is not needed
    }

    /* (non-Javadoc)
     * @see com.datapro.sockets.routers.MessageRouter#open()
     */
    public void open() throws IOException {

        try {
            // Create a QueueConnection from the QueueConnectionFactory
            connection = qcf.createQueueConnection();

            // IMPORTANT: Receive calls will be blocked if the connection is
            // not explicitly started, so make sure its started.
            connection.start();

            // Create a QueueSession from the connection.
            // Not transacted, and acknowledge received messages
            boolean transacted = false;
            session =
                    connection.createQueueSession(
                            transacted,
                            Session.AUTO_ACKNOWLEDGE);

            // Use the session to create a QueueSender, passing in the Queue as parameter
            queueSender = session.createSender(outQueue);

            // Message sent, let's read the response message using the correlation ID
            queueReceiver =
                    session.createReceiver(
                            inQueue,
                            "JMSCorrelationID = '" + selector + "'");
        } catch (JMSException e) {
            StringBuffer sb =
                    new StringBuffer("JMSMessageRouter: JMS failed with " + e);
            Exception le = e.getLinkedException();
            if (le != null) {
                sb.append("linked exception " + le);
            }
            throw new IOException(sb.toString());
        }

    }

    /* (non-Javadoc)
     * @see com.datapro.sockets.routers.MessageRouter#receive()
     */
    public byte[] receive() throws IOException {
        try {
            byte[] message;

            // Use the QueueReceiver to retrieve the message, blocking for a maximum of 5000ms.
            // The receive call returns when the message arrives, or after X ms
            Message inMessage = queueReceiver.receive(jmsTimeOut);

            // Check to see if the receive call has actually returned a message.
            if (inMessage == null) {
                throw new IOException("Failed to retrieve message.");
            }

            if (inMessage instanceof BytesMessage) {
                // Extract the message content with getText()
                int size =
                        Integer.parseInt(
                                inMessage.getStringProperty(PROPERTY_SIZE_NAME));
                message = new byte[size];
                BytesMessage bm = (BytesMessage) inMessage;
                bm.readBytes(message, size);
            } else {
                // Report that the incoming message was not of the expected type, and throw an exception
                throw new IOException("JMSMessageRouter: Retrieved the wrong type of message");
            }
            return message;
        } catch (JMSException e) {
            StringBuffer sb =
                    new StringBuffer("JMSMessageRouter: JMS failed with " + e);
            Exception le = e.getLinkedException();
            if (le != null) {
                sb.append("linked exception " + le);
            }
            throw new IOException(sb.toString());
        }
    }

    /* (non-Javadoc)
     * @see com.datapro.sockets.routers.MessageRouter#send(byte[])
     */
    public void send(byte[] message) throws IOException {
        try {

            // use the session to create our objectmessage
            BytesMessage outMessage = session.createBytesMessage();

            // Set message bytes and correlation ID
            outMessage.writeBytes(message, 0, message.length);
            outMessage.setStringProperty(
                    PROPERTY_SIZE_NAME,
                    message.length + "");
            outMessage.setJMSCorrelationID(selector);

            // send the message
            queueSender.send(outMessage);

			/*
			TextMessage outMessage = session.createTextMessage("test");
			queueSender.send(outMessage);
			*/
            // get message id
            // String messageID = outMessage.getJMSMessageID();
        } catch (JMSException e) {
            StringBuffer sb =
                    new StringBuffer("JMSMessageRouter: JMS failed with " + e);
            Exception le = e.getLinkedException();
            if (le != null) {
                sb.append("linked exception " + le);
            }
            throw new IOException(sb.toString());
        }

    }

    private Object getJMS(String jmsRef) throws NamingException {
        if (initialContext != null) {
            Object nsObject =
                    initialContext.lookup(
                            new StringBuffer("java:comp/env/")
                                    .append(jmsRef)
                                    .toString());
            System.out.println("class=" + nsObject.getClass());
            return nsObject;
        } else {
            throw new NamingException("HomeFactory: no InitialContext");
        }
    }
    /**
     * @return
     */
    public String getQueueConnectionFactory() {
        return queueConnectionFactory;
    }

    /**
     * @return
     */
    public String getReceiveQueue() {
        return receiveQueue;
    }

    /**
     * @return
     */
    public String getSendQueue() {
        return sendQueue;
    }

    /**
     * @param string
     */
    public void setQueueConnectionFactory(String string) {
        queueConnectionFactory = string;
    }

    /**
     * @param string
     */
    public void setReceiveQueue(String string) {
        receiveQueue = string;
    }

    /**
     * @param string
     */
    public void setSendQueue(String string) {
        sendQueue = string;
    }

    /**
     * @param string
     */
    public void setSelector(String string) {
        selector = string;
    }

    public String getSelector() {
        return selector;
    }
    /**
     * @return
     */
    public int getJmsTimeOut() {
        return jmsTimeOut;
    }

    /**
     * @param i
     */
    public void setJmsTimeOut(int i) {
        jmsTimeOut = i;
    }

}
