/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverse_proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author dy
 */
public class TestClient {
    
    public  static void main(String args[]) {
        try {
            InetAddress server = InetAddress.getByName(args[0]);
            Socket s = new Socket(server, 80);
            Scanner read = new Scanner(System.in);
            
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            OutputStreamWriter osr = new OutputStreamWriter(s.getOutputStream());
            PrintWriter pw = new PrintWriter(osr, true);
            
            while(true) {
                String send = read.next();
                pw.println(send);
                System.out.println(br.readLine());
            }
        } catch(Exception e) {
        }
    }
}
