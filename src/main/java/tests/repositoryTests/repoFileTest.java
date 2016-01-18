package tests.repositoryTests;

import Common.domain.User;
import Common.domain.UserValidator;
import Server.repository.UserFileRepository;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * Created by yusty on 17/01/2016.
 */
public class repoFileTest extends TestCase

{
     repoFileTest(){}

     public void testFileRepo(){
          UserValidator validator = new UserValidator();
          try{
               UserFileRepository userFileRepository = new UserFileRepository(validator,"src\\main\\java\\tests\\repositoryTests\\repoFileTest.txt");


               System.out.print(userFileRepository.findAll());
               userFileRepository.save(new User("admin3","admin3"));
               System.out.print(userFileRepository.findAll());

          }catch (IOException ex){

          }
     }
}
