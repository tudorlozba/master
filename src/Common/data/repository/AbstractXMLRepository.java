package Common.data.repository;

import Common.data.model.CommonException;
import Common.data.model.ValidationException;
import Common.data.model.Validator;

import javax.xml.stream.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
/**
 * Created by tudor on 14/01/16.
 */
public abstract class AbstractXMLRepository<Entity> implements
        RepositoryInterface<Entity>
{
    static Logger logger = Logger.getLogger(AbstractXMLRepository.class
            .getSimpleName());

    private static final String DOCUMENT = "document";
    private static final String ENTITYCLASS = "entityClass";
    private static final Object PROPERTY = "property";

    protected Validator<Entity> validator;

    protected String filename = "test.xml";

    public AbstractXMLRepository()
    {
        validator = null;

    }
    public AbstractXMLRepository(Validator validator)
    {
        this.validator = validator;

    }
    public void setValidator(Validator validator)
    {
        this.validator = validator;
    }
    @Override
    public int size()
    {
        // TODO Auto-generated method stub
        return getAll().size();
    }

    private Object createEntity(String className)
    {
        try
        {
            Object clasa = Class.forName(className).newInstance();

            return clasa;
        }
        catch (InstantiationException e)
        {
            logger.severe("No default constructor for class " + className);
            throw new CommonException(e);
        }
        catch (IllegalAccessException e)
        {
            logger.severe("Illegal access");
            throw new CommonException(e);
        }
        catch (ClassNotFoundException e)
        {
            logger.severe("Class not found");
            throw new CommonException(e);
        }
    }
    public List<Entity> getAll()
    {
        List<Field> fieldList = new ArrayList<Field>();
        List<Entity> entities = new ArrayList<Entity>();
        String className = null;
        // TODO Auto-generated method stub
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try
        {
            InputStream fileInputStream = ClassLoader.getSystemResourceAsStream(filename);
            if (fileInputStream == null)
            {
                logger.severe(filename + " not found");
                throw new CommonException(filename + " not found");
            }
            XMLStreamReader reader = factory
                    .createXMLStreamReader(fileInputStream);
            Object currentEntity = null;
            while (reader.hasNext())
            {
                reader.next();
                int eventType = reader.getEventType();
                String elementName = null;
                switch (eventType)
                {
                    case XMLStreamReader.START_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals(DOCUMENT))
                        {
                            className = reader.getAttributeValue(null,
                                    ENTITYCLASS);
                        }
                        else if (elementName.equals(className))
                        {
                            currentEntity = createEntity(className);
                        }
                        else if (elementName.equals(PROPERTY))
                        {
                            fieldList.addAll(Arrays.asList(currentEntity.getClass().getDeclaredFields()));
                            for (Field field : fieldList)
                            {
                                String fieldName = new String(field.getName());
                                String methodName = new String("set"
                                        + fieldName.substring(0, 1)
                                        .toUpperCase()
                                        + fieldName.substring(1));
                                String value = reader.getAttributeValue(null,
                                        fieldName);
                                Method method = null;
                                try
                                {
                                    method = currentEntity
                                            .getClass()
                                            .getMethod(methodName, String.class);
                                }
                                catch (NoSuchMethodException
                                        | SecurityException e2)
                                {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                try
                                {
                                    method.invoke(currentEntity, value);
                                }
                                catch (IllegalAccessException
                                        | IllegalArgumentException
                                        | InvocationTargetException e1)
                                {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }

                            }
                            entities.add((Entity) currentEntity);

                        }

                        break;
                    case XMLStreamReader.END_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals(DOCUMENT))
                        {
                        }
                        else if (elementName.equals(ENTITYCLASS))
                        {

                        }
                        else if (elementName.equals(PROPERTY))
                        {

                        }
                        break;
                }
            }
            reader.close();
            // logger.info("Returned all " + filename);
        }
        catch (XMLStreamException e)
        {
            logger.severe(filename + " - invalid format");
            throw new CommonException(e);
        }
        return entities;

    }
    @Override
    public Iterable<Entity> findAll()
    {
        return getAll();
    }
    @Override
    public void save(Entity e)
    {

        try {
             validator.validate(e);
        }
        catch (ValidationException errors) {

               throw new CommonException(errors.toString());
        }


        List<Field> fieldList = new ArrayList<Field>();
        fieldList.addAll(Arrays.asList(e.getClass().getDeclaredFields()));

        String validId = getValidId();
        setId(e, validId);
        List<Entity> entities = getAll();
        String className = e.getClass().getName();

        entities.add(e);
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try
        {
            XMLStreamWriter writer = factory
                    .createXMLStreamWriter(new FileWriter(filename));

            // writer.writeStartDocument();
            writer.writeStartElement(DOCUMENT);

            writer.writeAttribute(ENTITYCLASS, className);

            for (Entity entity : (Iterable<Entity>) entities)
            {

                writer.writeStartElement(className);
                writer.writeStartElement("property");

                for (Field field : fieldList)
                {
                    String fieldName = new String(field.getName());
                    String methodName = new String("get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1));
                    Method method = null;
                    try
                    {
                        method = entity.getClass().getMethod(methodName);
                    }
                    catch (NoSuchMethodException | SecurityException e2)
                    {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    try
                    {
                        writer.writeAttribute(field.getName(),
                                (String) method.invoke(entity));
                    }
                    catch (IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException e1)
                    {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();

        }
        catch (XMLStreamException err)
        {
            err.printStackTrace();
        }
        catch (IOException err)
        {
            err.printStackTrace();
        }

        // TODO Auto-generated method stub
        logger.info("Entity saved.");
    }
    @Override
    public Entity findById(String id)
    {
        Iterable<Entity> entities = findAll();
        for (Entity e : entities)
            if (equalsId(e, id) == true)
                return e;
        return null;
    }
    @Override
    public String getValidId()
    {
        String validId;
        Integer id = 1;
        Iterable<Entity> entities = findAll();
        while (true)
        {
            validId = id.toString();
            for (Entity e : entities)
                if (equalsId(e, validId))
                {
                    validId = null;
                }
            if (validId != null)
            {
                return validId;
            }
            id++;
        }
    }
    protected abstract boolean equalsId(Entity e, String id);
    protected abstract void setId(Entity e, String lastId);


}