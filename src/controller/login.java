package controller;

import com.alibaba.fastjson.JSON;
import com.company.con_sql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class login {
    public String url="jdbc:mysql://127.0.0.1:3306/jiutai_bank";
    public String user="root";
    public String password="root";


    public static String run(String request){
        System.out.println(request);

        System.out.println("login");
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
            for (int j = 0; j < keyAndVall.length; j++) {
                //System.out.println(j + ":"+keyAndVall[j]+ " endkeyAndVall");
            }
            if (i==0){
                username = keyAndVall[1];
            }
            if (i==1){
                password = keyAndVall[1];
            }
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
        String wheres = "username='"+username +"' and password='"+password+"'";
        List<String> res = con_sql.select("bankname","peoTable",wheres);
        System.out.println("res:"+res.get(0));
        if (res.get(0)==""){
            result_fail.put("error","no Feedback");
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println("result_fail_json:"+result_fail_json);
            return result_fail_json;
        }
        result_success.put("bankname",res.get(0));
        String result_success_json = JSON.toJSONString(result_success);
        System.out.println(result_success_json);
        return result_success_json;
    }
}
