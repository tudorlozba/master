package Common.domain;


import Common.core.Serializer;

public class User extends Serializer{
    private String id;
    private String name;
    private String password;

    public User(){
        this.name="";
        this.password ="";
    }

    public User(String name, String password){
        this.name=name;
        this.password = password;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getName(){return this.name;}


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

    @Override
    public String toString() {
        return "User{" +
                "username='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}
