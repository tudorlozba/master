package Common.data.repository;


import Common.data.model.CommonException;
import Common.data.model.ValidationException;
import Common.data.model.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class FileRepositoryInterface<Entity> implements RepositoryInterface<Entity>{

     private Validator<Entity> validator = null;
     protected String fileName="";

     protected List<Entity> entities = new ArrayList<Entity>();
     public <Entity> FileRepositoryInterface(){}

     public FileRepositoryInterface(Validator<Entity> validator, String fileName) {
          this.validator=validator;
          this.fileName=fileName;
     }

     protected void readFromFile() throws ValidationException {}
     protected void writeToFile() throws IOException {}
     protected abstract void setEntityId(Entity entity, Integer id);
     public abstract int getEntityId(Entity entity);

     public FileRepositoryInterface(Validator<Entity> validator){
          this.validator=validator;
     }

     @Override
     public int size(){
          return entities.size();
     }
     @Override
     public List<Entity> findAll(){
          return entities;
     }

     @Override
     public String getValidId() {
          return "valid id";
     }

     @Override
     public void save(Entity entity){
          try {
               validator.validate(entity);
               setEntityId(entity, entities.size()+1);
               entities.add(entity);
               updateFile();
          }catch (ValidationException errors){
               throw new CommonException(errors.toString());
          }
     }
     @Override
     public Entity findById(String id){
          if (id == null) {
               return null;
          }
          Optional<Entity> e = entities.parallelStream().filter((entity) -> id.equals(getEntityId(entity))).findFirst();
          if (e.isPresent())
               return e.get();
          else
               return null;
     }
     protected void updateFile() {}

}
