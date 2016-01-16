package Client;

import Client.Controller.PostsController;
import Client.Controller.UserController;
import Client.GUI.SocialNetworkMainView;
import Client.GUI.WelcomeView;
import Client.Service.SocialNetworkServiceClient;
import Client.Service.SocketClient;
import Common.domain.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by tudor on 15/01/16.
 */
public class SocialNetwork extends Application{

    static Log logger = LogFactory.getLog(SocialNetwork.class.getSimpleName());
    private static SocialNetworkServiceClient socialNetworkServiceClient;
    private Stage stage;
    private SocketClient client;
    public static void main(String[] args){
        launch(args);
    }

    public void userAuthenticated(User user) {
        logger.info("User authenticated " + user);
        logger.info("starting second scene");
        UserController userController = new UserController(socialNetworkServiceClient);
        PostsController postsController = new PostsController(socialNetworkServiceClient);
        SocialNetworkMainView socialNetworkMainView = new SocialNetworkMainView(this, userController, postsController);
        Scene scene = new Scene(socialNetworkMainView);
        logger.info("showing second scene");
        stage.setScene(scene);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        client = new SocketClient ("localhost",9990);
        try {
            //trying to establish connection to the server
            client.connect();
            //if successful, read response from server

            /*while (true) {
                if (client.authenticate("", "")) {
                    break;
                }
                else System.out.print("Log in failed! Try Again!\n");
            }
*/
            //UserInterface userInterface = new UserInterface(client);
            //System.out.print("Client socket:"+client);
            //userInterface.run();


        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }

        logger.info("starting the primary stage");
        stage = primaryStage;
        socialNetworkServiceClient = new SocialNetworkServiceClient();
        WelcomeView view = new WelcomeView(this, client);
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle("Social Network");
        logger.info("showing the primary stage, login");
        stage.show();
    }
}
