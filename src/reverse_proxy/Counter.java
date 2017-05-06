package reverse_proxy;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    
    private final ReentrantLock rl;
    private int counter;
    
    public Counter()
    {
        rl = new ReentrantLock();
        counter = 0;
    }
    
    public Counter(int initial_value)
    {
        rl = new ReentrantLock();
        counter = initial_value;
    }
    
    public void increment()
    {
        rl.lock();
        try {
            counter++;
        } finally {
            rl.unlock();
        }
    }
    
    public void decrement()
    {
        rl.lock();
        try {
            counter--;
        } finally {
            rl.unlock();
        }
    }
    
    public int get() {
        return counter;
    }
    
}
