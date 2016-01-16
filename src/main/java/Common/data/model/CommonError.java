package Common.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tudor on 14/01/16.
 */
public class CommonError {
    List<String> errorList;

    public CommonError()
    {
        errorList = new ArrayList<>();
    }

    public int size()
    {
        return errorList.size();
    }
    public void add(String error)
    {
        errorList.add(error);
    }
    @Override
    public String toString()
    {
        return "MyError [errorList=" + errorList + "]";
    }

}


