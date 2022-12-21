package com.ymimsr.task_16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        try {
            URL url = new URL(args[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (
                    BufferedReader httpReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))
            ) {
                String line;
                int i = 0;
                while ((line = httpReader.readLine()) != null) {
                    if (i == 25) {
                        i = 0;
                        System.out.println("Press return to scroll down.");
                        consoleReader.readLine();
                    }

                    i++;
                    System.out.println(line);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
