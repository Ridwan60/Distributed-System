package ChatBox;

import java.io.IOException;
import java.util.HashMap;

public class CreateConnection implements Runnable {
    HashMap<String, Information> clientList;
    NetworkConnection nc;
    public CreateConnection(HashMap<String, Information> clientList, NetworkConnection nc) {
        this.clientList = clientList;
        this.nc = nc;
    }


    @Override
    public void run() {
        Object userObj = null;
        try {
            userObj = nc.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String username = (String) userObj;
        System.out.println("Username: " + username + " connected");
        clientList.put(username, new Information(username, nc));
        System.out.println("HashMap updated" + clientList);
        new Thread(new ReaderWriterServer(username, nc, clientList)).start();
    }
}
