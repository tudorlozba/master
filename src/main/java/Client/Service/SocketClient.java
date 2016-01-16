package Client.Service;

/**
 * Created by tudor on 16/01/16.
 */


import Common.domain.User;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;


public class SocketClient {

    private String hostname;
    private int port;
    Socket socketClient;
    BufferedWriter writer;
    BufferedReader reader;
    public SocketClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;

    }

    public void connect() throws UnknownHostException, IOException {
        System.out.println("Attempting to connect to "+hostname+":"+port);
        socketClient = new Socket(hostname,port);
        System.out.println("Connection Established");
        this.writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
    }

    public String readResponse() throws IOException{

        String message = reader.readLine();

        return message;
    }


    public Service<User> authService(String username, String password) {
        return new Service<User>() {
            @Override
            protected Task<User> createTask() {
                return new Task<User>() {
                    @Override
                    protected User call() throws Exception {

                        sendString(username + ';' + password);
                        String response = readResponse();
                        User user = new User(username,password);

                        if(response.equals("1")) {
                            return user;
                        }
                        else {
                            this.cancel();
                            return null;
                        }

                    }
                };
            }
        };
    }

    public void sendString(String s){

        try {

            writer.write(s);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<User> reciveList(){
        List<User> entities = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socketClient.getInputStream());
            entities = (List<User>)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // System.out.print("reciveList:"+entities);
        return entities;
    }


}
