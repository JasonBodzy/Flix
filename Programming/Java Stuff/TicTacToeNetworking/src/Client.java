import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends ClientGUI implements Runnable {

    private static Socket clientSocket;
    private GameManager gameManager = new GameManager();

    ClientGUI clientGUI = new ClientGUI();

    private static Socket connectToServer() {
        try {
            clientSocket = new Socket("localhost", 4242);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return clientSocket;
    }

    private void protocol() {

        try {
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream());
            System.out.println(clientReader.readLine());
            System.out.println("Player ");
            myClientGUI.drawBoard(gameManager.getBoard());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void run() {

    }

    public void setup() {
        clientSocket = connectToServer();
        Client client = new Client();
        client.protocol();
        SwingUtilities.invokeLater(new Client());
    }

    public static void main(String[] args) {
        clientSocket = connectToServer();
        Client client = new Client();
        client.protocol();
        SwingUtilities.invokeLater(new Client());
    }





}
