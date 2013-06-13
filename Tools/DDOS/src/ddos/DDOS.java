/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ddos;

import Attacker.Attacker;
import ddos.server.Master;
import ddos.slave.Slave;

/**
 *
 * @author bruno
 */
public class DDOS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length < 1) {
            printHelp();
        }
        else if ("--slave".equals(args[0])) {
            Slave.main(args);
        }
        else if ("--master".equals(args[0])) {
            Master.main(args);
        }
        else if ("--attacker".equals(args[0])) {
            Attacker.main(args);
        }
        else if ("--help".equals(args[0])) {
            printHelp();
        }
        else {
            printHelp();
        }
        
    }
    
    public static void printHelp() {
        System.out.println("java -jar ddos.jar [ --slave | --master | --attacker ]");
    }
}
