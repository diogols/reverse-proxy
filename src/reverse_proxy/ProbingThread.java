package reverse_proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Thread que trata de receber os pedidos de probing e responder. É criada no Monitor
*/

public class ProbingThread extends Thread{
    
    private InetAddress my_ip; // IP do monitorUDP
    private int my_port;
    private InetAddress server_ip; // IP do servidor de monitorização
    private int server_port;
    
    public ProbingThread(InetAddress server, int port, int server_port)
    {
        this.my_port = port;
        this.server_ip = server;
        this.server_port = server_port;
    }
    
    public void run()
    {
        // Depois vai ler e esperar pelas mensagens de probing e responder
        InetAddress tcp_ip;
        try {
            my_ip = InetAddress.getByName("localhost"); // IP do monitorUDP
            
            NumTCP numTCP = new NumTCP(); /* Zona partilhada entre MonitorUDP e ServidorTCP com o número
                                             de conexões TCP */
            ServerTCP tcp = new ServerTCP(my_port, numTCP);
            tcp.start();
            
            tcp_ip = numTCP.getTCP(); // IP do servidor TCP criado
            
            MonitorThread pt = new  MonitorThread(my_ip, tcp_ip, server_ip, numTCP);
            pt.start();
            
            DatagramSocket ds = new DatagramSocket(my_port);
            
            DatagramPacket receivePacket;
            byte [] receiveData;
            DatagramPacket sendPacket;
            byte [] sendData;
            String message;
            
            message = my_ip.toString();
            sendData = new byte[1024];
            sendData = message.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, server_ip, server_port);
            ds.send(sendPacket);
   
            while(true)
            {
                // Recebe pedido de probing
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                ds.receive(receivePacket);
                
                String sentence = new String(receivePacket.getData());
                System.out.println("monitor: " + sentence + " port: " + receivePacket.getPort());
                // Manda resposta ao pedido de probing

                message = my_ip + " " + tcp_ip + " "+ numTCP.getNumTCP();
                 
                sendData = new byte[1024];
                sendData = message.getBytes();
                
                sendPacket = new DatagramPacket(sendData, sendData.length, server_ip, server_port);
                ds.send(sendPacket);
            }
        } catch (Exception e) {
           
        }
    }
}
