package cc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Thread que trata de mandar PDU periodicamente. É criada no ProbingThread
*/

public class MonitorThread extends Thread{
    
    private static final int port = 5556;
    
    private InetAddress udp;
    private InetAddress tcp;
    private InetAddress server;
    private NumTCP numTCP;
    
    public MonitorThread(InetAddress udp, InetAddress tcp, InetAddress server, NumTCP numTCP)
    {
        this.udp = udp;
        this.tcp = tcp;
        this.server = server;
        this.numTCP = numTCP;
    }
    
    public void run()
    {
        try 
        {
            DatagramSocket ds;
            byte [] sendData;
            DatagramPacket sendPacket;
            String message;
            PDU pdu;
            
            ds = new DatagramSocket(5555);
            
            while(true)
            {
                pdu = new PDU(udp, tcp, numTCP.getNumTCP());
                message = pdu.getPDU();
                
                sendData = new byte[1024];
                sendData = message.getBytes();
                
                sendPacket = new DatagramPacket(sendData, sendData.length, server, port);
                ds.send(sendPacket);
                sleep(2500); // Manda PDu de 2,5 em 2,5 segundos
            }
        } catch (SocketException ex) {
            Logger.getLogger(MonitorThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MonitorThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MonitorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
