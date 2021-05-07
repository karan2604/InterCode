package BigServer;

import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Big_Server_Connection {

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        Big_Server_Connection.database = database;
    }

    private static Database database;

    public static void main(String[] args) {

        database=new Database(); //Connection with Database
        database.connect();

        //System.exit(1);
        System.out.println("Big Server Started");
        ClientDetails.setClisntlist(new ArrayList<>());
        try {
            ServerSocket server=new ServerSocket(8063);   //Connection with Client
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while(true)//for(int i=0;i<2;i++)
                    {
                        try {
                            System.out.println("client accepted");
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
