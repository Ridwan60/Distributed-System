package MutlipleClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


class ServerTread implements Runnable {

    Socket clientSocket;
    Thread t;

    ServerTread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            while (true) {
                Object cMsg = ois.readObject();

                if (cMsg == null) break;
                System.out.println("From MutlipleClient.Client: " + (String) cMsg);

                String serverMsg = (String) cMsg;
                serverMsg = serverMsg.toUpperCase();
                oos.writeObject(serverMsg);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(2222);
        System.out.println("MutlipleClient.Server started....");

        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("MutlipleClient.Client connected....");
            new ServerTread(socket);

        }
    }
}
