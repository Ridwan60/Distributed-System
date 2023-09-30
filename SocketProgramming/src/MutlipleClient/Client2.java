package MutlipleClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client2 {



    public static void main(String[] args) throws IOException {
        System.out.println("MutlipleClient.Client started...");
        Socket socket = new Socket("127.0.0.1", 2222);
        System.out.println("MutlipleClient.Client connected...");

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        while (true) {
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();

            if(message.equals("exit")){
                break;
            }

            //sent to server...
            oos.writeObject(message);

            try {
                //receive from server..
                Object fromServer = ois.readObject();
                System.out.println("From MutlipleClient.Server: " + (String) fromServer);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        socket.close();

    }
}

