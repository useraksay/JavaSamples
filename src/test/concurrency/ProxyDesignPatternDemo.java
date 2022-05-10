package test.concurrency;

import java.util.ArrayList;
import java.util.List;

public class ProxyDesignPatternDemo {
    public static void main(String[] args) {
        Server proxyServer = new ProxyServer();
        proxyServer.connect("google.com");
        proxyServer.connect("banned.com");
    }
}

interface Server{
    void connect(String url);
}

class RealServer implements Server{
    @Override
    public void connect(String url) {
        System.out.println("Connected to " + url);
    }
}

class ProxyServer implements Server{
    static List<String> bannedSites = new ArrayList<>();
    RealServer realServer = new RealServer();

    static {
      bannedSites.add("banned.com");
    }

    @Override
    public void connect(String url) {
       if(bannedSites.contains(url)){
           return;
       }
       realServer.connect(url);
    }
}
