package reverse_proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server { 
    
    public static void main(String[] args)
    {   
        try { 
            int port = Integer.valueOf(args[0]);
            Table table = new Table();
            ProbingSender ps = new ProbingSender(table);
            //ps.start();

            byte[] receiveData;
            DatagramPacket receivePacket;
            DatagramSocket ds = new DatagramSocket(port);
            
            while(true)
            {
                // Recebemos resposta de probing
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                ds.receive(receivePacket);
                
                String sentence = new String(receivePacket.getData());
                System.out.println("servidor: " + sentence);
                // Calculamos tempo de chegada do pacote
               
                /*
                LocalTime l = LocalTime.now();

                String[] components = sentence.split(" ");
                InetAddress udp, tcp;
                int numtcp;

                
                udp = InetAddress.getByName(components[0]);
                tcp = InetAddress.getByName(components[1]);
                numtcp = Integer.parseInt(components[2]);

                if(table.containsTCP(tcp) == true)
                {
                 
                    table.update(tcp, numtcp, l.getNano());
                }
                else
                {
                   
                    InfoMonitor info = new InfoMonitor(udp, 0, 0, 0, 0, 0, 0);
                    table.add(tcp, info);
                }*/
            }
        } catch (Exception e) {
            System.err.println("An error ocurred!");
        }      

    }
        
}
