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
public class FromTCPToExterior extends Thread {
    private Socket tcp;
    private Socket exterior;

    public FromTCPToExterior(Socket tcp, Socket exterior) {
       this.tcp = tcp;
       this.exterior = exterior;
    }
    
    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(tcp.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            OutputStreamWriter osr = new OutputStreamWriter(exterior.getOutputStream());
            PrintWriter pw = new PrintWriter(osr, true);

            String read;
            while((read = br.readLine()) != null) {
                pw.println(read);
            }
        } catch(IOException e) {

        }
    }
}
