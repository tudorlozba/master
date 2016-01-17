package Client.Controller;

import Client.Service.SocialNetworkServiceClient;
import Common.domain.Post;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * Created by tudor on 15/01/16.
 */
public class PostsController {
    private static SocialNetworkServiceClient socialNetworkServiceClient;

    public PostsController(SocialNetworkServiceClient socialNetworkServiceClient) {
        this.socialNetworkServiceClient = socialNetworkServiceClient;
    }

    public static Service<ArrayList<Post>> fetchPostsService() {
        return new Service<ArrayList<Post>>() {
            @Override
            protected Task<ArrayList<Post>> createTask() {
                return new Task<ArrayList<Post>>() {
                    @Override
                    protected ArrayList<Post> call() throws Exception {
                        return socialNetworkServiceClient.viewPosts();
                    }
                };
            }
        };
    }
}
