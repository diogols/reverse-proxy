package reverse_proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP extends Thread{
    
    private int port;
    private NumTCP numTCP;
    
    public ServerTCP(int port, NumTCP n)
    {
        this.port = port;
        numTCP = n;
    }

    public void run()
    {
        ServerSocket s;
        Socket c;
        
        try {
            s = new ServerSocket(port);
       
            numTCP.setTCP(s.getInetAddress()); // Estamos a definir o ip do servidor TCP na classe partilhada NumTCP
            
            while((c = s.accept()) != null)
            {
                //qualquer coisa a ser feita na segunda parte do TP2
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
