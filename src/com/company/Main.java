package com.company;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] arg) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(9501), 0);
        server.createContext("/jtb", new TestHandler());
        server.start();
    }

    static class TestHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) {
            new Thread(new Runnable() {//并发处理
                @Override
                public void run() {
                    try{
                        String response = "hello world";

                        Headers responseHeaders = exchange.getResponseHeaders();
                        responseHeaders.set("Access-Control-Allow-Origin", "*");

                        //获得查询字符串(get)
                        String queryString =  exchange.getRequestURI().getQuery();
                        //Map<String,String> queryStringInfo = formData2Dic(queryString);
                        //获得表单提交数据(post)
                        //String postString = IOUtils.toString(exchange.getRequestBody());
                        //Map<String,String> postInfo = formData2Dic(postString);
                        //
                        //String query_sql = new con_sql().run();
                        //String request = String[2]
                        //login.run(queryString);
                        String query_path = exchange.getRequestURI().getPath();
                        System.out.println(query_path);
                        System.out.println(queryString);

                        final String[] aaaa = query_path.split("/");
                /*
                for (int i = 0; i < aaaa.length; i++) {
                    System.out.println(i + ":"+aaaa[i]+ "end");
                }

                 */
                        String para = "controller."+aaaa[2];
                        System.out.println(para);
                        Class class_path = Class.forName(para);
                        //Constructor c=class_path.getConstructor(String.class);//获取有参构造
                        Method method = class_path.getMethod("run", String.class);
                        response= (String) method.invoke(class_path,queryString);//获取所在类的对象


                        exchange.sendResponseHeaders(200,0);

                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }catch (IOException ie) {

                    } catch (Exception e) {

                    }
                }
            }).start();

        }
    }

    public static Map<String,String> formData2Dic(String formData ) {
        Map<String,String> result = new HashMap<>();
        if(formData== null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item ->{
            final String[] keyAndVal = item.split("=");
            if( keyAndVal.length == 2) {
                try{
                    final String key = URLDecoder.decode( keyAndVal[0],"utf8");
                    final String val = URLDecoder.decode( keyAndVal[1],"utf8");
                    System.out.println(key+":"+val);
                    result.put(key,val);
                }catch (UnsupportedEncodingException e) {}
            }
        });
        return result;
    }
}