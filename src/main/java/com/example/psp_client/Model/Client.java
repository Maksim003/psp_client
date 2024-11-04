package com.example.psp_client.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class Client {

    String outJson, inJson;
    ObjectMapper objectMapper = new ObjectMapper();
    Answer answer = new Answer();

    public ArrayList<Account> getAccounts(Request request) {
        ArrayList<Account> accounts = new ArrayList<>();
        objectMapper.registerModule(new JavaTimeModule());
        try (Socket clientSocket = new Socket("localhost", 3345); DataInputStream in =
                new DataInputStream(clientSocket.getInputStream()); DataOutputStream out =
                     new DataOutputStream(clientSocket.getOutputStream())) {
            while (!clientSocket.isClosed()) {
                answer = sendToServer(request, in,out,clientSocket);
            }
            accounts = answer.getAccounts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public ArrayList<Students> getStudents(Request request) {
        ArrayList<Students> students = new ArrayList<>();
        objectMapper.registerModule(new JavaTimeModule());
        try (Socket clientSocket = new Socket("localhost", 3345); DataInputStream in =
                new DataInputStream(clientSocket.getInputStream()); DataOutputStream out =
                     new DataOutputStream(clientSocket.getOutputStream())) {
            while (!clientSocket.isClosed()) {
                answer = sendToServer(request, in,out,clientSocket);
            }
            students = answer.getStudents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Marks getMarks(Request request) {
        Marks marks = new Marks();
        objectMapper.registerModule(new JavaTimeModule());
        try (Socket clientSocket = new Socket("localhost", 3345); DataInputStream in =
                new DataInputStream(clientSocket.getInputStream()); DataOutputStream out =
                     new DataOutputStream(clientSocket.getOutputStream())) {
            while (!clientSocket.isClosed()) {
                answer = sendToServer(request, in,out,clientSocket);
            }
            marks = answer.getMarks();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marks;
    }


    public Account getAcc(Request request) {
        Account account = new Account();
        try (Socket clientSocket = new Socket("localhost", 3345); DataInputStream in =
                new DataInputStream(clientSocket.getInputStream()); DataOutputStream out =
                     new DataOutputStream(clientSocket.getOutputStream())) {
            while (!clientSocket.isClosed()) {
                answer = sendToServer(request, in,out,clientSocket);
            }
            account = answer.getAccounts().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    public String getString(Request request) {
        String data = "";
        try (Socket clientSocket = new Socket("localhost", 3345); DataInputStream in =
                new DataInputStream(clientSocket.getInputStream()); DataOutputStream out =
                     new DataOutputStream(clientSocket.getOutputStream())) {
            while (!clientSocket.isClosed()) {
                answer = sendToServer(request, in, out, clientSocket);
            }
            data = answer.getSalt();
        } catch (IOException e) {
            System.out.println("Нет подключения к серверу");
            System.exit(0);
        }
        return data;
    }

    public double getDouble(Request request) {
        double data = 0;
        try (Socket clientSocket = new Socket("localhost", 3345); DataInputStream in =
                new DataInputStream(clientSocket.getInputStream()); DataOutputStream out =
                     new DataOutputStream(clientSocket.getOutputStream())) {
            while (!clientSocket.isClosed()) {
                answer = sendToServer(request, in, out, clientSocket);
            }
            data = answer.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Answer sendToServer(Request request, DataInputStream in, DataOutputStream out, Socket clientSocket) {
        try {
            outJson = objectMapper.writeValueAsString(request);
            out.writeUTF(outJson);
            out.flush();
            inJson = in.readUTF();
            answer = objectMapper.readValue(inJson, Answer.class);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public void changeData(Request request) {
        objectMapper.registerModule(new JavaTimeModule());
        try (Socket clientSocket = new Socket("localhost", 3345); DataInputStream in =
                new DataInputStream(clientSocket.getInputStream()); DataOutputStream out =
                     new DataOutputStream(clientSocket.getOutputStream())) {
            while (!clientSocket.isClosed()) {
                sendToServer(request, in, out, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
