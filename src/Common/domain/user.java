package Common.domain;

/**
 * Created by tudor on 14/01/16.
 */
public class User {
    public String name;
    public String number;

    public User(){
        this.name="";
        this.number="";
    }

    public User(String name, String number){
        this.name=name;
        this.number=number;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setNumber(String number){
        this.number=number;
    }

    public String getName(){return this.name;}

    public String getNumber(){return this.number;}


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }
}
