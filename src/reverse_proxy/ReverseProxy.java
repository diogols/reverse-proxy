/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverse_proxy;

/**
 *
 * @author Admin
 */
public class ReverseProxy {
    
    public static void main(String args[]) {
        Table table = new Table();
        LogicMonitoring lm = new LogicMonitoring(table);
        lm.start();
        ProxyLogic pl = new ProxyLogic(table);
        pl.start();
    }
}
