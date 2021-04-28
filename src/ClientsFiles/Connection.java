package ClientsFiles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    static int port = 9191;
    Socket s;
    String address = "localhost";
    ServerSocket ss;
    public Socket serverClientConnection(){
        try {
            ss = new ServerSocket(port);
            s = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public Socket clientSocketConnection(){
        try {
            s = new Socket(address,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public int getPort(){
        return port;
    }
    public String getAddress(){
        return address;
    }
}
