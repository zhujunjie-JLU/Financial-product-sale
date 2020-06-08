package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.con_sql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class getAllBranch {
    public static String run(String request){
        System.out.println(request);

        System.out.println("getAllBranch");
        Map<String,String> result_success = new LinkedHashMap<>();
        Map<String,String> result_fail = new LinkedHashMap<>();
        result_fail.put("status","fail");

        result_success.put("status","success");

        //String wheres = "quarter='"+quarter+"'";//测试代替
        List<JSONObject> res = con_sql.selectheadlog("*","peoTable","");
        int len = res.size();
        System.out.println(len);
        if (len>0){
            //res.remove("pid");
            //res.remove("status");
            //Map<String,List<Map<String, Object>>> mapp = new HashMap<String,List<Map<String, Object>>>();
            //mapp.put("log",res);

            Map<String,Object> mapp =new LinkedHashMap<String,Object>();
            //List<Map<String,String>> lists = new ArrayList<Map<String,String>>();

            mapp.put("status","success");
            mapp.put("log",res);

            String result_success_json = JSON.toJSONString(mapp);
            //result_success.put("log",result_json);
            //String result_success_json = JSON.toJSONString(result_json);
            System.out.println("result_success_json:"+result_success_json);
            return result_success_json;
        }else if (len==0){
            result_fail.put("error","no query feedback");
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
