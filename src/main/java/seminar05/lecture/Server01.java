package seminar05.lecture;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server01 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(11333);
            Socket socket = serverSocket.accept();

            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println("Hello from Server!");

            socket.close();
            serverSocket.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "/n" + e);
        }
    }
}
