package cc;

import java.net.InetAddress;

public class PDU {
    
    private String pdu;
    
    public PDU( InetAddress ip, InetAddress ip_tcp, int num_tcp )
    {
        //ip é o ip do MonitorUDP
        pdu = ip + " " + ip_tcp + " "+ num_tcp;
    }
    
    public String getPDU()
    {
        return pdu;
    }
}
