package sample;

import CommonFiles.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Method {

    public ObjectOutputStream out;
    public ObjectInputStream in;

    public void connect(String username){

        Socket socket= null;
        try {
            socket = new Socket("127.0.0.1",8063);
            out=new ObjectOutputStream(socket.getOutputStream());
            Message m=new Message();
            m.setStatus(username);
            m.setId(username+"123");
            m.setOthermanid(username+"toher");
            m.setMessage(username+"mes");
            out.writeObject(m);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void TextEditor(String msg)
    {
        Message m=new Message();
        m.setStatus("CompileTextArea");
        m.setMessage(msg);
        //out.writeObject(m);
        //out.flush();
    }
}
