package com.derucci.minibackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * http请求
 */
public class HttpUtil {

    /* GET 请求 */
    public static String getHttp(String uri) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);    // 设置该链接是可以输出的
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result.toString();
        }
    }

    /* GET 请求 */
    public static byte[] getHttpStream(String uri) throws IOException {
        HttpURLConnection connection = null;
        InputStream is = null;
        byte[] bytes = null;
        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);    // 设置该链接是可以输出的
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            is = connection.getInputStream();
            bytes = new byte[is.available()];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int index = 0;
            while(-1 != (index = is.read(bytes, 0, bytes.length))) {
                byteArrayOutputStream.write(bytes, 0, index);
            }
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
            connection.disconnect();
            return bytes;
        }
    }

    /* POST 请求 */
    public static String postHttp(String uri, Map<String, Object> param) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);    // 设置可输入
            connection.setDoOutput(true);   // 设置该链接是可以输出的
            connection.setRequestMethod("POST");    // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
            printWriter.write(objectMapper.writeValueAsString(param));
            printWriter.flush();
            printWriter.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result.toString();
        }
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("name", "wwf");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String s = objectMapper.writeValueAsString(map);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
