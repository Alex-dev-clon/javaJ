package seminar05.lecture;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client01 {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket client = new Socket(address, 11333);

            System.out.println(client.getInetAddress());
            System.out.println(client.getLocalPort());

            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            PrintStream printStream = new PrintStream(outputStream);

            printStream.println("Hello from Client!");
            System.out.println(dataInputStream.readLine());
            client.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "/n" + e);
        }
    }
}
