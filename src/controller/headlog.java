package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.con_sql;

import java.text.SimpleDateFormat;
import java.util.*;

public class headlog {
    public static String run(String request){
        System.out.println(request);

        System.out.println("headlog");
        Map<String,String> result_success = new LinkedHashMap<>();
        Map<String,String> result_fail = new LinkedHashMap<>();
        result_fail.put("status","fail");

        result_success.put("status","success");

        /*
        if(request== null || request.trim().length() == 0) {
            result_fail.put("error","parament is void");
            //将map对象转换成Json字符串
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println(result_fail_json);
            return result_fail_json;
        }

         */

        Date todaydate=new Date();
        //Date todaydate=sdf.parse("2020-01-01 10:06:11");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
        String today = formatter.format(todaydate);
        //String today ="2020-04-01 09:52:26";//测试用
        System.out.println(today);
        String todaymonth=formattermonth.format(todaydate);
        System.out.println(todaymonth);
        String todayyear=formatteryear.format(todaydate);
        System.out.println(todayyear);
        String quarter="";
        if ((Integer.parseInt(todaymonth)==1)||(Integer.parseInt(todaymonth)==2)||(Integer.parseInt(todaymonth)==3)) {
            int todayyearr = Integer.parseInt(todayyear) - 1;
            quarter = todayyearr + "4";
            System.out.println("quarter:"+quarter);
        }
        if ((Integer.parseInt(todaymonth)==4)||(Integer.parseInt(todaymonth)==5)||(Integer.parseInt(todaymonth)==6)) {
            //int todayyearr = Integer.parseInt(todayyear) - 1;
            quarter = todayyear + "1";
            System.out.println("quarter:"+quarter);
        }
        if ((Integer.parseInt(todaymonth)==7)||(Integer.parseInt(todaymonth)==8)||(Integer.parseInt(todaymonth)==9)) {
            //int todayyearr = Integer.parseInt(todayyear) - 1;
            quarter = todayyear + "2";
            System.out.println("quarter:"+quarter);
        }
        if ((Integer.parseInt(todaymonth)==10)||(Integer.parseInt(todaymonth)==11)||(Integer.parseInt(todaymonth)==12)) {
            //int todayyearr = Integer.parseInt(todayyear) - 1;
            quarter = todayyear + "3";
            System.out.println("quarter:"+quarter);
        }
        /*
        String bankname="";
        //String quarter="";
        Map<String,String> para=new HashMap<String,String>();
        final String[] items = request.split("&&");
        if (items.length!=1){
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

         */
        /*
        先检查本季度有没有总计的记录，没有先添加一个空记录。保证在这个季度结束后谁先进入系统谁就先建一个新的总计记录。
        先求和，更新总计。再和各个分支行的数据一起导出来
         */
        String seasonzj = "bankname='总计' and quarter='"+quarter+"'";//测试代替
        //String seasonzj = "bankname='总计' and quarter='20203'";//测试
        List<String> reszj = con_sql.select("bankname","productTable",seasonzj);
        if (reszj.get(0).equals("")) {
            String ziduans="bankname,quarter";
            String insertvalues="'总计','"+quarter+"'";//测试代替
            //String insertvalues="'总计','20203'";//test
            List<String> ress = con_sql.insert("productTable", ziduans, insertvalues);//插入一条总计记录
        }
        String wheres = "quarter='"+quarter+"'";//测试代替
        //String wheres = "quarter='20203'";//测试用
        con_sql.sumallupdate(quarter);//测试代替
        //con_sql.sumallupdate("20203");//测试s

        List<JSONObject> res = con_sql.selectheadlog("*","productTable",wheres);


        //con_sql.sumall(quarter);
        //System.out.println("res:"+res.get(0));
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
