package Server;

/**
 * Created by tudor on 15/01/16.
 */

import Server.Service.SocketServer;

import java.io.IOException;

public class SocialNetworkServer {

        public static void main(String[] args) {
            // Setting a default port number.
            int portNumber = 9990;
            try {

                // initializing the Socket Server
                SocketServer socketServer = new SocketServer(portNumber);
                socketServer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


}
