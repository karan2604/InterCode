package ClientsFiles;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BigServerSocketDetails {
    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        BigServerSocketDetails.socket = socket;
    }

    public static ObjectInputStream getIn() {
        return in;
    }

    public static void setIn(ObjectInputStream in) {
        BigServerSocketDetails.in = in;
    }

    public static ObjectOutputStream getOut() {
        return out;
    }

    public static void setOut(ObjectOutputStream out) {
        BigServerSocketDetails.out = out;
    }
}
