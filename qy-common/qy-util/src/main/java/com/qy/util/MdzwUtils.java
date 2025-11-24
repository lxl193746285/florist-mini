package com.qy.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

@Service
public class MdzwUtils {

    //获取小程序码
    public static String getEw(String scene, String url, String page, String envVersion, Integer width) {
        HashMap<String, Object> params = new HashMap<>();
        //scene:参数
        params.put("scene", scene);
        params.put("page", page);
        params.put("env_version", envVersion);
        params.put("width", width);
        JSONObject resultJson = new JSONObject();
        Iterator it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            resultJson.put(key, params.get(key));
        }
//            JSONObject json=JSONObject/f(params);
        String val = "";
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        String ew = wxPost(url, resultJson, val);
        return ew;
    }

    public static String wxPost(String uri, JSONObject paramJson, String fileName) {
        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            // 开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            System.out.println(bis);
            try (InputStream is = httpURLConnection.getInputStream();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ) {
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }

                // String s = "data:image/jpeg;base64," + ;
                return Base64.getEncoder().encodeToString(baos.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成程序码错误！");
        }


    }

}
