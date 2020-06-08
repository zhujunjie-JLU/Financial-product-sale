package user;

public class mysql {
    String url="jdbc:mysql://49.233.133.30:3306/jiutai_bank";//49.233.133.30 127.0.0,1
    String user="root";
    String password="root";
    String driver = "com.mysql.cj.jdbc.Driver";  //定义驱动名称
    public String getUrl(){
        return this.url;
    }
    public String getUser(){
        return this.user;
    }
    public String getPassword(){
        return  this.password;
    }
    public String getDriver(){
        return this.driver;
    }
}
