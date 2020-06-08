package com.company;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import user.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class con_sql {
    public String url="jdbc:mysql://49.233.133.30:3306/jiutai_bank";
    public String user="root";
    public String password="root";

    public static List<String> select(String item, String table, String wherename) {
        // TODO Auto-generated method stub
        //con_sql instance = new con_sql();
        mysql mysql = new mysql();
        //String driver = "com.mysql.cj.jdbc.Driver";  //定义驱动名称
        /*
        String url=instance.url;   //定义要访问的数据库名(jdbctest为数据库名)
        String user=instance.user;       //数据库用户名
        String password=instance.password;  //数据库登录密码
        System.out.println("url:"+url);
        //我的ip：49.233.133.30:3306
         */
        //String url="jdbc:mysql://49.233.133.30:3306/jiutai_bank";
        //String user="root";
        //String password="root";
        String driver = mysql.getDriver();
        String url = mysql.getUrl();
        String user = mysql.getUser();
        String password = mysql.getPassword();
        List<String> list=new ArrayList<String>();
        try {
            Class.forName(driver);                                 //加载驱动
            System.out.println("正在连接数据库...");
            Connection con = DriverManager.getConnection(url,user,password);   //声明Connection对象并获取数据库连接
            if(!con.isClosed())  System.out.println("数据库连接成功"+"\n");
            Statement stat=con.createStatement();             //创建数据库操作对象
            String sql = "select "+ item +" from " +table+" where "+ wherename;                   //执行的sql语句
            System.out.println("sql: "+sql);
            ResultSet rs=stat.executeQuery(sql);            //执行sql语句并存放结果


            //Map<String, Object> map1 = new HashMap<String,Object>();
            //System.out.println(rs);
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();

            boolean flag=false;
            //int i = 1;
            while(rs.next())                               //遍历结果集
                {
                    //String key = rsmd.getColumnLabel(i);
                    String item1=rs.getString(item);             //数据库username字段信息
                    //map1.put(key,item1);
                    System.out.println("数据库返回的item1:"+item1);
                    list.add(item1);
                    flag=true;
                    //i++;
                }
                if (flag==false){
                    System.out.println("查询没有结果");
                    rs.close();
                    stat.close();
                    con.close();
                    list.add("");
                    return list;//没有查询到值，就返回一个”“
                }


            //String res = JSON.toJSONString(map1);
            //System.out.println(res);

/*********************像IO流一样，使用过的资源都需要关闭******************************/
/***********************先打开的后关闭，后打开的先关闭********************************/
            rs.close();
            stat.close();
            con.close();
            return list;
/*************************************处理异常**************************************/
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally{
            System.out.println("\n"+"数据库get");
        }
        return list;
    }
    public static Map<String,Object> selectlog(String item, String table, String wherename) {
        // TODO Auto-generated method stub
        //con_sql instance = new con_sql();
        mysql mysql = new mysql();
        String driver = mysql.getDriver();
        String url = mysql.getUrl();
        String user = mysql.getUser();
        String password = mysql.getPassword();
        String result="";
        List<String> list=new ArrayList<String>();
        Map<String, Object> map1 = new HashMap<String,Object>();
        try {
            Class.forName(driver);                                 //加载驱动
            System.out.println("正在连接数据库...");
            Connection con = DriverManager.getConnection(url,user,password);   //声明Connection对象并获取数据库连接
            if(!con.isClosed())  System.out.println("数据库连接成功"+"\n");
            Statement stat=con.createStatement();             //创建数据库操作对象
            String sql = "select "+ item +" from " +table+" where "+ wherename;                   //执行的sql语句
            System.out.println("sql: "+sql);
            ResultSet rs=stat.executeQuery(sql);            //执行sql语句并存放结果

            //System.out.println(rs);
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();

            boolean flag=false;
            while(rs.next())                               //遍历结果集
            {

                for (int i=1;i<=count;i++){
                    String key = rsmd.getColumnLabel(i);
                    String item1=rs.getString(key);             //数据库username字段信息
                    map1.put(key,item1);
                    System.out.println("数据库返回的:"+key+":"+item1);
                }
                flag=true;
            }
            result= JSON.toJSONString(map1);
            if (flag==false){
                System.out.println("查询没有结果");
                map1.put("status","fail");
            }else {map1.put("status","success");}


            //String res = JSON.toJSONString(map1);
            //System.out.println(res);

/*********************像IO流一样，使用过的资源都需要关闭******************************/
/***********************先打开的后关闭，后打开的先关闭********************************/
            rs.close();
            stat.close();
            con.close();
            return map1;
/*************************************处理异常**************************************/
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally{
            System.out.println("\n"+"数据库get");
        }
        return map1;
    }
    public static List<String> update(String table, String sets, String wherename){
        mysql mysql = new mysql();
        String driver = mysql.getDriver();
        String url = mysql.getUrl();
        String user = mysql.getUser();
        String password = mysql.getPassword();
        List<String> list=new ArrayList<String>();
        try {
            Class.forName(driver);                                 //加载驱动
            System.out.println("正在连接数据库...");
            Connection con = DriverManager.getConnection(url,user,password);   //声明Connection对象并获取数据库连接
            if(!con.isClosed())  System.out.println("数据库连接成功"+"\n");
            Statement stat=con.createStatement();             //创建数据库操作对象

            String sql = "update "+ table +" set " +sets+" where "+ wherename;                   //执行的sql语句
            System.out.println(sql);
            PreparedStatement prl;
            prl=con.prepareStatement(sql);
            prl.executeUpdate();


            //String res = JSON.toJSONString(map1);
            //System.out.println(res);

/*********************像IO流一样，使用过的资源都需要关闭******************************/
/***********************先打开的后关闭，后打开的先关闭********************************/
            prl.close();
            stat.close();
            con.close();
            return list;
/*************************************处理异常**************************************/
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally{
            System.out.println("\n"+"数据库get");
        }
        return list;
    }//String sql3="update stu set score=98 where id=72124 "
    public static List<String> insert(String table, String ziduan, String insertvalue){
        mysql mysql = new mysql();
        String driver = mysql.getDriver();
        String url = mysql.getUrl();
        String user = mysql.getUser();
        String password = mysql.getPassword();
        List<String> list=new ArrayList<String>();
        try {
            Class.forName(driver);                                 //加载驱动
            System.out.println("正在连接数据库...");
            Connection con = DriverManager.getConnection(url,user,password);   //声明Connection对象并获取数据库连接
            if(!con.isClosed())  System.out.println("数据库连接成功"+"\n");
            Statement stat=con.createStatement();             //创建数据库操作对象

            String sql = "insert into "+ table +"("+ziduan+")"+" values("+insertvalue+")";                   //执行的sql语句
            System.out.println(sql);
            PreparedStatement prl;
            prl=con.prepareStatement(sql);
            prl.executeUpdate();


            //String res = JSON.toJSONString(map1);
            //System.out.println(res);

/*********************像IO流一样，使用过的资源都需要关闭******************************/
/***********************先打开的后关闭，后打开的先关闭********************************/
            prl.close();
            stat.close();
            con.close();
            return list;
/*************************************处理异常**************************************/
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally{
            System.out.println("\n"+"数据库get");
        }
        return list;
    }//"insert into stu(name,id,class,score,subject) values('张依依','732897','电子1704','78','软基')";
    public static List<JSONObject> selectheadlog(String item, String table, String wherename) {
        // TODO Auto-generated method stub
        //con_sql instance = new con_sql();

        mysql mysql = new mysql();
        String driver = mysql.getDriver();
        String url = mysql.getUrl();
        String user = mysql.getUser();
        String password = mysql.getPassword();

        List<JSONObject> list=new ArrayList<JSONObject>();
        try {
            Class.forName(driver);                                 //加载驱动
            System.out.println("正在连接数据库...");
            Connection con = DriverManager.getConnection(url,user,password);   //声明Connection对象并获取数据库连接
            if(!con.isClosed())  System.out.println("数据库连接成功"+"\n");
            Statement stat=con.createStatement();             //创建数据库操作对象
            String sql="";
            if (wherename.equals("")){                        //如果没有where表示没有条件
                sql = "select "+ item +" from " +table;
            }else {
                sql = "select "+ item +" from " +table+" where "+ wherename;                   //执行的sql语句
            }

            System.out.println("sql: "+sql);
            ResultSet rs=stat.executeQuery(sql);            //执行sql语句并存放结果

            int in =0;
            while(rs.next())                               //遍历结果集
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                int count = rsmd.getColumnCount();
                System.out.println("rsmd.getColumnCount():"+rsmd.getColumnCount());
                //record record=new record();
                String jsonstring = "";
                for (int i=1;i<=count;i++){
                    String key = rsmd.getColumnLabel(i);
                    String value=rs.getString(key);             //数据库username字段信息
                    //if (key=="bankname") record.setBankname(item1);
                    //if (key==record.getQuarter()) record.setQuarter(item1);
                    //map1.put(key,item1);
                    /*这里的处理是非常厉害的，我自己想出来，有效的避免被前面的值覆盖内存而失去，方法是全部转换成字符串然后再转换成JSON格式，在循环内部声明的变量会被舍弃，所以不存在反复覆盖*/
                    String listitem = "\""+key+ "\""+":"+"\""+value+"\"";
                    if (i==1){
                        jsonstring =jsonstring+listitem;
                    }else{jsonstring =jsonstring+","+listitem;}

                    System.out.println("数据库返回的:"+key+":"+value);
                }



                String recordone="{"+jsonstring+"}";
                JSONObject result=JSON.parseObject(recordone);
                System.out.println(recordone);
                list.add(in,result);
                in++;
            }

/*********************像IO流一样，使用过的资源都需要关闭******************************/
/***********************先打开的后关闭，后打开的先关闭********************************/
            rs.close();
            stat.close();
            con.close();
            return list;
/*************************************处理异常**************************************/
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally{
            System.out.println("\n"+"数据库get");
        }
        return list;
    }

    public static void sumallupdate(String quarter) {
        mysql mysql = new mysql();
        String driver = mysql.getDriver();
        String url = mysql.getUrl();
        String user = mysql.getUser();
        String password = mysql.getPassword();
        JSONObject result;
        try {
            Class.forName(driver);                                 //加载驱动
            System.out.println("正在连接数据库...");
            Connection con = DriverManager.getConnection(url,user,password);   //声明Connection对象并获取数据库连接
            if(!con.isClosed())  System.out.println("数据库连接成功"+"\n");
            Statement stat=con.createStatement();             //创建数据库操作对象
            String sql = "select * from productTable where quarter='"+quarter+"'";                   //执行的sql语句
            System.out.println("求和查询sql: "+sql);
            ResultSet rs=stat.executeQuery(sql);            //执行sql语句并存放结果
            Map<String, Integer> updatesum = new HashMap<String, Integer>();
            /*
            int own_net=0;
            int own_sd=0;
            int own_smartc=0;
            int own_specc=0;
            int consI_net=0;
            int consI_sd=0;
            int consI_smartc=0;
            int consI_specc=0;
            int consO_net=0;
            int consO_sd=0;
            int consO_smartc=0;
            int consO_specc=0;
            int bSum_net=0;
            int bSum_sd=0;
            int bSum_smartc=0;
            int bSum_specc=0;
            int d2own=0;
            int d2insure=0;
            int d2others=0;
            int d2sum=0;

             */

            while(rs.next())                               //遍历结果集
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                int count = rsmd.getColumnCount();
                System.out.println("rsmd.getColumnCount():"+rsmd.getColumnCount());
                String jsonstring = "";

                for (int i=1;i<=count;i++){
                    String key = rsmd.getColumnLabel(i);
                    String zj = rs.getString("bankname");
                    if (zj.equals("总计")){
                        continue;
                    }
                    //System.out.println(i+"---key:"+key);
                    if ((key.equals("bankname"))||(key.equals("quarter"))||(key.equals("pdid"))){
                        //System.out.println("continue");
                        continue;
                    }

                    //System.out.println("key:"+key);
                    int value=rs.getInt(key);             //数据库username字段信息
                    if (updatesum.containsKey(key)){//如果上一次循环时已经有这个key，则将上次的value与这次的想加，逐渐累计求和
                        updatesum.put(key,updatesum.get(key)+value);
                    }else {//如果是第一个记录则要将这次的放入map
                        updatesum.put(key,value);
                    }
                    System.out.println("数据库返回的:"+key+":"+value);
                }
                /*
                own_net=own_net+rs.getInt("own_net");
                own_sd=own_sd+rs.getInt("own_sd");
                own_smartc=own_smartc+rs.getInt("own_smartc");
                own_specc=own_specc+rs.getInt("own_specc");
                consI_net=consI_net+rs.getInt("consI_net");
                consI_sd=consI_sd+rs.getInt("consI_sd");
                consI_smartc=consI_smartc+rs.getInt("consI_smartc");
                consI_specc=consI_specc+rs.getInt("consI_specc");
                consO_net=consO_net+rs.getInt("consO_net");
                consO_sd=consO_sd+rs.getInt("consO_sd");
                consO_smartc=consO_smartc+rs.getInt("consO_smartc");
                consO_specc=consO_specc+rs.getInt("consO_specc");
                bSum_net=bSum_net+rs.getInt("bSum_net");
                bSum_sd=bSum_sd+rs.getInt("bSum_sd");
                bSum_smartc=bSum_smartc+rs.getInt("bSum_smartc");
                bSum_specc=bSum_specc+rs.getInt("bSum_specc");
                d2own=d2own+rs.getInt("d2own");
                d2insure=d2insure+rs.getInt("d2insure");
                d2others=d2others+rs.getInt("d2others");
                d2sum=d2sum+rs.getInt("d2sum");
                */
                //String recordone="{"+jsonstring+"}";
                //JSONObject result=JSON.parseObject(recordone);
                //System.out.println(recordone);
                //list.add(result);
            }
            String sqlset ="";
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            for (int i=1;i<=count;i++){//该循环主要是将各个字段连接起来形成sql的update语句中的set部分，将updatesum中的key与value取出连接
                String key = rsmd.getColumnLabel(i);
                if ((key.equals("bankname"))||(key.equals("quarter"))||(key.equals("pdid"))){
                    continue;
                }
                int value=updatesum.get(key);
                if (sqlset.equals("")){
                    sqlset=key+"="+value;
                }else {
                    sqlset=sqlset+", "+key+"="+value;
                }
            }
            System.out.println(sqlset);
            String sqlwhere = "bankname='总计' and quarter='"+quarter+"'";
            update("productTable",sqlset,sqlwhere);
            /*
            //result= JSON.toJSONString(map1);
            Map<String, Object> map2 = new HashMap<String,Object>();
            if (flag==false){
                System.out.println("查询没有结果");
                map2.put("status","fail");
                list.add(map2);
            }else {
                map2.put("status","success");
                list.add(map2);
            }

             */

/*********************像IO流一样，使用过的资源都需要关闭******************************/
/***********************先打开的后关闭，后打开的先关闭********************************/
            rs.close();
            stat.close();
            con.close();
            return ;
/*************************************处理异常**************************************/
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally{
            System.out.println("\n"+"数据库get");
        }
        return ;
    }

    public static String delete(String table, String where) {//delete from stu where id=452676
        mysql mysql = new mysql();
        String driver = mysql.getDriver();
        String url = mysql.getUrl();
        String user = mysql.getUser();
        String password = mysql.getPassword();
        List<String> list=new ArrayList<String>();
        String result="fail";
        try {
            Class.forName(driver);                                 //加载驱动
            System.out.println("正在连接数据库...");
            Connection con = DriverManager.getConnection(url,user,password);   //声明Connection对象并获取数据库连接
            if(!con.isClosed())  System.out.println("数据库连接成功"+"\n");
            Statement stat=con.createStatement();             //创建数据库操作对象

            String sql = "delete "+ " from " +table+" where "+ where;                   //执行的sql语句
            System.out.println(sql);
            PreparedStatement prl;
            prl=con.prepareStatement(sql);
            prl.executeUpdate();


            //String res = JSON.toJSONString(map1);
            //System.out.println(res);

/*********************像IO流一样，使用过的资源都需要关闭******************************/
/***********************先打开的后关闭，后打开的先关闭********************************/
            prl.close();
            stat.close();
            con.close();
            result="success";
            return result;
/*************************************处理异常**************************************/
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }finally{
            System.out.println("\n"+"数据库get");
        }
        return result;
    }
}
