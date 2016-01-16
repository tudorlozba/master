package Common.core;

/**
 * Created by tudor on 14/01/16.
 */
public interface AsyncCallback<T> {
    void onSuccess(T result);

    void onException(Exception e);

}

