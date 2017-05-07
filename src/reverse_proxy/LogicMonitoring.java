package reverse_proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class LogicMonitoring extends Thread { 
    private Table table;
    
    public LogicMonitoring(Table t) {
        table = t;
    }
    
    @Override
    public void run() {   
            int my_port = 5555; /* Integer.valueOf(args[0]); */
            
            byte[] receiveData;
            DatagramPacket receivePacket;
            try {
                DatagramSocket ds = new DatagramSocket(my_port);
                String message;
                String fields[];
                InetAddress ip;
                
                LogicMonitoringThread lmt = new LogicMonitoringThread(ds, table);
                lmt.start();

                while(true) {
                    receiveData = new byte[1024];
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    ds.receive(receivePacket);
                    message = new String(receivePacket.getData());
                    fields = message.split(" ");
                    String s;
                    switch (fields[0]) {
                        case "init":
                            try {
                                s = fields[1].split("/")[0];
                            } catch(NumberFormatException e) {
                                s = fields[1];
                            }
                            ip = InetAddress.getByName(fields[1].split("/")[0]);
                            //onde está receivePacket.getAdress() no tcp devia ser ip
                            Information i = new Information(receivePacket.getAddress(), receivePacket.getPort(), receivePacket.getAddress(), Integer.parseInt(fields[2].trim()));
                            table.put(receivePacket.getAddress(), i);
                            System.out.println(message);
                            break;
                        case "reply":
                            table.receivedPacket(receivePacket.getAddress(), Integer.parseInt(fields[1].trim()), Integer.parseInt(fields[2].trim()));
                            break;
                        case "automatic":
                            table.receivedPacket(receivePacket.getAddress(), Integer.parseInt(fields[1].trim()));
                            break;
                        default:
                            break;
                    }
                }
        } catch(IOException | NumberFormatException e) {

        }
    }
}