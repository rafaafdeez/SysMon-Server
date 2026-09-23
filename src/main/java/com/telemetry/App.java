package com.telemetry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        int port = 8080;
        
        //Start the server on port 8080
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("--- [SysMon Server] Listening on port " + port + " ---");
            
            //Infinite loop to keep the server alive waiting for connections
            while (true) {
                // he program pauses here until the C++ agent connects
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    
                    //Read the entire JSON payload sent by the C++ agent
                    String inputLine;
                    StringBuilder payload = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        payload.append(inputLine).append("\n");
                    }
                    
                    //Print the received JSON to the console
                    System.out.println("[New Payload Received]:\n" + payload.toString());
                    
                } catch (Exception e) {
                    System.err.println("[Read Error]: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("[Fatal Error] Could not start server: " + e.getMessage());
        }
    }
}