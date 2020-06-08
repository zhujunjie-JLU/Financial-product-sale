package controller;

import com.alibaba.fastjson.JSON;
import com.company.con_sql;
import user.quarter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class branchCmt {
    public static String run(String request) throws ParseException {
        System.out.println(request);

        System.out.println("branchCmt");
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


        final String[] items = request.split("&");
        if (items.length!=22){
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

        //quarter=requestpara.get("quarter");
        quarter quarterr = new quarter();
        quarter=quarterr.getQuarter();//获取季度字符串字段

        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ":"+items[i]+ " enditems");


            final String[] keyAndVall = items[i].split("=");
            if (keyAndVall[0].equals("quarter")){
                sets=sets  + keyAndVall[0]+"='"+quarter+ "'";
                ziduan=ziduan+keyAndVall[0];
                insertvalue=insertvalue+"'"+quarter+"'";
                continue;
            }
            if (i==0){
                sets=keyAndVall[0]+"='"+keyAndVall[1]+ "', ";
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
            /*
            for (int j = 0; j < keyAndVall.length; j++) {
                System.out.println(j + ":"+keyAndVall[j]+ " endkeyAndVall");
            }
             */
            requestpara.put(keyAndVall[0],keyAndVall[1]);

            /*
            switch (i){
                case 0:
                    bankname=keyAndVall[1];
                    System.out.println(keyAndVall[0]);
                    break;
                case 1:
                    own_net=Integer.valueOf(keyAndVall[1]);
                    break;
                case 2:
                    own_sd=Integer.parseInt(keyAndVall[1]);
                    break;
                case 3:
                    own_smartc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 4:
                    own_specc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 5:
                    consI_net=Integer.parseInt(keyAndVall[1]);
                    break;
                case 6:
                    consI_sd=Integer.parseInt(keyAndVall[1]);
                    break;
                case 7:
                    consI_smartc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 8:
                    consI_specc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 9:
                    consO_net=Integer.parseInt(keyAndVall[1]);
                    break;
                case 10:
                    consO_sd=Integer.parseInt(keyAndVall[1]);
                    break;
                case 11:
                    consO_smartc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 12:
                    consO_specc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 13:
                    bSum_net=Integer.parseInt(keyAndVall[1]);
                    break;
                case 14:
                    bSum_sd=Integer.parseInt(keyAndVall[1]);
                    break;
                case 15:
                    bSum_smartc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 16:
                    bSum_specc=Integer.parseInt(keyAndVall[1]);
                    break;
                case 17:
                    d2own=Integer.parseInt(keyAndVall[1]);
                    break;
                case 18:
                    d2insure=Integer.parseInt(keyAndVall[1]);
                    break;
                case 19:
                    d2others=Integer.parseInt(keyAndVall[1]);
                    break;
                case 20:
                    d2sum=Integer.parseInt(keyAndVall[1]);
                    break;
                default:
                    System.out.println("branchCmt的switch语句出错啦！");
                    break;
            }

             */

        }
        System.out.println(sets);



        bankname=requestpara.get("bankname");
        own_net=Integer.parseInt(requestpara.get("own_net"));
        own_sd=Integer.parseInt(requestpara.get("own_sd"));own_smartc=Integer.parseInt(requestpara.get("own_smartc"));own_specc=Integer.parseInt(requestpara.get("own_specc"));consI_net=Integer.parseInt(requestpara.get("consI_net"));consI_sd=Integer.parseInt(requestpara.get("consI_sd"));consI_smartc=Integer.parseInt(requestpara.get("consI_smartc"));consI_specc=Integer.parseInt(requestpara.get("consI_specc"));consO_net=Integer.parseInt(requestpara.get("consO_net"));consO_sd=Integer.parseInt(requestpara.get("consO_sd"));consO_smartc=Integer.parseInt(requestpara.get("consO_smartc"));consO_specc=Integer.parseInt(requestpara.get("consO_specc"));bSum_net=Integer.parseInt(requestpara.get("bSum_net"));bSum_sd=Integer.parseInt(requestpara.get("bSum_sd"));bSum_smartc=Integer.parseInt(requestpara.get("bSum_smartc"));bSum_specc=Integer.parseInt(requestpara.get("bSum_specc"));d2own=Integer.parseInt(requestpara.get("d2own"));d2insure=Integer.parseInt(requestpara.get("d2insure"));d2others=Integer.parseInt(requestpara.get("d2others"));d2sum=Integer.parseInt(requestpara.get("d2sum"));
        //接下来判断提交时间是不是在规定时间之内，判断是不是在提交时间之内
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
        String targetdate="";
        //不是这四个月份提交肯定不行
        if ((Integer.parseInt(todaymonth)==4)||(Integer.parseInt(todaymonth)==7)||(Integer.parseInt(todaymonth)==10)||(Integer.parseInt(todaymonth)==1))
        {
            targetdate=todayyear+"-"+todaymonth+"-05 00:00:01";
            /*
            if (Integer.parseInt(todaymonth)==1){
                int todayyearr = Integer.parseInt(todayyear)-1;
                targetdate=todayyear+"-"+todaymonth+"-05 00:00:01";
            }

             */
            System.out.println(targetdate);
            //将字符串形式的时间转化为Date类型的时间
            Date todays=sdf.parse(today);
            Date targetdates=sdf.parse(targetdate);
            //Date类的一个方法，如果a早于b返回true，否则返回false
            if(todays.before(targetdates)){}
            else{
                result_fail.put("error","not commit day");
                String result_fail_json = JSON.toJSONString(result_fail);
                System.out.println("result_fail_json:"+result_fail_json);
                return result_fail_json;
            }
        }else{
            result_fail.put("error","not commit month");
            String result_fail_json = JSON.toJSONString(result_fail);
            System.out.println("result_fail_json:"+result_fail_json);
            return result_fail_json;
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
        String season = "bankname='"+bankname +"' and quarter='"+quarter+"'";
        List<String> res = con_sql.select("bankname","productTable",season);//查询是否有这个记录存在，如果有，就在原记录上update，否则insert
        /*
        这里在分支行提交时候，检查总行该季度的记录是否存在，如果不存在就插入总行（”总计“）该季度的记录。总行在登陆时也会有同样操作。应该单独写成一个方法，这样太臃肿了
         */
        String seasonzj = "bankname='总计' and quarter='"+quarter+"'";
        List<String> reszj = con_sql.select("bankname","productTable",seasonzj);
        if (reszj.get(0).equals("")) {
            String ziduans="bankname,quarter";
            String insertvalues="'总计','"+quarter+"'";
            List<String> ress = con_sql.insert("productTable", ziduans, insertvalues);//插入一条分行的提交记录
        }
        System.out.println("res:"+res.get(0));
        if (res.get(0).equals("")){//如果这条记录不存在，就插入
            List<String> ress = con_sql.insert("productTable",ziduan,insertvalue);//插入一条分行的提交记录
            List<String> check1 = con_sql.select("bankname","productTable",season);//检查是否插入
            if (check1.get(0).equals("")){//插入失败
                String result_fail_json=fail(result_fail);
                return result_fail_json;
            }
            String result_success_json = JSON.toJSONString(result_success);
            System.out.println(result_success_json);
            return result_success_json;
        }
        List<String> ress = con_sql.update("productTable",sets,season);//更新分行的一条记录,条件一定要银行名字加上季度！
        List<String> check0 = con_sql.select("d2sum","productTable",season);

        if (check0.get(0).equals("")){
            String result_fail_json=fail(result_fail);
            return result_fail_json;
        }
        String result_success_json = JSON.toJSONString(result_success);
        System.out.println(result_success_json);
        return result_success_json;

    }

    private static String fail(Map<String,String> result_fail) {
        result_fail.put("error","no Feedback");
        String result_fail_json = JSON.toJSONString(result_fail);
        System.out.println("result_fail_json:"+result_fail_json);
        return result_fail_json;
    }
}
