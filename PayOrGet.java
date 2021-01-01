package com.khatabookProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PayOrGet implements ActionListener {

    private JRadioButton pay;
    private JRadioButton get;
    private final JTextField rupees;
    private JLabel rupeesLabel;
    private JLabel DescriptionLabel;
    private JButton payOrGetAddEntry;
    private JTextField Description;
    private ButtonGroup BG1;
    static double totalMoney = 0;
    static String customerTitle;
    static String currentDate;
    static String currentTime;
    static String description;
    static File file;
    static JButton payOrGetback;
    static JFrame payOrGetframe;

    @Override
    public void actionPerformed(ActionEvent e) {

        description = Description.getText();

        if (e.getSource() == payOrGetAddEntry){

            currentDate = giveDate();
            currentTime = giveTime();

            try {
                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bf = new BufferedWriter(fw);

                if (pay.isSelected()){
                    final double paidMoney;
                    paidMoney = Double.parseDouble(rupees.getText());

                    bf.write(currentTime +" / ");
                    bf.write(currentDate +" / ");
                    bf.write(description +" / ");
                    bf.write(paidMoney+" / ");
                    bf.write("0");
                    bf.append("\n");
                    bf.close();
                    fw.close();

                    payOrGetframe.dispose();

                    new AllEntries(customerTitle,file);
                }else{
                    final double gotMoney;
                    gotMoney = Double.parseDouble(rupees.getText());
//                    totalMoney = totalMoney - Double.parseDouble(rupees.getText());
//                    System.out.println(totalMoney);

                    bf.write(currentTime +" / ");
                    bf.write(currentDate +" / ");
                    bf.write(description +" / ");
                    bf.write("0"+" / ");
                    bf.write(gotMoney + "");
                    bf.append("\n");
                    bf.close();
                    fw.close();

                    payOrGetframe.dispose();

                    new AllEntries(customerTitle,file);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }else if (e.getSource() == payOrGetback){

            payOrGetframe.dispose();
            System.out.println("all entries");
            new AllEntries(customerTitle,file);

        }
    }

    PayOrGet(String title,File file){

        PayOrGet.file = file;
        customerTitle = title;

        payOrGetframe = new JFrame();
        payOrGetframe.setLocation(500,100);
        payOrGetframe.setSize(450,300);
        payOrGetframe.setTitle("Adding Entry in " +title+" Account");
        payOrGetframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        payOrGetframe.setResizable(false);
        payOrGetframe.setLayout(null);

        rupeesLabel = new JLabel("Rupees :");
        rupeesLabel.setFont(new Font("Arial",Font.PLAIN,15));
        rupeesLabel.setSize(80,24);
        rupeesLabel.setLocation(100,30);
        payOrGetframe.add(rupeesLabel);

        rupees = new JTextField("");
        rupees.setFont(new Font("Arial",Font.PLAIN,15));
        rupees.setSize(100,24);
        rupees.setLocation(200,30);

        // for make input type numbers only
        rupees.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = rupees.getText();
                int l = value.length();
                if (ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    rupees.setEditable(true);
                }else
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    rupees.setEditable(true);
                } else {
                    rupees.setEditable(false);
                }
            }
        });

        payOrGetframe.add(rupees);

        BG1 = new ButtonGroup();

        pay = new JRadioButton("pay",true);
        pay.setFont(new Font("Arial",Font.PLAIN,15));
        pay.setBounds(100,65,50,50);
        payOrGetframe.add(pay);

        get = new JRadioButton("get");
        get.setFont(new Font("Arial",Font.PLAIN,15));
        get.setBounds(170,65,50,50);
        payOrGetframe.add(get);

        DescriptionLabel = new JLabel("Description :");
        DescriptionLabel.setFont(new Font("Arial",Font.PLAIN,15));
        DescriptionLabel.setSize(100,24);
        DescriptionLabel.setLocation(100,130);
        payOrGetframe.add(DescriptionLabel);

        Description = new JTextField();
        Description.setFont(new Font("Arial",Font.PLAIN,15));
        Description.setSize(150,24);
        Description.setLocation(200,130);
        payOrGetframe.add(Description);

        payOrGetAddEntry = new JButton("ADD ENTRY");
        payOrGetAddEntry.setSize(150,24);
        payOrGetAddEntry.setLocation(130,180);
        payOrGetAddEntry.addActionListener(this);
        payOrGetframe.add(payOrGetAddEntry);

        BG1.add(pay);
        BG1.add(get);

        payOrGetback = new JButton("<<");
        payOrGetback.setBounds(40,180,50,24);
        payOrGetback.addActionListener(this);
        payOrGetframe.add(payOrGetback);

        payOrGetframe.setVisible(true);

    }

    public String giveDate(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);

    }

    public String giveTime(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm a");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);

    }
}
