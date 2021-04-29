package BigServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Big_Server_Connection {


    public static void main(String[] args) {
        System.out.println("Big Server Started");
        ClientDetails.setClisntlist(new ArrayList<>());
        try {
            ServerSocket server=new ServerSocket(8063);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    for(int i=0;i<2;i++)
                    {
                        try {
                            System.out.println("client accepted"+i);
                            new ClientHandle(server.accept());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
