package ChatBox;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReaderWriterServer implements Runnable {

    String username;
    NetworkConnection nc;
    HashMap<String, Information> clientList;

    public ReaderWriterServer(String username, NetworkConnection nc, HashMap<String, Information> clientList) {
        this.username = username;
        this.nc = nc;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        Object obj = null;
        try {
            obj = nc.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Data dataObj = (Data)obj;
        String actualMessage = dataObj.message;
        System.out.println(actualMessage);
        if (actualMessage.toLowerCase().contains("list")) {
            System.out.println("List asked.." + actualMessage);
            String words[] = actualMessage.split("\\$");
                /*
                words[0] = Sender Name
                words[1] = Receiver Name
                words[2] = keyword
                words[3] = message/null
                */
            System.out.println("Client List: \n" + clientList);
            Information info = clientList.get(words[0]);
            String msgToSend = new String("List of Clients...\n");
            for (Map.Entry<String, Information> entry : clientList.entrySet()) {
                String key = entry.getKey();
                //Information value = entry.getValue();
                msgToSend = new String(msgToSend + key + "\n");
                //System.out.println(key);
            }
            Object object = msgToSend;
            System.out.println("sending.." + msgToSend);
            System.out.println("words0: " + words[0]);
            try {
                info.netConnection.write(msgToSend);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //String messageToSend=username+" -> "+sendMsg;
            //Data data=new Data();
            //data.message=messageToSend;
        }
        if (actualMessage.toLowerCase().contains("ip")){
            String words[] = actualMessage.split("\\$");
                /*
                words[0] = Sender Name
                words[1] = Receiver Name
                words[2] = keyword = ip
                words[3] = message/null
                */
            System.out.println("Client List: \n" + clientList);
            Information info = clientList.get(words[0]);
            String msgToSend = new String("Your PORT: \n");
            msgToSend+=info.netConnection.getSocket().getLocalAddress().getHostAddress();
            Object object = msgToSend;
            System.out.println("sending.." + msgToSend);
            System.out.println("words0: " + words[0]);
            try {
                info.netConnection.write(msgToSend);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (actualMessage.toLowerCase().contains("send")){
            String words[] = actualMessage.split("\\$");
                /*
                words[0] = Sender Name
                words[1] = Receiver Name
                words[2] = keyword = send
                words[3] = message
                */
            Information info = clientList.get(words[1]);
            String msgToSend = words[0]+" says: " + words[3];
            Object object = msgToSend;
            System.out.println("sending.." + msgToSend);
            System.out.println("words0: " + words[0]);
            try {
                info.netConnection.write(msgToSend);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
