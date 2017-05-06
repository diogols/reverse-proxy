package reverse_proxy;

import java.net.InetAddress;
import java.time.LocalTime;

public class Information {
    private final int timeout = 10;
    private final InetAddress udp_ip;
    private final int udp_port;
    private final InetAddress tcp_ip;
    private final int tcp_port;
    
    private int number_tcp;
    private float sum_rtt;
    private LocalTime time_sent, time_arrived;
    private int sent_packets, received_packets;
    private int last_received;
    
    public Information(InetAddress udp_ip, int udp_port, InetAddress tcp_ip, int tcp_port)
    {
        this.udp_ip = udp_ip;
        this.udp_port = udp_port;
        this.tcp_ip = tcp_ip;
        this.tcp_port = tcp_port;
        number_tcp = 0;
        time_sent = LocalTime.now();
        time_arrived = time_sent.plusSeconds(timeout);
        sent_packets = 0;
        sum_rtt = 0;
        received_packets = 0;
        last_received = 0;
    }

    public int getNumberTCP() {
        return number_tcp;
    }
    
    public void receivedPacket(int sequence_number, int number_tcp) {
        LocalTime now = LocalTime.now();
        if(sequence_number == sent_packets) {
            if(now.isBefore(time_arrived)) {
                setTimeArrived();
                received_packets++;
            }
        }
        if(sequence_number >= last_received) {
            setNumberTCP(number_tcp);
            last_received = sequence_number;
        }
                System.out.println("time sent: " + time_sent + " - time received: " + time_arrived);
                System.out.println("received packets: " + received_packets + " - sent packets:" + sent_packets);
                System.out.println("last received: " + last_received + " - sequence number:" + sequence_number);

    }
    
    public void receivedPacket(int number_tcp) {
        setNumberTCP(number_tcp);
    }
    
    public void sentPacket() {
        sent_packets++;
        setTimeSent();
    }
    
    private void setNumberTCP(int n) {
        this.number_tcp = n;
    }
    
    private void setTimeSent() {
        time_sent = LocalTime.now();
        time_arrived = time_sent.plusSeconds(timeout);
    }
    
    private void setTimeArrived() {
        time_arrived = LocalTime.now();
        sum_rtt += time_arrived.getNano() - time_sent.getNano();
    }

    public float getRoundTripTime() {
        return sum_rtt / received_packets;
    }

    public InetAddress getUDP() {
        return udp_ip;
    }
    
    public int getUDP_Port() {
        return udp_port;
    }

    public float getRatioPacketLoss() {
        return 1 - (received_packets / sent_packets);
    }
    
    public float getPontuation() {
        return (float) (getRatioPacketLoss() + getRoundTripTime()/1000 + getNumberTCP()); 
    }
}
