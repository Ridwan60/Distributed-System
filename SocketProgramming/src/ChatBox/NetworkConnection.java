package ChatBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkConnection {
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public NetworkConnection(Socket socket) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public NetworkConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    public void write(Object obj) throws IOException {
        oos.writeObject(obj);
    }

    public Object read() throws IOException, ClassNotFoundException {
        Object obj = ois.readObject();
        return obj;
    }

    public Socket getSocket(){
        return socket;
    }
}
