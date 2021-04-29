package BigServer;

import CommonFiles_With_BigServerAndClients.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandle extends Thread{

    private  Socket socket;
    private  ObjectOutputStream out;
    private  ObjectInputStream in;
    private int ID;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ClientHandle(Socket socket) {     //constructor
        this.socket = socket;
        this.start();
    }

    public void run()
    {
        try {
            in=new ObjectInputStream(socket.getInputStream());
            out=new ObjectOutputStream(socket.getOutputStream());
            System.out.println(out);
            ID=ClientDetails.addclient(this);
            System.out.println("hey");

            for(ClientHandle clientHandle:ClientDetails.getClisntlist())
                System.out.println(clientHandle.getSocket());

           while(true)
            {
                Message m=(Message) in.readObject();
                String option=m.getOption();
                String content=m.getContent();
                String lang=m.getLang();
                //System.out.println(content);
                //System.out.println(lang);
                if(option.equals("compile"))
                {
                    InterCodeServer.storeinput("");
                    InterCodeServer.deletefiles();
                    InterCodeServer.checkCompile(content,lang);
                }
                else
                {
                    InterCodeServer.storeinput(content);
                    InterCodeServer.checkrun(lang);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
