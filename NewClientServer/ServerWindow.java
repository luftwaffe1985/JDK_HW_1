package NewClientServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 500;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private String message = "";
    private String messageFileName = "messageLog.txt";
    private String pathFile;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JButton btnAddUser = new JButton("addUser");
    private final JButton btnSendMessage = new JButton("sendMessage");
    private final JTextArea log = new JTextArea(11, 33);
    private final JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
    private final JPanel textPanel = new JPanel();
    private final JPanel generalPanel = new JPanel(new BorderLayout());

    public boolean isServerWorking;
    private List<String> chatMessageList = new ArrayList<>();
    public List<ClientGUI> lstClients;

    // public List<ClientGUI> getLstClients() {
    // return lstClients;
    // }

    public ServerWindow() {
        isServerWorking = true;
        lstClients = new ArrayList<>();
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = false;
                message = "Server stoped " + new Date() + "\n";
                for (int i = 0; i < lstClients.size() - 1; i++) {
                    lstClients.get(i).getLog().append(message);
                }
                log.append(message);
                pathFile = getPathThis() + messageFileName;
                writeMessagetoFile(message, pathFile);
                textPanel.setBackground(Color.LIGHT_GRAY);
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = true;
                message = "Server started " + new Date() + "\n";
                for (int i = 0; i < lstClients.size() - 1; i++) {
                    lstClients.get(i).getLog().append(message);
                }
                log.append(message);
                pathFile = fullPathName(messageFileName);
                ;
                writeMessagetoFile(message, pathFile);
                textPanel.setBackground(Color.GRAY);
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        buttonPanel.setPreferredSize(new Dimension(getWidth() - 2, 35));
        textPanel.setBackground(Color.GRAY);

        log.setFont(new Font("Dialog", Font.PLAIN, 14));
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setBorder(BorderFactory.createEtchedBorder());
        log.append("Server is started \n");

        textPanel.add(log);
        textPanel.add(new JScrollPane(log));

        buttonPanel.add(btnStart);
        buttonPanel.add(btnStop);

        generalPanel.add(textPanel);
        generalPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(generalPanel);

        setVisible(true);
    }

    protected String getPathThis() {
        String pathLog;
        URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
        pathLog = location.getPath();
        return pathLog;
    }

    public String fullPathName(String fileName) {
        return getPathThis() + fileName;
    }

    public void writeMessagetoFile(String str, String path) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file, true);
                writer.append(str);
                writer.close();
            } else {
                FileWriter writer = new FileWriter(file, true);
                writer.append(str);
                writer.close();
            }
        } catch (Exception e) {
        }
    }

    public void readLog(String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (reader.ready()) {
                    chatMessageList.add(reader.readLine());
                }
                reader.close();
            }
        } catch (Exception e) {
        }
    }

    public boolean requestToTurn(ClientGUI clientGUI) {
        if (isServerWorking) {
            lstClients.add(clientGUI);
            for (int i = 0; i < lstClients.size() - 1; i++) {
                lstClients.get(i).getLog().append("К беседе присоединился "
                        + lstClients.get(lstClients.size() - 1).getFieldLogin()
                        + " " + new Date() + "\n");
            }
            for (String item : chatMessageList) {
                lstClients.get(lstClients.size() - 1).getLog().append(item);
            }
            pathFile = fullPathName(messageFileName);
            writeMessagetoFile(message, pathFile);
            return true;
        } else {
            return false;
        }
    }

    public String sendMessage(String message, ClientGUI clientGUI) {
        if (isServerWorking) {
            message = clientGUI.getFieldLogin() + ": " + message;
            chatMessageList.add(message);
            for (int i = 0; i < lstClients.size() - 1; i++) {
                if (!clientGUI.getFieldLogin().equals(lstClients.get(i).getFieldLogin()))
                    lstClients.get(i).getLog().append(message + "\n");
            }
            pathFile = fullPathName(messageFileName);
            ;
            writeMessagetoFile(message, pathFile);
        } else {
            message = "Нет связи с сервером";
        }
        return message;
    }
}