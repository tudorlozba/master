package tests.repositoryTests;

/**
 * Created by tudor on 14/01/16.
 */

import Common.data.repository.AbstractXMLRepository;
import Common.domain.User;
import Common.domain.UserValidator;
import junit.framework.TestCase;

public class repoXmlTest extends TestCase{


    public repoXmlTest()
    {

    }

    public void testRepoXML(){

        UserValidator validator = new UserValidator();
        AbstractXMLRepository<User> repository = new AbstractXMLRepository<User>(validator) {
            @Override
            protected boolean equalsId(User e, String id) {
                return false;
            }

            @Override
            protected void setId(User e, String lastId) {

            }
        };
        repository.getAll();

        User user = new User("Nume", "Numar");
        repository.save(user);
        user.setName("ABC");
        user.setNumber("123");
        repository.save(user);
    }

}
