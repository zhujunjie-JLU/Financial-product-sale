package controller;

import com.alibaba.fastjson.JSON;
import com.company.con_sql;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/*
* 对于增加人员，仅仅在peoTable添加记录就可以。不需要再productTable添加记录和改动
*/
public class addBranch {
    public static String run(String request){
        System.out.println(request);

        System.out.println("addBranch");
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
        final String[] items = request.split("&");
        if (items.length!=3){
            result_fail.put("error","parament is lack");
            //将map对象转换成Json字符串
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println(result_fail_json);
            return result_fail_json;
        }
        Map<String,String> requestpara=new HashMap<String,String>();
        String sets = "";
        String ziduan="";
        String insertvalue="";
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ":"+items[i]+ " enditems");
            final String[] keyAndVall = items[i].split("=");
            if (i==0){
                sets=keyAndVall[0]+"='"+keyAndVall[1]+ "', ";//set是为了更新准备的语句
                ziduan=keyAndVall[0]+",";//ziduan是为insert增加记录的语句，表示插入记录对应的字段
                insertvalue="'"+keyAndVall[1]+"',";//insertvalue是为insert增加记录的语句，表示插入字段与上一句对应的字段的值
            }else if (i==1){
                sets=sets  + keyAndVall[0]+"='"+keyAndVall[1]+ "'";
                ziduan=ziduan+keyAndVall[0];
                insertvalue=insertvalue+"'"+keyAndVall[1]+"'";
            }else {
                sets=sets  +", "+ items[i];
                ziduan=ziduan+", "+keyAndVall[0];
                insertvalue=insertvalue+",'"+keyAndVall[1]+"'";//插入的值全是字符串，要加单引号
            }
            requestpara.put(keyAndVall[0],keyAndVall[1]);

        }
        System.out.println(sets);
        /*
        先检查本季度有没有该分支行的记录，没有先添加一个空记录。保证在这个季度结束后谁先进入系统谁就先建一个新的总计记录。
        先求和，更新总计。再和各个分支行的数据一起导出来
         */
        String seasonzj = "bankname='"+requestpara.get("bankname")+"'";

        List<String> reszj = con_sql.select("bankname","peoTable",seasonzj);//检查

        if (reszj.get(0).equals("")) {
            //tring ziduans="bankname,quarter";
            //String insertvalues="'总计','"+quarter+"'";//测试代替
            //String insertvalues="'总计','20203'";//test

            List<String> ress = con_sql.insert("peoTable", ziduan, insertvalue);//插入一条分支行记录

        }else {
            result_fail.put("error","already exist in database");
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println("result_fail_json:"+result_fail_json);
            return result_fail_json;
        }

        String result_success_json = JSON.toJSONString(result_success);
        System.out.println("result_success_json:"+result_success_json);
        return result_success_json;
    }
}
