/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverse_proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class TCPServer extends Thread {
    private Counter counter;
    
    TCPServer(Counter c) {
        counter = c;
    }
    
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(80);
            Socket s;
            while((s=ss.accept()) != null) {
                counter.increment();
                TCPServerThread tcpst = new TCPServerThread(s, counter);
                tcpst.start();
            }
        } catch(IOException e) {
        }
    }
}
