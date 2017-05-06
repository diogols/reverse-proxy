/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverse_proxy;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class ProxyLogic extends Thread {
    Table table;
    
    public ProxyLogic(Table t) {
        table = t;
    }
    
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(80);
            Socket s;
            
            while((s = ss.accept()) != null) {
                Socket tcp = new Socket(getTCPServer(table), 80);
                
                FromExteriorToTCP fett = new FromExteriorToTCP(s, tcp);
                fett.start();
                FromTCPToExterior ttfe = new FromTCPToExterior(tcp, s);
                ttfe.start();
            }
        } catch(Exception e) {
            System.err.println("An error ocurred at ProxyLogic.");
        }
    }
    
    private InetAddress getTCPServer(Table t) {
        InetAddress r = null;
        float pontuation = 9999;
        for(Information i : table.getInformations()) {
            if(i.getPontuation() < pontuation) {
                pontuation = i.getPontuation();
                r = i.getTCP_Address();
            }
        }
        return r;
    }
}
