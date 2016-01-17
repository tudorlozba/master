package Client.Service;


import Common.Service.SocialNetworkService;
import Common.domain.Post;
import Common.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;


public class SocialNetworkServiceClient implements SocialNetworkService {

    static Log log = LogFactory.getLog(SocialNetworkServiceClient.class.getSimpleName());

    private User user;

    public SocialNetworkServiceClient() {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Post> viewPosts() {
        Post post1 = new Post("User1", "This is the first post");
        Post post2 = new Post("User2", "This is the second post");

        Post[] posts = {post1, post2};
        return new ArrayList<>(Arrays.asList(posts));
    }

    public ArrayList<User> viewUsers() {
        User user1 = new User("User1", "pass");
        User user2 = new User("User2", "pass");

        User[] users = {user1,user2};
        return new ArrayList<>(Arrays.asList(users));
    }
}
