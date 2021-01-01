package com.khatabookProject;

import java.io.*;

public class DeleteEntry {

    DeleteEntry(String RemoveTerm, String InputfileName){
        File inputFile = new File(InputfileName);
        File tempFile = new File("myTempFile.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while((currentLine = reader.readLine()) != null) {

                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(RemoveTerm)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));

            }

            writer.close();
            reader.close();
            inputFile.delete();

            boolean successful = tempFile.renameTo(inputFile);

            System.out.println(successful+" delete successful");

        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
