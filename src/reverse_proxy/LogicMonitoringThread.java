/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverse_proxy;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

/**
 *
 * @author Admin
 */
public class LogicMonitoringThread extends Thread {
    private Table t;
    DatagramSocket ds;
    
    LogicMonitoringThread(DatagramSocket ds, Table t) {
        this.ds = ds;
        this.t = t;
    }
    
    public void run() {
        String message = "signal"; // Não interessa conteúdo desta mensagem
        DatagramPacket sendPacket;
        byte[] sendData;
        sendData = new byte[1024];
        sendData = message.getBytes();
        
        while(true) {
            try {
            List<InetAddress> l = t.getAddresses();
            
            for(InetAddress address: l) {
                            System.out.println("im here");

                sendPacket = new DatagramPacket(sendData, sendData.length, address, 5555);
                t.sentPacket(address);
                ds.send(sendPacket);
            }
            Thread.sleep(5000); // Manda-se probing de 5 em 5 segundos
            } catch(Exception e) {
                
            }
        }
    }
}