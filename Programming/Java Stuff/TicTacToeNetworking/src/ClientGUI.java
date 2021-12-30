import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI extends JComponent implements Runnable {
    public JFrame frame;
    public Graphics2D graphics2D;
    private Image image;
    ClientGUI myClientGUI;

    private char[][] board;
    private int playerNum;

    private static Socket clientSocket;
    private GameManager gameManager = new GameManager();

    static Client client = new Client();

    public ClientGUI() {
        addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());
                processClick(e.getX(), e.getY());

                drawBoard(client.);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                drawBoard(gameManager.getBoard());
            }
        });
    }


    public void paintComponent(Graphics g) {
        if (image == null) {
            System.out.println("instantiation");
            image = createImage(getWidth(), getHeight());

            graphics2D = (Graphics2D) image.getGraphics();

            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics2D.setPaint(Color.white);
            graphics2D.fillRect(0, 0, getWidth(), getHeight());

            graphics2D.setPaint(Color.black);
            graphics2D.setStroke(new BasicStroke(5));

            repaint();
        } else {
            g.drawImage(image, 0, 0, null);
        }
        g.setColor(Color.black);
        // Vertical Lines
        g.drawLine(200, 0, 200, 300);
        g.drawLine(100, 0, 100, 300);

        // Horizontal Lines
        g.drawLine(0, 100, 300, 100);
        g.drawLine(0, 200, 300, 200);

    }

    public void processClick(int x, int y) {

    }

    public void drawX(int index) {
        index += 1;

        int startX = 10;
        int startY = 10;

        for (int i = 1; i < index; i++) {
            startX += 100;
            if (i % 3 == 0) {
                startY += 100;
                startX = 10;
            }
        }

        int endX = startX + 70;
        int endY = startY + 70;

        graphics2D.drawLine(startX, startY, endX, endY);
        graphics2D.drawLine(endX, startY, startX, endY);
        repaint();
    }

    public void drawO(int index) {
        index += 1;

        int startX = 10;
        int startY = 10;

        for (int i = 1; i < index; i++) {
            startX += 100;
            if (i % 3 == 0) {
                startY += 100;
                startX = 10;
            }
        }

        graphics2D.drawOval(startX, startY, 70, 70);
        repaint();
    }

    public void drawBoard(char[][] board) {
        int index = 0;
        for (char[] C : board) {
            for (char c : C) {
                if (c == 'x') {
                    drawX(index);
                } else if (c == 'o') {
                    drawO(index);
                }
                index++;
            }
        }
    }

    @Override
    public void run() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(300, 330);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        Container content = frame.getContentPane();
        myClientGUI = new ClientGUI();
        content.add(myClientGUI, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
        client.setup();


    }


}
