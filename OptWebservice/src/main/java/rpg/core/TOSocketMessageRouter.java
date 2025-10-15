package rpg.core;

import java.io.IOException;

/**
 * @author ogarcia
 *
 * Class extends SocketMessageRouter to be able to set
 * a timeout for socket connection.
 *
 */
public class TOSocketMessageRouter extends SocketMessageRouter {

    int timeout = 0;

    /**
     * Allows to set socket timeout.
     * @param servername
     * @param serverport
     * @param sockettimeout
     */
    public TOSocketMessageRouter(
            String servername,
            int serverport,
            int sockettimeout) {
        server = servername;
        port = serverport;
        timeout = sockettimeout;
    }

    /**
     * Opens the connection.
     * Call this method after creating a SocketMessageRouter to open the
     * message router connections.  If the socket is null, a new one is created
     * using the server name and port number.  New input and output data streams
     * are created if either of them is null. A timeout is defined
     *
     * @exception IOException
     */
    public void open() throws IOException {
        super.open();

        if (s != null) {
            s.setSoTimeout(timeout);
        }
    }
}
