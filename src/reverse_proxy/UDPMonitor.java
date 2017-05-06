package reverse_proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMonitor {
    
    public static void main(String args[]) throws Exception
    {
        /*
        InetAddress udp_ip = InetAddress.getByName("localhost");
        int udp_port =  Integer.valueOf(args[0]);
        InetAddress tcp_ip = InetAddress.getByName(args[1]);
        int tcp_port =Integer.valueOf(args[2]);
        InetAddress server_ip = InetAddress.getByName(args[3]);
        int server_port = Integer.valueOf(args[4]);
        */
        
        //debugging
        InetAddress udp_ip = InetAddress.getByName("localhost");
        int udp_port =  5555;
        InetAddress tcp_ip = InetAddress.getByName("localhost");
        int tcp_port = 80;
        InetAddress server_ip = InetAddress.getByName(args[0]);
        int server_port = 5555;
        try {
            DatagramSocket ds = new DatagramSocket(udp_port);
            
            Counter counter = new Counter(0);
        
            DatagramPacket receivePacket;
            byte [] receiveData;
            DatagramPacket sendPacket;
            byte [] sendData;
            String message;
            
            message = "init " + tcp_ip.toString() + " " + tcp_port;
            sendData = new byte[1024];
            sendData = message.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, server_ip, server_port);
            ds.send(sendPacket);
            
             // lançar tcp com counter
            AutomaticThread at = new AutomaticThread(ds, tcp_ip, server_ip, server_port, counter);
            at.start();
            
            int sequence_number = 0;
            
            while(true)
            {
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                ds.receive(receivePacket);
                
                message = new String(receivePacket.getData());
                sequence_number++;
                message = "reply " + counter.get() + " " + sequence_number;
                 
                sendData = new byte[1024];
                sendData = message.getBytes();
                
                sendPacket = new DatagramPacket(sendData, sendData.length, server_ip, server_port);
                ds.send(sendPacket);
            }
        } catch (IOException e) {
            System.err.println("An error ocurred at UDPMonitor.");
        }
    }
}
