package Common.domain;


public class User {
    public String name;
    public String password;
    public String id;


    public User(){
        this.name="";
        this.password ="";
        this.id="";
    }

    public User(String name, String password){
        this.name=name;
        this.password = password;
    }
    public User(String id ,String name, String password){
        this.name=name;
        this.password = password;
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setId(String id){ this.id = id;}


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
}
