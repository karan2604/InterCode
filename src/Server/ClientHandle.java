package Server;

import CommonFiles.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandle{

    Socket socket=null;
    ObjectOutputStream out;
    ObjectInputStream in;

    ClientHandle(Socket socket)
    {
        this.socket=socket;
        execute();
    }

    public void execute()
    {
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    in=new ObjectInputStream(socket.getInputStream());
                    Message m=(Message) in.readObject();
                    System.out.println(m.getId());
                    System.out.println(m.getStatus());
                    System.out.println(m.getMessage());
                    System.out.println(m.getOthermanid());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        th.start();
    }


}
