package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    //public static void main(String[] args) {
      //      new Main().startserver();
    //}

    public void startserver()
    {
        System.out.println("Server Started");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket server=new ServerSocket(8063);
                    //while(true)
                    //{
                        new ClientHandle(server.accept());
                    //}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
