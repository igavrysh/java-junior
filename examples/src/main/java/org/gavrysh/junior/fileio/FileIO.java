package org.gavrysh.junior.fileio;

import java.io.*;

public class FileIO {

    public static void main(String[] args) {

        String[] names = {"John", "Carl", "Jerry"};

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write("Writing to a file.");
            writer.write("\nHere is another line.");

            for (String name: names) {
                writer.write("\n" + name);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(reader.readLine());
            }
            reader.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
