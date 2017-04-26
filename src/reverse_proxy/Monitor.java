package reverse_proxy;

import java.net.InetAddress;

public class Monitor {
    
    private static int port = 5556;
    

    public static void main(String args[]) throws Exception
    { 
        int port =  Integer.valueOf(args[0]);
        InetAddress ip = InetAddress.getByName(args[1]);
        int server_port = Integer.valueOf(args[2]);
         
        ProbingThread  pt = new ProbingThread(ip, port, server_port);
        pt.start();
    }
}
