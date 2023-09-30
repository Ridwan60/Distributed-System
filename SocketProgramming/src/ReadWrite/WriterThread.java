package ReadWrite;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class WriterThread implements Runnable{
    private ObjectOutputStream oos;
    private String name;

    WriterThread(ObjectOutputStream oos, String name){
        this.oos = oos;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            String message = sc.nextLine();

            if (message.equals("exit")) {
                break;
            }

            //sent to server...
            try {
                oos.writeObject(message);
                System.out.println("Message sent from " + name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
