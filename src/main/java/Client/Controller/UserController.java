package Client.Controller;

import Client.Service.SocialNetworkServiceClient;
import Common.domain.User;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Created by tudor on 15/01/16.
 */
public class UserController {
    private static SocialNetworkServiceClient socialNetworkServiceClient;

    public UserController(SocialNetworkServiceClient socialNetworkServiceClient) {
        this.socialNetworkServiceClient = socialNetworkServiceClient;
    }

    public static Service<ArrayList<User>> fetchUsersService() {
        return new Service<ArrayList<User>>() {
            @Override
            protected Task<ArrayList<User>> createTask() {
                return new Task<ArrayList<User>>() {
                    @Override
                    protected ArrayList<User> call() throws Exception {
                        return socialNetworkServiceClient.viewUsers();
                    }
                };
            }
        };
    }
}
