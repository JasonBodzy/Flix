import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private Server server;
    private int players = 0;
    private static Socket player1Socket;
    private static Socket player2Socket;

    private BufferedReader reader1;
    private PrintWriter writer1;

    private BufferedReader reader2;
    private PrintWriter writer2;

    private void flushAndClose() {
        try {
            writer1.flush();
            writer1.close();
            reader1.close();

            writer2.flush();
            writer2.close();
            reader2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized int assignPlayer() {
        System.out.println(this.players);
        this.players += 1;
        return this.players;
    }

    private void initializeIO() {
        System.out.println("IO initialized");
        try {
            if (this.players == 0) {
                reader1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
                writer1 = new PrintWriter(player1Socket.getOutputStream());
            } else if (this.players == 1) {
                reader2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
                writer2 = new PrintWriter(player2Socket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void protocol() {
        System.out.println("protocol");
        if (reader1 != null) {
            writer1.println(assignPlayer());
        } else if (writer2 != null) {
            writer2.println(assignPlayer());
        }
    }

    @Override
    public void run() {
        System.out.println("Running a thread!");
        initializeIO();
        protocol();

    }

    public static void main(String[] args) {
        Server server = new Server();
        Thread player1Thread = new Thread(new Server());
        Thread player2Thread = new Thread(new Server());

        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            player1Socket = serverSocket.accept();
            System.out.println("Connected p1");
            player1Thread.start();

            player2Socket = serverSocket.accept();
            System.out.println("Connected p2");
            player2Thread.start();

            player1Thread.join();
            player2Thread.join();

            server.flushAndClose();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
