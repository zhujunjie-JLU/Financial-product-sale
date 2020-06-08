package controller;

import com.alibaba.fastjson.JSON;
import com.company.con_sql;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/*
只删除peoTable，理财数据中的该分支行的记录不会删除
 */
public class deleteBranch {
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


        String ziduan="";
        String insertvalue="";
        Map<String,String> requestpara=new HashMap<String,String>();
        final String[] items = request.split("&");
        if (items.length!=3){
            result_fail.put("error","parament is lack");
            //将map对象转换成Json字符串
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println(result_fail_json);
            return result_fail_json;
        }
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ":"+items[i]+ " enditems");
            final String[] keyAndVall = items[i].split("=");
            requestpara.put(keyAndVall[0],keyAndVall[1]);

        }

        bankname=requestpara.get("bankname");
        username=requestpara.get("username");
        password=requestpara.get("password");

        String seasonzj = "bankname='"+requestpara.get("bankname")+"'";
        List<String> rescheck = con_sql.select("bankname","peoTable",seasonzj);//检查名字是不是已经存在，如果不存在，
        if (rescheck.get(0).equals("")) {//已经存在于表中则报错返回
            result_fail.put("error","bankname not in database");
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println("result_fail_json:"+result_fail_json);
            return result_fail_json;
        }
        String where = "bankname='"+requestpara.get("bankname")+"'";//根据oldname寻找

        String res=con_sql.delete("peoTable",where);//根据bankname删除

        String setproduct = "bankname='"+bankname+"'";
        //List<String> resss = con_sql.update("productTable",setproduct,where);//

        List<String> resc = con_sql.select("bankname","peoTable",seasonzj);//检查新的名字是不是已经存在，如果存在，则这个新的名称不能用
        if (!resc.get(0).equals("")) {//没有更新到表里报错返回
            result_fail.put("error","not delete");
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println("result_fail_json:"+result_fail_json);
            return result_fail_json;
        }

        String result_success_json = JSON.toJSONString(result_success);
        System.out.println(result_success_json);
        return result_success_json;
    }
}
