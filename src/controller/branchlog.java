package controller;

import com.alibaba.fastjson.JSON;
import com.company.con_sql;
import user.quarter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class branchlog {
    public static String run(String request){
        System.out.println(request);

        System.out.println("login");
        Map<String,String> result_success = new HashMap<>();
        Map<String,String> result_fail = new HashMap<>();
        result_fail.put("status","fail");

        result_success.put("status","success");


        if(request== null || request.trim().length() == 0) {
            result_fail.put("error","parament is void");
            //将map对象转换成Json字符串
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println(result_fail_json);
            return result_fail_json;
        }
        String bankname="";
        String quarter="";
        Map<String,String> para=new HashMap<String,String>();
        final String[] items = request.split("&");
        if (items.length!=2){
            result_fail.put("error","parament is lack");
            //将map对象转换成Json字符串
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println(result_fail_json);
            return result_fail_json;
        }
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ":"+items[i]+ " enditems");
            final String[] keyAndVall = items[i].split("=");
            para.put(keyAndVall[0],keyAndVall[1]);
        }
        /*
        AtomicReference<String> username= new AtomicReference<String>();
        AtomicReference<String> password= new AtomicReference<String>();
        int i;
        //String username;
        Arrays.stream(items).forEach(item ->{
            final String[] keyAndVal = item.split("=");

            if( keyAndVal.length == 2) {
                try{
                    final String key = URLDecoder.decode( keyAndVal[0],"utf8");
                    final String val = URLDecoder.decode( keyAndVal[1],"utf8");

                    if(key=="username"){
                        username.set(val);
                    }
                    if(key=="password"){
                        password.set(val);
                    }
                    System.out.println(key+":"+val);

                    //result.put(key,val);
                }catch (UnsupportedEncodingException e) {}
            }
        });
*/
        quarter quarterr = new quarter();
        quarter=quarterr.getQuarter();//获取季度字符串字段

        //String wheres = "bankname='"+para.get("bankname") +"' and quarter='"+para.get("quarter")+"'";
        String wheres = "bankname='"+para.get("bankname") +"' and quarter='"+quarter+"'";
        //String item="bankname as bankname,quarter as quarter,own_net as own_net";
        Map<String,Object> res = con_sql.selectlog("*","productTable",wheres);
        //System.out.println("res:"+res.get(0));
        if (res.get("status")=="success"){
            res.remove("pid");
            res.remove("status");
            //String result= JSON.toJSONString(res);
            //JSONObject result1=JSON.parseObject(result);
            //Map<String,Map<String,Object>> mapp = new HashMap<String,Map<String,Object>>();
            Map<String,Object> mapp = new LinkedHashMap<String,Object>();
            mapp.put("status", "success");
            mapp.put("log",res);

            String result_success_json = JSON.toJSONString(mapp);
            //result_success.put("log",result_json);
            //String result_success_json = JSON.toJSONString(result_json);
            System.out.println("result_success_json:"+result_success_json);
            return result_success_json;
        }
        if (res.get("status")=="fail"){
            result_fail.put("error","no query Feedback");
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println("result_fail_json:"+result_fail_json);
            return result_fail_json;
        }
        result_fail.put("error","no result");
        String result_fail_json = JSON.toJSONString(result_fail);
        System.out.println("result_fail_json:"+result_fail_json);
        return result_fail_json;
    }
}
