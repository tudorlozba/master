package Common.domain;

import Common.core.Serializer;

/**
 * Created by tudor on 15/01/16.
 */
public class Post extends Serializer{

    private String user;
    private String content;

    public Post(String user, String content){
        this.user = user;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUser(){
        return this.user;
    }

    public String getContent(){
        return this.content;
    }
}
