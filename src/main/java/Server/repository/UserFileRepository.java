package Server.repository;


import Common.data.model.Validator;
import Common.data.repository.FileRepositoryInterface;
import Common.domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

public class UserFileRepository extends FileRepositoryInterface<User> {

     public UserFileRepository(){}
     public UserFileRepository(Validator<User> validator,String fileName)  throws IOException {
          super(validator,fileName);
          File f = new File(fileName);
          if(!f.exists()) {
               f.createNewFile();
          }

          loadFromFile();
     }

     private void loadFromFile() {
          try{
               File file = new File(fileName);
               if(!file.exists())
                    file.createNewFile();
               Scanner input = new Scanner(file);
               while ( input.hasNextLine()) {
                    String[] tokens = input.nextLine().split(" ");
                    if(tokens[0]!="") {
                         User user = new User();
                         user.setId(tokens[0]); // Maybe tokens.toString is more suitable
                         user.setName(tokens[1]);
                         user.setPassword(tokens[2]);

                         entities.add(user);
                    }
               }
          }catch (IOException exception){
               System.out.print(exception.getMessage());
          }

     }
     @Override
     protected void updateFile() {

          try{
               File file = new File(fileName);

               PrintWriter pw = new PrintWriter(file);
               Iterator<User> iterator = entities.iterator();
               while(iterator.hasNext()){
                    User entity = (User) iterator.next();
                    pw.println(entity.id + " " + entity.getName() + " " + entity.getPassword());

               }
               pw.close();
          }catch (FileNotFoundException exception) {
               System.out.print(exception.getMessage() + "");
          }


     }

     @Override
     protected void setEntityId(User user, Integer id){
          user.setId(id.toString());
     }


     @Override
     public int getEntityId(User user){
          return 0;
     }

}
