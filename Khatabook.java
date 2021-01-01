package com.khatabookProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Khatabook implements ActionListener {
    private static FileWriter fw1;
    private final JTextField textField;
    private final JButton addCustomer;
    private static JButton goAccountPage;
    private static JButton delete;
    static java.awt.List list;
    static JLabel jLabel;
    static File file;
    static int count = 0;
    static Object[] oldList;
    static JFrame frame;

    Khatabook() {
        frame = new JFrame();
        frame.setSize(350, 540);
        frame.setLocation(500, 100);
        frame.setTitle("KhataBook App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        textField = new JTextField("");
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setSize(220, 24);
        textField.setLocation(10, 30);
        frame.add(textField);

        addCustomer = new JButton("ADD");
        addCustomer.setSize(80, 24);
        addCustomer.setLocation(240, 30);
        addCustomer.addActionListener(this);
        frame.add(addCustomer);

        list.setBounds(10, 70, 310, 320);
        list.setMultipleMode(false);
        frame.add(list);

        goAccountPage = new JButton("GO TO ACCOUNT PAGE");
        goAccountPage.setVisible(true);
        goAccountPage.setSize(190, 24);
        goAccountPage.setLocation(10, 415);
        goAccountPage.addActionListener(this);
        frame.add(goAccountPage);

        delete = new JButton("DELETE");
        delete.setVisible(true);
        delete.setSize(90, 24);
        delete.setLocation(230, 415);
        delete.addActionListener(this);
        frame.add(delete);

        jLabel = new JLabel();
        jLabel.setFont(new Font("Arial",Font.PLAIN,15));
        jLabel.setBounds(50,450,300,30);
        frame.add(jLabel);

        frame.setVisible(true);

    }

    public static void main(String[] args) {

        list = new java.awt.List();

        file = new File("List.txt");
        boolean listExist = file.exists();

        if (!listExist) {

            try {
                fw1 = new FileWriter(file, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            if (file.length() == 0) {

            } else {

                list = new java.awt.List();

                try {
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    oldList = bufferedReader.lines().toArray();

                    for (Object o : oldList) {

                        list.add(o.toString().trim());

                    }

                    bufferedReader.close();
                    fileReader.close();

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        new Khatabook();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addCustomer) {
            String name = textField.getText();

            int countSame = 0;
            for (int i = 0; i < list.getItemCount() ; i++){

                if (textField.getText().equals(list.getItem(i))){
                    countSame = 1;
                    break;
                }
            }

            System.out.println(countSame);

            if (countSame == 0){

                list.add(textField.getText());

                try {
                    fw1 = new FileWriter(file, true);
                    fw1.write(name);
                    fw1.append("\n");
                    fw1.close();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }else {

                jLabel.setText("same name available in list already");

            }

            goAccountPage.setVisible(true);

            textField.setText("");

        } else if (e.getSource() == goAccountPage) {

            File file = new File(list.getItem(list.getSelectedIndex()) + ".txt");

            try {
                FileWriter fw = new FileWriter(file, true);

                if (file.length() != 0) {
                    fw.close();
                } else {

                    fw.write("TIME ,");
                    fw.write("DATE ,");
                    fw.write("DESCRIPTION ,");
                    fw.write("PAY ,");
                    fw.write("GET");
                    fw.write("\n");
                    fw.close();

                    count++;
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            if (list.getSelectedIndex() != -1) {

                frame.dispose();
                new AllEntries(list.getItem(list.getSelectedIndex()), file);
            } else {
                System.out.println("please select someone");
            }
        } else if (e.getSource() == delete) {

            String removeterm = list.getItem(list.getSelectedIndex());

            File selectedFile = new File(list.getItem(list.getSelectedIndex()) + ".txt");

            new DeleteEntry(removeterm, "List.txt");

            boolean selectedFileDeletion = selectedFile.delete();

            System.out.println("selectedFileDeletion = " + selectedFileDeletion);

            list.remove(list.getSelectedIndex());

        }
    }
}
