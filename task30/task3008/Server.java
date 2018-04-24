package com.javarush.task.task30.task3008;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {

            while (true) {

                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();

                if (message.getType() == MessageType.USER_NAME) {
                    if (!message.getData().isEmpty()) {
                        if (!connectionMap.containsKey(message.getData())) {
                            connectionMap.put(message.getData(), connection);
                            connection.send(new Message(MessageType.NAME_ACCEPTED));
                            return message.getData();
                        }
                    }
                }
            }
        }
        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for(Map.Entry<String, Connection> pair : connectionMap.entrySet()){
                String name = pair.getKey();
                if(name != userName) {
                    Message message = new Message(MessageType.USER_ADDED, name);
                    connection.send(message);
                }
            }
        }
        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException{
            while(true) {
                Message message = connection.receive();
                if(message.getType() == MessageType.TEXT){
                    Message newMessage = new Message(MessageType.TEXT, userName + ": " + message.getData());
                    Server.sendBroadcastMessage(newMessage);
                }
                else {
                    ConsoleHelper.writeMessage("Error");
                }
            }
        }
        public void run(){

            ConsoleHelper.writeMessage("Connected with address " + socket.getRemoteSocketAddress());
            try(Connection connection = new Connection(socket)) {
                String userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(connection, userName);
                serverMainLoop(connection, userName);
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                ConsoleHelper.writeMessage("Connection was closed "+ socket.getRemoteSocketAddress());
            }
            catch (IOException e) {
                ConsoleHelper.writeMessage("Error");
            }
            catch (ClassNotFoundException e){
                ConsoleHelper.writeMessage("Error");
            }

        }
    }
        private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

        public static void main(String[] args) throws IOException {
            ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt());
            System.out.println("Сервер запущен");
            try {
                while (true) {
                    Handler handler = new Handler(serverSocket.accept());
                    handler.start();
                    continue;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                serverSocket.close();
            }
        }

        public static void sendBroadcastMessage(Message message) {
            for (Connection connection : connectionMap.values()) {
                try {
                    connection.send(message);
                } catch (IOException e) {
                    System.out.println("Сообщение не отправлено");
                }
            }
        }
    }

