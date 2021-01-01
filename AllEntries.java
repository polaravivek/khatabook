package com.khatabookProject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AllEntries implements ActionListener {

    JTable table;
    JScrollPane scrollPane;
    private final JButton deleteEntry;
    private final JButton back;
    private final DefaultTableModel model;
    private final JButton addEntry;
    DefaultTableCellRenderer cellRenderer;
    static String[] columnName;
    static File file;
    static String title;
    static Object[] tableLine;
    static JFrame frame;
    static double totalPaid = 0;
    static double totalget = 0;
    static double totalMoney = 0;
    static String customerName;
    static JLabel jLabel;
    static JLabel jLabel1;
    static double totalMoneyAbs;

    AllEntries(String name, File file){

        customerName = name;
        AllEntries.file = file;

        try {
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            String firstLine = bufferedReader.readLine().trim();
            columnName = firstLine.split(",");

            tableLine = bufferedReader.lines().toArray();
            bufferedReader.close();
            filereader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        title = name;
        frame = new JFrame();
        frame.setSize(550,550);
        frame.setLocation(500,100);
        frame.setTitle("Entries of "+ name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        table = new JTable();

        //we can't change the column from this
        table.setModel(new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        //set column name
        model =  (DefaultTableModel)table.getModel();
        model.setColumnIdentifiers(columnName);

        for (int i = tableLine.length-1 ; i >= 0  ; i--){

            String line = tableLine[i].toString().trim();
            String[] dataRow = line.split("/");
            model.addRow(dataRow);

        }

        //set column width
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(10);
        table.getColumnModel().getColumn(4).setPreferredWidth(10);

        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBounds(40,70,450,360);
        scrollPane.createVerticalScrollBar();

        frame.add(scrollPane);

        addEntry = new JButton("Add Entry");
        addEntry.setBounds(130,450,120,30);
        addEntry.addActionListener(this);
        frame.add(addEntry);

        deleteEntry = new JButton("Delete Entry");
        deleteEntry.setBounds(260,450,120,30);
        deleteEntry.addActionListener(this);
        frame.add(deleteEntry);

        back = new JButton("<<");
        back.setBounds(40,450,50,30);
        back.addActionListener(this);
        frame.add(back);

        for (int i = 0; i < table.getRowCount();i++){

            totalPaid = totalPaid + Double.parseDouble(table.getValueAt(i,3).toString());
        }

        for (int i = 0; i < table.getRowCount();i++){

            totalget = totalget + Double.parseDouble(table.getValueAt(i,4).toString());
        }

        totalMoney = totalget - totalPaid;

        jLabel = new JLabel("");
        jLabel.setFont(new Font("Arial",Font.PLAIN,17));
        jLabel.setSize(300,30);
        jLabel.setLocation(40,20);
        frame.add(jLabel);

        jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Arial",Font.PLAIN,22));
        jLabel1.setSize(100,50);

        if (totalMoney < 0 ){
            jLabel.setText("you have to pay : ");
            jLabel1.setLocation(170,10);
            totalMoneyAbs = Math.abs(totalMoney);
            jLabel1.setText(String.valueOf(totalMoneyAbs));
            jLabel1.setForeground(Color.red);
        }else{
            jLabel.setText("you will get : ");
            jLabel1.setLocation(135,10);
            jLabel1.setText(String.valueOf(totalMoney));
            jLabel1.setForeground(new Color(0, 198, 0));
        }

        frame.add(jLabel1);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == deleteEntry){

            int selectedRowIndex = table.getSelectedRow();
            String timeForRemoveTerm = model.getValueAt(selectedRowIndex,0).toString();
            String dateForRemoveTerm = model.getValueAt(selectedRowIndex,1).toString();
            String desForRemoveTerm = model.getValueAt(selectedRowIndex,2).toString();
            String payForRemoveTerm = model.getValueAt(selectedRowIndex,3).toString();
            String getForRemoveTerm = model.getValueAt(selectedRowIndex,4).toString();

            String RemoveTerm = timeForRemoveTerm + "/" + dateForRemoveTerm + "/"+desForRemoveTerm+"/"+payForRemoveTerm+"/"+getForRemoveTerm;

            totalPaid = 0;
            totalget = 0;

            String InputFileName = customerName+".txt";

            new DeleteEntry(RemoveTerm,InputFileName);

            double deletedPaidMoney = Double.parseDouble(payForRemoveTerm);
            double deletedgetMoney = Double.parseDouble(getForRemoveTerm);

            System.out.println(totalMoney);
            System.out.println(deletedgetMoney);
            System.out.println(deletedPaidMoney);

            totalMoney = totalMoney + deletedPaidMoney - deletedgetMoney;

            if (totalMoney < 0 ){
                jLabel.setText("you have to pay : ");
                jLabel1.setLocation(170,10);
                totalMoneyAbs = Math.abs(totalMoney);
                jLabel1.setText(String.valueOf(totalMoneyAbs));
                jLabel1.setForeground(Color.red);
            }else{
                jLabel.setText("you will get : ");
                jLabel1.setLocation(135,10);
                jLabel1.setText(String.valueOf(totalMoney));
                jLabel1.setForeground(new Color(0, 198, 0));
            }

            model.removeRow(table.getSelectedRow());

        }else if (e.getSource() == addEntry){
            totalPaid = 0;
            totalget = 0;
            totalMoney = 0;
            frame.dispose();
            new PayOrGet(title,file);
        }else if(e.getSource() == back){
            totalPaid = 0;
            totalget = 0;
            totalMoney = 0;
            frame.dispose();
            new Khatabook();
        }
    }

}
