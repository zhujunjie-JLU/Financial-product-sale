package user;

import java.util.ArrayList;
import java.util.List;

public class user {//这个实体没用到的
    private String id;
    private String name;
    private String password;
    private String invcode;
    private List<String> students = new ArrayList<String>();


    public void User(String id, String name,String invcode,String password)
    {
        this.id = id;
        this.name = name;
        this.invcode=invcode;
        this.password=password;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getinvcode() {
        return invcode;
    }
    public void setinvcode(String invcode) {
        this.invcode = invcode;
    }
    public String getpassword() {
        return password;
    }
    public void setpassword(String password) {
        this.password = password;
    }
}
