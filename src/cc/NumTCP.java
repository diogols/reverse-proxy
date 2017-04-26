package cc;

import java.net.InetAddress;
import java.util.concurrent.locks.ReentrantLock;

public class NumTCP {
    
    private ReentrantLock lock;
    private int numTCP;
    private InetAddress tcp;
    
    public NumTCP()
    {
        lock = new ReentrantLock();
        numTCP = 0;
    }
    
    public void incNumTCP()
    {
        lock.lock();
        try {
            numTCP++;
        } finally {
            lock.unlock();
        }
    }
    
    public InetAddress getTCP()
    {
        return tcp;
    }
    
    public void setTCP(InetAddress tcp)
    {
        this.tcp = tcp;
    }
    
    public int getNumTCP()
    {
        int res;
        lock.lock();
        try {
            res = numTCP;
        } finally {
            lock.unlock();
        }
        return res;
    }
    
}
