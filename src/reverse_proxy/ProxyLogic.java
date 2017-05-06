/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverse_proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProxyLogic extends Thread {
    Table table;
    
    public ProxyLogic(Table t) {
        table = t;
    }
    
    @Override
    public void run() {
        
        try {
            ServerSocket ss = new ServerSocket(80);
            Socket s;
            
            while((s = ss.accept()) != null) {
                Socket tcp = new Socket(getTCPServer(table), 80);
                
                FromExteriorToTCP fettcp = new FromExteriorToTCP(s, tcp);
                fettcp.start();
                FromTCPToExterior ftcpte = new FromTCPToExterior(tcp, s);
                ftcpte.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ProxyLogic.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println(r.toString());
        return r;
    }
}
