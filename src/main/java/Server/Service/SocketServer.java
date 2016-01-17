package Server.Service;

/**
 * Created by tudor on 16/01/16.
 */


        import Server.repository.UserRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer {

    private ServerSocket serverSocket;
    private int port;

    public SocketServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("Starting the socket server at port:" + port);
        serverSocket = new ServerSocket(port);

        //Listen for clients. Block till one connects

        while(true) {
            System.out.println("Waiting for clients...");
            Socket client = serverSocket.accept();

            //A client has connected to this server. Send welcome message
            System.out.print("Client connected!\n");

            UserRepository userRepository = new UserRepository();
            SocialNetworkServiceImpl socialNetworkService = new SocialNetworkServiceImpl(client, userRepository);



            //socialNetworkService.setPersonManager(personManager);

            Thread thread = new Thread(socialNetworkService);
            thread.start();

        }
    }


}
