package user;

import java.text.SimpleDateFormat;
import java.util.Date;

public class quarter {

    public String getQuarter(){
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
        return quarter;
    }
}
