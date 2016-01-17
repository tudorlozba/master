package tests.repositoryTests;

/**
 * Created by tudor on 14/01/16.
 */

import Common.data.repository.AbstractXMLRepository;
import Common.domain.User;
import Common.domain.UserValidator;
import junit.framework.TestCase;

import java.util.List;

public class repoXmlTest extends TestCase{


    public repoXmlTest()
    {

    }

    public void testRepoXML(){

        UserValidator validator = new UserValidator();
        AbstractXMLRepository<User> repository = new AbstractXMLRepository<User>(validator, "test.xml") {
            @Override
            protected boolean equalsId(User e, String id) {
                return false;
            }

            @Override
            protected void setId(User e, String lastId) {

            }
        };
        List<User> entities = repository.getAll();
        System.out.print("size: ");
        System.out.print(entities.size());
        System.out.print(entities.toString());

       User user = new User("3", "Test3", "test3");
        //repository.save(user);
        user.setId("2");
        user.setName("ABC");
        user.setPassword("123");
       // repository.save(user);
    }

}
