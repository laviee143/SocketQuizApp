import java.net.ServerSocket;
import java.net.Socket;

public class QuizServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Quiz Server is running on port 5000...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Each client handled in a separate thread
                ClientHandler clientThread = new ClientHandler(socket);
                clientThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
