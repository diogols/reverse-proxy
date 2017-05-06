package reverse_proxy;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


public class Table {
    
    private final ReentrantLock l;
    private Map<InetAddress, ReentrantLock> rl;
    private Map<InetAddress, Information> table;
    
    public Table()
    {
        l = new ReentrantLock();
        rl = new HashMap<>();
        table = new HashMap<>();
    }
    
    public List<Information> getInformations() {
        List<Information> r = new LinkedList();
        l.lock();
        try {
            r.addAll(table.values());
        } finally {
            l.unlock();
        }
        return r;
    }
    
    public Information getInformation(InetAddress udp_ip) { 
        Information res;
        
        rl.get(udp_ip).lock();
        try {
            res = table.get(udp_ip);
        } finally {
            rl.get(udp_ip).unlock();
        }
        
        return res;
    }
    
    public void put(InetAddress udp_ip, Information i) { 
        try {
            l.lock();
            ReentrantLock lock = new ReentrantLock();
            rl.put(udp_ip, lock);
            table.put(udp_ip, i);
        } finally {
            l.unlock();
        }
    }
    
    public void receivedPacket(InetAddress udp_ip, int number_tcp, int sequence_number) {
        rl.get(udp_ip).lock();
        try {
            table.get(udp_ip).receivedPacket(sequence_number, number_tcp);
        } finally {
            rl.get(udp_ip).unlock();
        }
    }
    
    public void receivedPacket(InetAddress udp_ip, int number_tcp) {
        rl.get(udp_ip).lock();
        try {
            table.get(udp_ip).receivedPacket(number_tcp);
        } finally {
            rl.get(udp_ip).unlock();
        }
    }
    
    public void sentPacket(InetAddress udp_ip) {
        rl.get(udp_ip).lock();
        try {
            table.get(udp_ip).sentPacket();
        } finally {
            rl.get(udp_ip).unlock();
        }
    }

    List<InetAddress> getAddresses() {
        List<InetAddress> list = new LinkedList();
        l.lock();
        try {
            list.addAll(table.keySet());
        } finally {
            l.unlock();
        }
        return list;
    }
    
    int getLastPacketSent(InetAddress ip) {
        int r;
        rl.get(ip).lock();
        try {
            r = table.get(ip).getLastSentPacket();
        } finally {
            rl.get(ip).unlock();
        }
        return r;
    }
    
    int getUDP_Port(InetAddress ip) {
        int r;
        rl.get(ip).lock();
        try {
            r = table.get(ip).getUDP_Port();
        } finally {
            rl.get(ip).unlock();
        }
        return r;
    }
}
