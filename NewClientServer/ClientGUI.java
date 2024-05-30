package NewClientServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {
    private static final int POS_X = 100;
    private static final int POS_Y = 100;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private ServerWindow serverWindow;
    private JButton btnSend = new JButton("send");
    private JButton btnLogin = new JButton("login");
    private JTextArea log = new JTextArea(7, 33);
    private JPanel buttonPanel = new JPanel();
    private JPanel textPanel = new JPanel();
    private JPanel upperPanel = new JPanel(new GridLayout(2, 3));
    private JPasswordField fieldPassw = new JPasswordField("qwerty");
    private JTextField fieldLogin = new JTextField("");
    private JTextField fieldPort = new JTextField("8080");
    private JTextField fieldIp = new JTextField("127.0.0.1");

    public ClientGUI(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
        // setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chat client");
        setResizable(false);
        setAlwaysOnTop(true);

        // setLocation(serverWindow.getX()-500,
        // serverWindow.getY());

        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestToTurnServer();
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        upperPanel.add(fieldIp);
        upperPanel.add(fieldPort);
        upperPanel.add(new JPanel());
        upperPanel.add(fieldLogin);
        upperPanel.add(fieldPassw);
        upperPanel.add(btnLogin);

        log.setFont(new Font("Dialog", Font.PLAIN, 14));
        log.setLineWrap(true);
        log.setWrapStyleWord(true);

        log.setFont(new Font("Dialog", Font.PLAIN, 14));
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setBorder(BorderFactory.createEtchedBorder());

        textPanel.add(log);
        textPanel.add(new JScrollPane(log));

        buttonPanel.add(btnSend, BorderLayout.EAST);

        add(upperPanel, BorderLayout.NORTH);
        add(textPanel);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void requestToTurnServer() {
        if (serverWindow.isServerWorking) {
            if (serverWindow.requestToTurn(this))
                log.append("Вы подключились к серверу \n");
            upperPanel.setVisible(false);
        }
    }

    public void sendMessage() {
        if (serverWindow.isServerWorking) {
            String str = log.getText();
            serverWindow.sendMessage(str, this);
        }
    }

    public JTextArea getLog() {
        return log;
    }

    public String getFieldLogin() {
        return fieldLogin.getText();
    }
}