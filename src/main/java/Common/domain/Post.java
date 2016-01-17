package Common.domain;

import Common.core.Serializer;

/**
 * Created by tudor on 15/01/16.
 */
public class Post extends Serializer{

    private String user;
    private String content;
    public String getUser(){
        return this.user;
    }

    public String getContent(){
        return this.content;
    }
}
