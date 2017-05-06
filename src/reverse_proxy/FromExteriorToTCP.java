/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverse_proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class FromExteriorToTCP extends Thread {
    Socket exterior;
    Socket tcp;

    public FromExteriorToTCP(Socket exterior, Socket tcp) {
        this.exterior = exterior;
        this.tcp = tcp;
    }
    
    //neste momento so envia nunca recebe
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(exterior.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            
            OutputStreamWriter osr = new OutputStreamWriter(tcp.getOutputStream());
            PrintWriter pw = new PrintWriter(osr, true);
            
            String read;
            while((read = br.readLine()) != null) {
                System.out.println("fromexteriortotcp: " + read);
                pw.println(read);
            }
        } catch(IOException e) {

        }
    }
    
}
