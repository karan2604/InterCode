package BigServer;

import CommonFiles_With_BigServerAndClients.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

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
            //System.out.println("hey");

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
                    CompileHandle.storeinput("");
                    CompileHandle.deletefiles();
                    CompileHandle.checkCompile(content,lang);
                }
                else if(option.equals("run"))
                {
                    CompileHandle.storeinput(content);
                    CompileHandle.checkrun(lang);
                }
                else if(option.equals("NewClient"))
                {
                        Database database=Big_Server_Connection.getDatabase();
                        if(!database.CheckDuplicateId(m.getId()))
                            ExceptionIndatabase("ExceptionIndatabase");
                        else {
                            database.InsertNewClient(m.getFirstName(), m.getLastName(), m.getUserName(), m.getPassword(), m.getId());
                            ExceptionIndatabase("NoExceptionIndatabase");
                        }
                }
                else if(option.equals("Authentication"))
                {
                    Database database=Big_Server_Connection.getDatabase();
                    if(database.CheckAuthentication(m.getUserName(),m.getPassword()))
                        AuthenticationReply("AuthenticationCorrect");
                    else
                        AuthenticationReply("AuthenticationNotCorrect");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            ExceptionIndatabase("ExceptionIndatabase");
        }
    }

    private void ExceptionIndatabase(String s){
        Message m=new Message();
        m.setOption(s);
        try {
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void AuthenticationReply(String s)
    {
        Message m=new Message();
        m.setOption(s);
        try {
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
