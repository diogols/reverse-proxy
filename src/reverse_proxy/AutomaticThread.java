package reverse_proxy;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;

public class AutomaticThread extends Thread{
    
    private final DatagramSocket ds;
    private final InetAddress tcp_ip;
    private final InetAddress server_ip;
    private final int server_port;
    private final Counter counter;
    
    public AutomaticThread(DatagramSocket ds, InetAddress tcp_ip, InetAddress server_ip, int server_port, Counter counter) {
        this.ds = ds;
        this.tcp_ip = tcp_ip;
        this.server_ip = server_ip;
        this.server_port = server_port;
        this.counter = counter;
    }
    
    public void run() {
        try  {
            byte [] sendData;
            DatagramPacket sendPacket;
            String message;
            while(true) {
                message = "automatic " + counter.get() + " " + LocalTime.now(); //futuramente será utilizado para ter o número de tcp correto em Information
                
                sendData = new byte[1024];
                sendData = message.getBytes();
                
                sendPacket = new DatagramPacket(sendData, sendData.length, server_ip, server_port);
                ds.send(sendPacket);
                Thread.sleep(2500);
            }
        } catch (Exception e) {
        }
    }
    
}
