package ddos.acrux;

import java.util.ArrayList;

/**
 *
 * @author acrux
 */
public class FlooderPool {

    private ArrayList<FlooderThread> pool = new ArrayList<FlooderThread>();

    public FlooderPool(Flooder... flooder) {
        for (Flooder f : flooder) {
            addFlooder(f);
        }
    }

    public final Thread addFlooder(Flooder f) {
        FlooderThread t = new FlooderThread(f);
        pool.add(t);
        return t;
    }

    public void startAll() {
        for (Thread t : pool) {
            t.start();
        }
    }

    public void setDelay(long delay) {
        for (FlooderThread t : pool) {
            t.flooder.setDelay(delay);
        }
    }

    public void setSocketTimeout(int timeout) {
        for (FlooderThread t : pool) {
            t.flooder.setTimeout(timeout);
        }
    }

    public void stopAll() {
        for (FlooderThread t : pool) {
            t.stopFlooding();
            t.interrupt();
        }
        pool.clear();
    }
}

class FlooderThread extends Thread {

    Flooder flooder;

    public FlooderThread(Flooder f) {
        super(f);
        flooder = f;
    }

    public void stopFlooding() {
        flooder.stop();
    }
}
