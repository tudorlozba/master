package Server.Controller;

import Common.domain.User;
import Server.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by tudor on 15/01/16.
 */

public class SocialNetworkController {

    private static Log logger = LogFactory.getLog(SocialNetworkController.class);

    private UserRepository userRepository;

    SocialNetworkController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Iterable<User> getAllUsers() {
        logger.info("getAllUsers...");
        logger.info(userRepository.findAll().toString());
        return userRepository.findAll();
    }
}
