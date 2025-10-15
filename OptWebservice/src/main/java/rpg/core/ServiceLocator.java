package rpg.core;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author fhernandez
 *
 * @version $Revision: 1.1 $
 */
public class ServiceLocator {

    /**
     * Field log
     */
    private final static Logger log = Logger.getLogger(ServiceLocator.class.getName());

    /**
     * Field SlInfo
     */
    private static PropertyResourceBundle SlInfo = null;

    /**
     * Field ServiceLocatorRef
     */
    private static ServiceLocator ServiceLocatorRef = null;

    /**
     * Field ClassNamesCache
     */
    private static Hashtable ClassNamesCache = null;
    /**
     * Field SocketIpCache
     */
    private static Hashtable SocketIpCache = null;
    /**
     * Field SocketPortCache
     */
    private static Hashtable SocketPortCache = null;
    /**
     * Field SocketTimeOutCache
     */
    private static Hashtable SocketTimeOutCache = null;
    /**
     * Field SocketMsgPackageCache
     */
    private static Hashtable SocketMsgPackageCache = null;
    /**
     * Field SocketMsgRouterCache
     */
    private static Hashtable SocketMsgRouterCache = null;

    /**
     * Field JmsCnxFactoryCache
     */
    private static Hashtable JmsCnxFactoryCache = null;
    /**
     * Field JmsServerQueueCache
     */
    private static Hashtable JmsServerQueueCache = null;
    /**
     * Field JmsClientQueueCache
     */
    private static Hashtable JmsClientQueueCache = null;
    /**
     * Field JmsTimeOutCache
     */
    private static Hashtable JmsTimeOutCache = null;
    /**
     * Field JmsMsgPackageCache
     */
    private static Hashtable JmsMsgPackageCache = null;

    /**
     * Field sckIP
     */
    private String sckIP = "127.0.0.1";
    /**
     * Field sckPort
     */
    private int sckPort = -1;
    /**
     * Field sckTimeOut
     */
    private int sckTimeOut = 18000;
    /**
     * Field sckMsgPackage
     */
    private String sckMsgPackage = "ec.com.bancointernacional.rpg.beans";
    /**
     * Field sckMsgRouter
     */
    private String sckMsgRouter = "ec.com.bancointernacional.rpg.core.TOSocketMessageRouter";

    /**
     * Field jmsCnxFactory
     */
    private String jmsCnxFactory = "";
    /**
     * Field jmsServerQueue
     */
    private String jmsServerQueue = "";
    /**
     * Field jmsClientQueue
     */
    private String jmsClientQueue = "";
    /**
     * Field jmsTimeOut
     */
    private int jmsTimeOut = 18000;
    /**
     * Field jmsMsgPackage
     */
    private String jmsMsgPackage = "";
    /**
     * Field CLASS_NAME_PREFIX
     */
    private static final String CLASS_NAME_PREFIX = "class.name.";

    /**
     * Field SOCKET_IP_PREFIX
     */
    private static final String SOCKET_IP_PREFIX = "socket.ip.";
    /**
     * Field SOCKET_PORT_PREFIX
     */
    private static final String SOCKET_PORT_PREFIX = "socket.port.";
    /**
     * Field SOCKET_TIME_OUT_PREFIX
     */
    private static final String SOCKET_TIME_OUT_PREFIX = "socket.timeout.";
    /**
     * Field SOCKET_MSG_PACKAGE_PREFIX
     */
    private static final String SOCKET_MSG_PACKAGE_PREFIX =
            "socket.msg.package.";
    /**
     * Field SOCKET_MSG_ROUTER_PREFIX
     */
    private static final String SOCKET_MSG_ROUTER_PREFIX =
            "socket.router.";

    /**
     * Field JMS_CNX_FACTORY_PREFIX
     */
    private static final String JMS_CNX_FACTORY_PREFIX = "jms.cnx.factory.";
    /**
     * Field JMS_SERVER_QUEUE_PREFIX
     */
    private static final String JMS_SERVER_QUEUE_PREFIX = "jms.server.queue.";
    /**
     * Field JMS_CLIENT_QUEUE_PREFIX
     */
    private static final String JMS_CLIENT_QUEUE_PREFIX = "jms.client.queue.";
    /**
     * Field JMS_TIME_OUT_PREFIX
     */
    private static final String JMS_TIME_OUT_PREFIX = "jms.timeout.";
    /**
     * Field JMS_MSG_PACKAGE_PREFIX
     */
    private static final String JMS_MSG_PACKAGE_PREFIX = "jms.msg.package.";


    /**
     * Field socketRouter
     */
    private static boolean socketRouter = true;

    static {
        ServiceLocatorRef = new ServiceLocator();
    }

    /**
     * Private Constructor for ServiceLocator.
     * @see java.lang.Object#Object()
     */
    private ServiceLocator() {
        loadProperties();
    }

    /**
     * Method loadProperties.
     */
    private void loadProperties() {
        try {

            ClassNamesCache = new Hashtable();
            SocketIpCache = new Hashtable();
            SocketPortCache = new Hashtable();
            SocketTimeOutCache = new Hashtable();
            SocketMsgPackageCache = new Hashtable();
            SocketMsgRouterCache = new Hashtable();

        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.SEVERE, "Error:", e);
        }

    }

    /**
     * Method getInstance.
     * @return ServiceLocator
     */
    public static ServiceLocator getInstance() {
        return ServiceLocatorRef;
    }

    /**
     * Method getEJBHomeRef.
     * @param key
     * @return Class
     * @throws ServiceLocatorException
     */
    static private Class getEJBHomeRef(String key)
            throws ServiceLocatorException {

        Class homeRef = null;
        String homeName = null;
        try {
            homeName = (String) ClassNamesCache.get(CLASS_NAME_PREFIX + key);
            homeRef = Class.forName(homeName);
            return homeRef;
        } catch (ClassNotFoundException e) {
            throw new ServiceLocatorException(e.toString());
        } catch (Exception e) {
            throw new ServiceLocatorException(e.toString());
        }

    }

    /**
     * @param ip
     * @param port
     * @param timeout
     * @param pck
     * @return MessageHandler
     * @throws ServiceLocatorException
     */
    public MessageHandler getMessageHandlerSocketRouter(
            String ip,
            int port,
            int timeout,
            String pck)
            throws ServiceLocatorException {

        MessageRouter router = null;
        try {
            router = new TOSocketMessageRouter(ip, port, timeout);
            return new MessageHandler(router, pck);
        } catch (Exception e) {
            throw new ServiceLocatorException(
                    router != null ? (router.toString() + "\nCause By : ") : "" + e.toString());
        }

    }

    /**
     * @param queueConnectionFactory
     * @param sendQueue
     * @param receiveQueue
     * @param timeout
     * @param pck
     * @return MessageHandler
     * @throws ServiceLocatorException
     */
    public MessageHandler getMessageHandlerJMSRouter(
            String queueConnectionFactory,
            String sendQueue,
            String receiveQueue,
            int timeout,
            String pck)
            throws ServiceLocatorException {

        MessageRouter router = null;
        try {
            router = new JMSMessageRouter(
                    queueConnectionFactory,
                    sendQueue,
                    receiveQueue,
                    timeout);
            return new MessageHandler(router, pck);
        } catch (Exception e) {
            throw new ServiceLocatorException(
                    router != null ? (router.toString() + "\nCause By : ") : "" + e.toString());
        }
    }

    /**
     * @param key
     * @return MessageHandler
     * @throws ServiceLocatorException
     */
    public MessageHandler getMessageHandler(String key)
            throws ServiceLocatorException {
        MessageHandler mh = null;
        if (socketRouter) {
            mh =
                    getMessageHandlerSocketRouter(
                            getSocketIp(key),
                            getSocketPort(key),
                            getSocketTimeOut(key),
                            getSocketMsgPackage(key));
        } else {
            mh =
                    getMessageHandlerJMSRouter(
                            getJmsCnxFactory(key),
                            getJmsServerQueue(key),
                            getJmsClientQueue(key),
                            getJmsTimeOut(key),
                            getJmsMsgPackage(key));
        }
        return mh;
    }

    /**
     * @return MessageHandler
     * @throws ServiceLocatorException
     */
    public MessageHandler getMessageHandler() throws ServiceLocatorException {

        MessageHandler mh = null;
        if (socketRouter) {
            mh =
                    getMessageHandlerSocketRouter(
                            sckIP,
                            sckPort,
                            sckTimeOut,
                            sckMsgPackage);
        } else {
            mh =
                    getMessageHandlerJMSRouter(
                            jmsCnxFactory,
                            jmsServerQueue,
                            jmsClientQueue,
                            jmsTimeOut,
                            jmsMsgPackage);
        }
        return mh;
    }

    /**
     * Method getSocketIp
     * @param key String
     * @return String
     */
    public String getSocketIp(String key) {
        String ip = sckIP;
        try {
            Object obj = SocketIpCache.get(SOCKET_IP_PREFIX + key);
            int pos = key.length();
            while (obj == null && (pos = key.lastIndexOf('.', pos)) >= 0) {
                obj = SocketIpCache.get(SOCKET_IP_PREFIX + key.substring(0, pos));
                pos = pos - 1;
            }
            if (obj == null && (pos = key.lastIndexOf('.')) >= 0) {
                obj = SocketIpCache.get(SOCKET_IP_PREFIX + key.substring(pos+1));
            }
            if (obj != null) {
                ip = (String) obj;
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return ip;
    }

    /**
     * Method getSocketPort
     * @param key String
     * @return int
     */
    public int getSocketPort(String key) {
        int port = sckPort;
        try {
            Object obj = SocketPortCache.get(SOCKET_PORT_PREFIX + key);
            int pos = key.length();
            while (obj == null && (pos = key.lastIndexOf('.', pos)) >= 0) {
                obj = SocketPortCache.get(SOCKET_PORT_PREFIX + key.substring(0, pos));
                pos = pos - 1;
            }
            if (obj == null && (pos = key.lastIndexOf('.')) >= 0) {
                obj = SocketPortCache.get(SOCKET_PORT_PREFIX + key.substring(pos+1));
            }
            if (obj != null) {
                port = ((Integer) obj).intValue();
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return port;
    }

    /**
     * Method getSocketTimeOut
     * @param key String
     * @return int
     */
    public int getSocketTimeOut(String key) {
        int timeout = sckTimeOut;
        try {
            Object obj = SocketTimeOutCache.get(SOCKET_TIME_OUT_PREFIX + key);
            int pos = key.length();
            while (obj == null && (pos = key.lastIndexOf('.', pos)) >= 0) {
                obj = SocketTimeOutCache.get(SOCKET_TIME_OUT_PREFIX + key.substring(0, pos));
                pos = pos - 1;
            }
            if (obj == null && (pos = key.lastIndexOf('.')) >= 0) {
                obj = SocketTimeOutCache.get(SOCKET_TIME_OUT_PREFIX + key.substring(pos+1));
            }
            if (obj != null) {
                timeout = ((Integer) obj).intValue();
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return timeout;
    }

    /**
     * Method getSocketMsgPackage
     * @param key String
     * @return String
     */
    public String getSocketMsgPackage(String key) {
        String pck = sckMsgPackage;
        try {
            Object obj =
                    SocketMsgPackageCache.get(SOCKET_MSG_PACKAGE_PREFIX + key);
            if (obj != null) {
                pck = (String) obj;
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return pck;
    }

    /**
     * Method getSocketMsgPackage
     * @param key String
     * @return String
     */
    public String getSocketMsgRouter(String key) {
        String pck = sckMsgRouter;
        try {
            Object obj =
                    SocketMsgRouterCache.get(SOCKET_MSG_ROUTER_PREFIX + key);
            if (obj != null) {
                pck = (String) obj;
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return pck;
    }

    /**
     * Method getJmsCnxFactory
     * @param key String
     * @return String
     */
    public String getJmsCnxFactory(String key) {
        String ip = jmsCnxFactory;
        try {
            Object obj = JmsCnxFactoryCache.get(JMS_CNX_FACTORY_PREFIX + key);
            if (obj != null) {
                ip = (String) obj;
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return ip;
    }

    /**
     * Method getJmsServerQueue
     * @param key String
     * @return String
     */
    public String getJmsServerQueue(String key) {
        String ip = jmsServerQueue;
        try {
            Object obj = JmsServerQueueCache.get(JMS_SERVER_QUEUE_PREFIX + key);
            if (obj != null) {
                ip = (String) obj;
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return ip;
    }

    /**
     * Method getJmsClientQueue
     * @param key String
     * @return String
     */
    public String getJmsClientQueue(String key) {
        String ip = jmsClientQueue;
        try {
            Object obj = JmsClientQueueCache.get(JMS_CLIENT_QUEUE_PREFIX + key);
            if (obj != null) {
                ip = (String) obj;
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return ip;
    }

    /**
     * Method getJmsTimeOut
     * @param key String
     * @return int
     */
    public int getJmsTimeOut(String key) {
        int timeout = jmsTimeOut;
        try {
            Object obj = JmsTimeOutCache.get(JMS_TIME_OUT_PREFIX + key);
            if (obj != null) {
                timeout = ((Integer) obj).intValue();
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return timeout;
    }

    /**
     * Method getJmsMsgPackage
     * @param key String
     * @return String
     */
    public String getJmsMsgPackage(String key) {
        String pck = jmsMsgPackage;
        try {
            Object obj = JmsMsgPackageCache.get(JMS_MSG_PACKAGE_PREFIX + key);
            if (obj != null) {
                pck = (String) obj;
            }
        } catch (Exception e) {
            log.log(Level.CONFIG, "Error", e);
        }
        return pck;
    }

}
