package controller;

import com.alibaba.fastjson.JSON;
import com.company.con_sql;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class alterBranch {
    public static String run(String request){
        System.out.println(request);

        System.out.println("alterBranch");
        Map<String,String> result_success = new LinkedHashMap<>();
        Map<String,String> result_fail = new LinkedHashMap<>();
        result_fail.put("status","fail");

        result_success.put("status","success");


        if(request== null || request.trim().length() == 0) {
            result_fail.put("error","parament is void");
            //将map对象转换成Json字符串
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println(result_fail_json);
            return result_fail_json;
        }
        String username="";
        String password="";
        String bankname="";
        String oldbankname="";
        String sets="";
        String ziduan="";
        String insertvalue="";
        Map<String,String> requestpara=new HashMap<String,String>();
        final String[] items = request.split("&");
        if (items.length!=4){
            result_fail.put("error","parament is lack");
            //将map对象转换成Json字符串
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println(result_fail_json);
            return result_fail_json;
        }
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ":"+items[i]+ " enditems");
            final String[] keyAndVall = items[i].split("=");
            if (i==0){
                sets=keyAndVall[0]+"='"+keyAndVall[1]+ "', ";  //set是为了更新准备的语句
                ziduan=keyAndVall[0]+",";
                insertvalue="'"+keyAndVall[1]+"',";
            }else if (i==1){
                sets=sets  + keyAndVall[0]+"='"+keyAndVall[1]+ "'";
                ziduan=ziduan+keyAndVall[0];
                insertvalue=insertvalue+"'"+keyAndVall[1]+"'";
            }else {
                sets=sets  +", "+ items[i];
                ziduan=ziduan+", "+keyAndVall[0];
                insertvalue=insertvalue+","+keyAndVall[1];
            }

            requestpara.put(keyAndVall[0],keyAndVall[1]);

        }
        System.out.println(sets);

        bankname=requestpara.get("bankname");
        username=requestpara.get("username");
        password=requestpara.get("password");
        oldbankname=requestpara.get("oldbankname");
        String where_update = "bankname='"+requestpara.get("oldbankname")+"'";
        sets="bankname='"+bankname+"',"+"username='"+username+"',"+"password='"+password+"'";
        //先获取原有银行名字的原有账号
        String where_old_bankname = "bankname='"+requestpara.get("oldbankname")+"'";
        List<String> rescheck_oldb_usrname = con_sql.select("username","peoTable",where_old_bankname);
        /*
        比较新旧账号是否相同
         */
        if (rescheck_oldb_usrname.get(0).equals(username)){//新旧账号名称相同
            //比较新旧分行名称是否相同
            if (oldbankname.equals(bankname)){//新旧分行名字相同
                List<String> ress = con_sql.update("peoTable",sets,where_update);//更新
            }else {//新旧分行名字不同
                //检查新名字是否已经存在
                String where_new_bankname = "bankname='"+requestpara.get("bankname")+"'";
                List<String> rescheck_newbank = con_sql.select("bankname","peoTable",where_new_bankname);
                if (rescheck_newbank.get(0).equals("")){//新分行名称不存在
                    List<String> ress = con_sql.update("peoTable",sets,where_update);//更新
                }else{//新分行名称存在
                    result_fail.put("error","newbankname already exist in database");
                    String result_fail_json = JSON.toJSONString(result_fail);
                    System.out.println("result_fail_json:"+result_fail_json);
                    return result_fail_json;
                }
            }
        }else{//新旧账号不同
            String where_new_username = "username='"+username+"'";
            List<String> rescheck_new_username = con_sql.select("bankname","peoTable",where_new_username);
            if (rescheck_new_username.get(0).equals("")){//新账号不存在
                List<String> ress = con_sql.update("peoTable",sets,where_update);//更新
            }else{//新账号存在
                result_fail.put("error","newusername already exist in database");
                String result_fail_json = JSON.toJSONString(result_fail);
                System.out.println("result_fail_json:"+result_fail_json);
                return result_fail_json;
            }
        }
        String where_bank_username = "username='"+username+"' and "+"bankname='"+bankname+"'";
        List<String> resc = con_sql.select("bankname","peoTable",where_bank_username);//检查新的修改是不是已经存在，如果存在，则这个新的名称不能用
        if (resc.get(0).equals(bankname)) {//没有更新到表里报错返回
            String result_success_json = JSON.toJSONString(result_success);
            System.out.println(result_success_json);
            return result_success_json;
        }
        result_fail.put("error","unknown error");
        String result_fail_json = JSON.toJSONString(result_fail);
        System.out.println("result_fail_json:"+result_fail_json);
        return result_fail_json;

        /*

        sets="bankname='"+bankname+"',"+"username='"+username+"',"+"password='"+password+"'";
        String seasonzj = "bankname='"+requestpara.get("bankname")+"'";
        String where_old_bankname = "bankname='"+requestpara.get("oldbankname")+"'";
        String where_new_bankname = "bankname='"+requestpara.get("bankname")+"'";
        List<String> rescheck_oldb_usrname = con_sql.select("username","peoTable",where_old_bankname);//检查新的名字是不是已经存在，如果存在，则这个新的名称不能用
        List<String> rescheck_newb_username = con_sql.select("username","peoTable",where_new_bankname);//检查新的名字是不是已经存在
        if (!rescheck_newb_username.get(0).equals("")){//如果查询到了值
            if (!((rescheck_oldb_usrname.get(0)).equals(rescheck_newb_username.get(0)))) {//已经存在于表中则报错返回
                result_fail.put("error","newbankname already exist in database");
                String result_fail_json = JSON.toJSONString(result_fail);
                System.out.println("result_fail_json:"+result_fail_json);
                return result_fail_json;
            }
        }

        String where_new_username = "username='"+requestpara.get("username")+"'";
        //String where_new_username_password = "username='"+requestpara.get("username")+"' and "+"password='"+password+"'";
        List<String> rescheck_new_username = con_sql.select("bankname","peoTable",where_new_username);//检查新的账号是不是已经存在
        //List<String> rescheck_new_password = con_sql.select("password","peoTable",where_new_username);

        if (rescheck_new_username.get(0).equals(bankname)){
            if (!((bankname.equals(oldbankname))&&(username.equals(rescheck_new_username.get(0))))){
                result_fail.put("error","username already exist in database");
                String result_fail_json = JSON.toJSONString(result_fail);
                System.out.println("result_fail_json:"+result_fail_json);
                return result_fail_json;
            }
        }
        String where = "bankname='"+requestpara.get("oldbankname")+"'";//根据oldname寻找

        List<String> ress = con_sql.update("peoTable",sets,where);//更新
        String setproduct = "bankname='"+bankname+"'";
        List<String> resss = con_sql.update("productTable",setproduct,where);//更新
        String where_bank_username = "username='"+username+"' and "+"bankname='"+bankname+"'";
        List<String> resc = con_sql.select("bankname","peoTable",where_bank_username);//检查新的修改是不是已经存在，如果存在，则这个新的名称不能用
        if (resc.get(0).equals("")) {//没有更新到表里报错返回
            result_fail.put("error","unknown error");
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println("result_fail_json:"+result_fail_json);
            return result_fail_json;
        }

        String result_success_json = JSON.toJSONString(result_success);
        System.out.println(result_success_json);
        return result_success_json;

         */
    }


}
