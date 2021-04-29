package BigServer;

import java.util.ArrayList;

public class ClientDetails {

    private static ArrayList<ClientHandle> clisntlist;
    private static int clientid;

    public static ArrayList<ClientHandle> getClisntlist() {
        return clisntlist;
    }

    public static void setClisntlist(ArrayList<ClientHandle> aclisntlist) {
        clisntlist = aclisntlist;
        clientid=1;
    }

    public static int getClientid() {
        return clientid;
    }

    public static void setClientid(int clientid) {
        ClientDetails.clientid = clientid;
    }

    public static int addclient(ClientHandle clientHandle)
    {
        clisntlist.add(clientHandle);
        return clientid++;
    }
}
