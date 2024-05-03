package com.ayman.fightEnemies.network.client;

import com.ayman.fightEnemies.entity.projectile.WizardProjectile;
import com.ayman.fightEnemies.level.SpawnLevel;
import com.ayman.fightEnemies.network.client.controller.ClientController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import java.net.*;
import java.util.Scanner;
import java.util.UUID;


public class Login extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField txtName;
    private final JTextField txtAddress;
    private final JTextField txtPort;

    public Login() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        setResizable(false);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 380);
        setLocationRelativeTo(null);
        //panel to hold all the components
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(127, 34, 45, 16);
        contentPanel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(67, 50, 165, 28);
        contentPanel.add(txtName);
        txtName.setColumns(10);


        JLabel lblIpAddress = new JLabel("IP Address of the GameController Server you want to connect to:");
        lblIpAddress.setBounds(20, 96, 300, 16);
        contentPanel.add(lblIpAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(67, 116, 165, 28);
        contentPanel.add(txtAddress);
        txtAddress.setColumns(10);
        txtAddress.setText("localhost");

        JLabel lblAddressDesc = new JLabel("(eg. 192.168.1.2)");
        lblAddressDesc.setBounds(100, 142, 112, 16);

        contentPanel.add(lblAddressDesc);
        JLabel lblPort = new JLabel("Server Port:");
        lblPort.setBounds(113, 171, 70, 16);
        contentPanel.add(lblPort);

        txtPort = new JTextField();
        txtPort.setColumns(10);
        txtPort.setBounds(67, 191, 165, 28);
        contentPanel.add(txtPort);



        JLabel lblPortDesc = new JLabel("(eg. 8192)");
        lblPortDesc.setBounds(116, 218, 68, 16);
        contentPanel.add(lblPortDesc);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            String name = txtName.getText();
            String address = txtAddress.getText();
            int port = Integer.parseInt(txtPort.getText());
            login(name, address, port);
        });
        btnLogin.setBounds(91, 311, 117, 29);
        contentPanel.add(btnLogin);
    }

    private void login(String name, String address, int port) {
        dispose();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Level Directory: ");
        SpawnLevel.levelsLocation = scanner.nextLine();
        System.out.println("Enter the number of levels: ");
        SpawnLevel.numberOfLevels = scanner.nextInt();
        // decreasing fire rate to ensure that the server will not be overwhelmed
        WizardProjectile.FIRE_INTERVAL = 20;
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            ClientController.twoPlayerMode = true;
            GameClient gameClient = new GameClient(InetAddress.getByName(address), port, name);
            ClientController controller = ClientController.init(gameClient);
            controller.start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
//
//        GameController game = new GameController(txtName.getText());
//        game.jFrame.setResizable(false);
//        game.jFrame.setTitle("FightEnemies - " + name);
//        game.jFrame.add(game);
//        game.jFrame.pack();
//        game.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        game.jFrame.setLocationRelativeTo(null);
//        game.jFrame.setVisible(true);
//
//        game.start();
//
//        game.requestFocus(); //request focus for the game



    }

    private void sendConnectionPacket(String name, String address, int port, DatagramSocket socket) {

        DatagramPacket packet;

        int attempts = 100;
        try {
            GameClient  cl = new GameClient(InetAddress.getLocalHost(), 8081, "sasa");
            System.out.println("sasa");
            cl.start();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        while (attempts-- > 0) {
            try {
                String connectionMessage = "C" + name;
                packet = new DatagramPacket(connectionMessage.getBytes(), (connectionMessage).getBytes().length, InetAddress.getByName(address), port);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("Sending connection packet to " + address + ":" + port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        while(true) {
            byte[] data = new byte[1024];
            packet = new DatagramPacket(data, data.length);
            System.out.println("BAD");
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(new String(packet.getData()));

        }




    }
    private void sendDisconnectionPacket(String name, String address, int port, DatagramSocket socket) {
        DatagramPacket packet;
        try {
            String disconnectionMessage = "D" + UUID.randomUUID();
            packet = new DatagramPacket(disconnectionMessage.getBytes(), (disconnectionMessage).getBytes().length, InetAddress.getByName(address), port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("Sending disconnection packet to " + address + ":" + port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
