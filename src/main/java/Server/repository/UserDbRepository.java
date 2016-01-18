package Server.repository;

import Common.data.model.CommonException;
import Common.data.model.ValidationException;
import Common.data.model.Validator;
import Common.data.repository.RepositoryInterface;
import Common.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;

public class UserDbRepository implements RepositoryInterface<User> {

     String tempId= "";
     private static Log log = LogFactory.getLog(UserDbRepository.class);
     @Autowired
     JdbcTemplate jdbcTemplate;

     Validator<User> validator;
     public UserDbRepository(Validator validator){
          this.validator = validator;
     }
     public UserDbRepository(){
          this.validator=null;
     }
     public void setValidator(Validator validator){
          this.validator=validator;
     }
     @Override
     public int size(){
          return 0;
     }

     @Override
     public void save(User user){
          try{
               validator.validate(user);

               user.setId(insertAndGetId(user));

          }catch (ValidationException errors){
               throw new CommonException(errors.toString());
          }

     }
     @Override
     public String getValidId(){
          return tempId;
     }

     private String insertAndGetId(User user) {
          KeyHolder keyHolder = new GeneratedKeyHolder();
          jdbcTemplate.update(connection -> {
               PreparedStatement ps = connection.prepareStatement(
               "INSERT INTO USERS (name, password) VALUES (?, ?)",
                       new String[]{"id"});
               ps.setString(1, user.getName());
               ps.setString(2, user.getPassword());
               return ps;
          }, keyHolder);
          return keyHolder.getKey().toString();
     }

     @Override
     public User findById (String id){
          List<User> users = jdbcTemplate.query("SELECT id ,name , password FROM USERS WHERE id=?",
                  new Object[]{id},(rs, rowNumber) ->
                    new User(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("password")

                    )

          );
          return users.size() == 0 ? null:users.get(0);
     }

     @Override
     public Iterable<User> findAll(){
          log.info("Find all users.");
          return jdbcTemplate.query("SELECT id , name, password FROM USERS" , (rs ,i) -> {
               log.info(
                       rs.getString("id")+ "  " +
                       rs.getString("name") + "  " +
                       rs.getString("password") + "  "

               );
               return new User(
                       rs.getString("id"),
                       rs.getString("name"),
                       rs.getString("password")
               );
          });
     }
}
