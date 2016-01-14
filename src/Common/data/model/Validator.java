package Common.data.model;

/**
 * Created by tudor on 14/01/16.
 */
public interface Validator<E> {
    void validate(E e) throws ValidationException;
}

