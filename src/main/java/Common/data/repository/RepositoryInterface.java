package Common.data.repository;

/**
 * Created by tudor on 14/01/16.
 */
public interface RepositoryInterface<Entity>
{
     int size();
     Iterable<Entity> findAll();
     void save(Entity e);
     Entity findById(String id);
     String getValidId();
}
