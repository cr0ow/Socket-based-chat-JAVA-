package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Window extends Thread {
    private final JDialog dialog = new JDialog();
    private final JTextField field = new JTextField();
    private final JFrame frame = new JFrame();
    private final JTextArea messages = new JTextArea();
    private final JTextField text = new JTextField();
    private final JScrollPane chatArea = new JScrollPane(messages);
    private final JPanel textArea = new JPanel();
    private final JScrollPane textContainer = new JScrollPane(text);
    private final JButton send = new JButton("Send");

    protected BufferedReader reader;
    protected PrintWriter writer;
    private String user = null;

    public Window(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public JTextArea getMessages() {
        return messages;
    }

    public void run() {
        showStarterDialog();
    }

    class Username implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            user = field.getText();
            dialog.dispose();
            showApp();
        }
    }

    class Send implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(text.getText().equals(""))
                return;
            writer.println("[" + user + "]: " + text.getText());
            text.setText(null);
        }
    }

    private void showApp() {
        frame.setSize(new Dimension(500, 500));
        frame.setTitle("MyCHAT (" + user + ")");
        send.addActionListener(new Send());

        chatArea.setSize(frame.getWidth(), frame.getHeight() - textArea.getHeight());
        chatArea.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        chatArea.setHorizontalScrollBar(null);
        chatArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 13));

        messages.setWrapStyleWord(true);
        messages.setLineWrap(true);
        messages.setEditable(false);
        messages.setMargin(new Insets(2, 5, 2, 5));
        DefaultCaret caret = (DefaultCaret)messages.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        text.setSize(new Dimension(frame.getWidth(), textArea.getHeight()));
        text.setColumns(40);
        text.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        text.setFont(new Font("SansSerif", Font.PLAIN, 13));
        text.setScrollOffset(3);

        textArea.add(textContainer);
        textArea.add(send, BorderLayout.LINE_END);

        frame.setResizable(false);
        frame.getContentPane().add(chatArea);
        frame.getContentPane().add(textArea, BorderLayout.PAGE_END);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    private void showStarterDialog() {
        dialog.setTitle("Welcome!");
        dialog.setResizable(false);
        dialog.setSize(new Dimension(300, 155));
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel("What's Your name?", JLabel.CENTER);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setBorder(new EmptyBorder(10, 10, 10, 10));

        field.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setMaximumSize(new Dimension(dialog.getWidth() - 40, 30));
        field.setHorizontalAlignment(JTextField.CENTER);

        JButton button = new JButton("Next");
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setSize(100, 30);
        button.setMaximumSize(button.getSize());
        button.addActionListener(new Username());

        JLabel placeholder = new JLabel("");
        placeholder.setBorder(new EmptyBorder(5,5,5,5));
        JLabel placeholder1 = new JLabel("");
        placeholder.setBorder(new EmptyBorder(5,5,5,5));

        dialog.getContentPane().add(label);
        dialog.getContentPane().add(field);
        dialog.getContentPane().add(placeholder);
        dialog.getContentPane().add(button);
        dialog.getContentPane().add(placeholder1);

        dialog.setVisible(true);
    }
}
