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
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class TCPServerThread extends Thread {
    private final Socket socket;
    private final Counter counter;
    
    TCPServerThread(Socket s, Counter c) {
        socket = s;
        counter = c;
    }
    
    public void run() {
        try {
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(isr);
            
        OutputStreamWriter osr = new OutputStreamWriter(socket.getOutputStream());
        PrintWriter pw = new PrintWriter(osr, true);
        String read;
        while((read = br.readLine()) != null) {
            // process
            pw.println(read);
        }
        } catch(IOException e) {
        }
    }
}
