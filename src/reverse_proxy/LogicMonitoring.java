package reverse_proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LogicMonitoring { 
    
    public static void main(String[] args) throws UnknownHostException, IOException
    {   
         
            int my_port = 5555; /* Integer.valueOf(args[0]); */
            
            byte[] receiveData;
            DatagramPacket receivePacket;
            DatagramSocket ds = new DatagramSocket(my_port);
            String message;
            String fields[];
            InetAddress ip;
                    
            /* posteriormente adicionar locks e colocar numa nova classe */
            //Map<InetAddress, Information> table = new TreeMap<>();
            Table table = new Table();
            
            LogicMonitoringThread lmt = new LogicMonitoringThread(ds, table);
            lmt.start();
            
            while(true) {
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                ds.receive(receivePacket);
                message = new String(receivePacket.getData());
                fields = message.split(" ");
                String s;
                System.out.println(message);
                if(fields[0].equals("init")) {
                    try {
                        s = fields[1].split("/")[0];
                    } catch(NumberFormatException e) {
                        s = fields[1];
                    }
                    ip = InetAddress.getByName(fields[1].split("/")[0]);
                  
                    Information i = new Information(receivePacket.getAddress(), receivePacket.getPort(), ip, 80/*Integer.parseInt(fields[2])*/);
                    table.put(receivePacket.getAddress(), i);
                   System.out.println(message);
                } else if(fields[0].equals("reply")) {
                    table.receivedPacket(receivePacket.getAddress(), Integer.parseInt(fields[1].trim()), Integer.parseInt(fields[2].trim()));
                } else if(fields[0].equals("automatic")) {
                    table.receivedPacket(receivePacket.getAddress(), Integer.parseInt(fields[1].trim()));
                }

            }
    }
}