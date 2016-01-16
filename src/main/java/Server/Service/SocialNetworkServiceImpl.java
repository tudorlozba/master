package Server.Service;

import Common.Service.SocialNetworkService;
import Common.domain.User;
import Server.repository.UserRepository;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.Socket;


/**
 * Created by tudor on 15/01/16.
 */
public class SocialNetworkServiceImpl implements SocialNetworkService, Runnable{
    private final Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;
    private UserRepository userRepository;

    public SocialNetworkServiceImpl(Socket client, UserRepository userRepository){
        this.userRepository = userRepository;
        this.client = client;

        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        while(true) {
            User user;
            try {
                 user = authenticate();
            }
            catch (LoginException e){
                e.printStackTrace();
                break;
            }
            if(user!=null)
                break;
        }
    }


    private User authenticate() throws LoginException {
        User user = null;
        try {
            String username;
            String password;
            String input;

            input = readResponse();
            if(input == null || input=="")
                throw new LoginException("null message");
            String[] in = input.split(";");

            username=in[0];
            password = in[1];

            System.out.print("Input:"+username+" "+password);

            user = logIn(username, password);

            if(user!=null){
                sendString("1");
                System.out.print("Server sends 1");
            }
            else
            {
                sendString("404");
                System.out.print("Server sends 404");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User logIn(String username, String password) {
        if(username.equals("admin") && password.equals("admin"))
            return new User(username,password);

        return null;
    }

    public String readResponse() throws IOException{

        String message = reader.readLine();
        return message;
    }
    private void sendString(String s) throws IOException {

        writer.write(s);
        writer.newLine();
        writer.flush();

    }

}
