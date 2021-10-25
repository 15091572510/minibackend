package com.derucci.minibackend.controller;

import com.derucci.minibackend.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@RestController
public class HelloController {

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;

    @RequestMapping("/hello")
    public String hello() {
        return name + age;
    }

    @GetMapping("/qrCodeDownload")
    public void qrCodeDownload(HttpServletResponse response) throws IOException {
        String uri = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGR8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyRmJJZmh4VzZkcUMxMDAwMDAwN00AAgRRzWhhAwQAAAAA";
        byte[] bytes = HttpUtil.getHttpStream(uri);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
//        return bytes;
    }

    public static void main(String[] args) {
        File file = new File("C:/Users/Administrator/Desktop/img.txt");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ZipInputStream zipInputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream("C:/Users/Administrator/Desktop/img1.txt");
            zipInputStream = new ZipInputStream(inputStream);
            zipOutputStream = new ZipOutputStream(outputStream);
            byte[] b = new byte[1024];
            int read = 0;
            while ((read = zipInputStream.read(b)) != -1) {
                zipOutputStream.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.flush();
                zipOutputStream.close();
                outputStream.close();
                zipInputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
