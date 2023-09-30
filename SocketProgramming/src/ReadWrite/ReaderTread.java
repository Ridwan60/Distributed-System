package ReadWrite;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReaderTread implements Runnable{

    ObjectInputStream ois;
    String name;
    ReaderTread(ObjectInputStream ois, String name){
        this.ois = ois;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true) {

            try {
                Object cMsg = ois.readObject();
                System.out.println(name + " received " + (String) cMsg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
