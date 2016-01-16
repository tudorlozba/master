package Server.repository;

import Common.data.repository.AbstractXMLRepository;
import Common.domain.User;

/**
 * Created by tudor on 15/01/16.
 */
public class UserRepository extends AbstractXMLRepository<User>{


    @Override
    protected boolean equalsId(User e, String id) {
        return false;
    }

    @Override
    protected void setId(User e, String lastId) {

    }
}
