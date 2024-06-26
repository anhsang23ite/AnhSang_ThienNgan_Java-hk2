package Model;

import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class Chat extends JFrame implements Observer {
    private JTextArea messageTextArea;
    private JTextField messageInput;
    private JButton sendMessageButton;
    private JButton cancelButton; // New Cancel button
    private JScrollPane jScrollPane1;
    private MessageBroker broker;
    private String username;

    public Chat(String username, MessageBroker broker) {
        this.username = username;
        this.broker = broker;
        broker.addObserver(this);
        initComponents();
    }

    private void initComponents() {
        messageTextArea = new JTextArea();
        messageInput = new JTextField();
        sendMessageButton = new JButton("Gửi");
        cancelButton = new JButton("Cancel"); // Initialize Cancel button
        jScrollPane1 = new JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        messageTextArea.setColumns(20);
        messageTextArea.setRows(5);
        messageTextArea.setText("PetShop Trò Chuyện\n");
        messageTextArea.setEditable(false);
        jScrollPane1.setViewportView(messageTextArea);

        messageInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageInputKeyPressed(evt);
            }
        });

        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });

        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        // Layout and adding components
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(messageInput, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sendMessageButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton) // Add Cancel button here
                        )
                    )
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(messageInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(sendMessageButton)
                        .addComponent(cancelButton)) // Adjust here to include Cancel button
                    .addContainerGap())
        );

        pack();
    }

    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {
        sendMessage();
    }

    private void messageInputKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage();
        }
    }

    private void sendMessage() {
        try {
            if (messageInput.getText().isEmpty()) {
                throw new Exception("Vui lòng nhập nội dung tin nhắn");
            }
            String message = username + ": " + messageInput.getText();
            broker.sendMessage(message);
            messageInput.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose(); // Close the Chat window (current window)
    }

    @Override
    public void update(Observable o, Object arg) {
        String message = (String) arg;
        messageTextArea.append(message + "\n");
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public static void main(String args[]) {
        MessageBroker broker = new MessageBroker();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat("QUAN LI", broker).setVisible(true);
                new Chat("NHAN VIEN", broker).setVisible(true);
            }
        });
    }
}

@SuppressWarnings("deprecation")
class MessageBroker extends Observable {
    public void sendMessage(String message) {
        setChanged();
        notifyObservers(message);
    }
}
