/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
public class Master {

    
    private AttackerAccepter aa;
    private SlaveAccepter sa;
    private boolean closing;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       System.out.println("Master - MAIN - START");
        
       new Master().start();

       System.out.println("Master - MAIN - END");

    }

    Master() {
        this.closing = false;
        this.aa = new AttackerAccepter(this);
        this.sa = new SlaveAccepter(this);
        
        
    }
    
    public void start() {
        new Thread(this.aa).start();
        new Thread(this.sa).start();
    }
    
    public void close() throws IOException {
        this.closing = true;
        this.sa.close();
        this.aa.close();
    }
    
    public boolean isClosing() {
        return this.closing;
    }

    public void send(String s) {
        this.sa.send(s);
    }
}
