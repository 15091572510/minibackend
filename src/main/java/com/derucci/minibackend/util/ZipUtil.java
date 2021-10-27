package com.derucci.minibackend.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void compress(File file) throws Exception {
        if (file.isFile()) {
            fileCompress(file);
        } else if (file.isDirectory()) {
            try {
                folderCompress(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("压缩内容无效!");
        }
    }

    public static void folderCompress(File file) throws Exception {
        if (!file.isDirectory()) {
            throw new Exception("压缩内容不是文件夹");
        }

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file.getAbsolutePath() + ".zip"));

        File[] files = file.listFiles();
        for (int i=0; i<files.length; i++) {
            FileInputStream fileInputStream = new FileInputStream(files[i]);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            zipOutputStream.putNextEntry(new ZipEntry(file.getName() + file.separator + files[i].getName()));
            byte[] bytes = new byte[bufferedInputStream.available()];
            int index = 0;
            while(-1 != (index = bufferedInputStream.read(bytes, 0, bytes.length))) {
                zipOutputStream.write(bytes, 0, index);
            }

            bufferedInputStream.close();
        }

        zipOutputStream.flush();
        zipOutputStream.close();
    }

    public static void fileCompress(File file) {
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            inputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(inputStream);

            String outFile = file.getAbsolutePath().split("\\.", -1)[0] + ".zip";
            outputStream = new FileOutputStream(outFile);
            zipOutputStream = new ZipOutputStream(outputStream);
            bufferedOutputStream = new BufferedOutputStream(zipOutputStream);

            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            zipOutputStream.setComment("seven compress!");

            byte[] bytes = new byte[bufferedInputStream.available()];
            int index = 0;
            while(-1 != (index = bufferedInputStream.read(bytes, 0, bytes.length))) {
                bufferedOutputStream.write(bytes, 0, index);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void handleCompress(File file) {
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file.getAbsolutePath() + ".zip"));
            compress(zipOutputStream, file, file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void compress(ZipOutputStream zos, File file, String base) throws IOException {
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            // 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if (fileList.length == 0) {
                zos.putNextEntry(new ZipEntry(base + File.separator));
            } else {
                // 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for (int i=0; i<fileList.length; i++) {
                    compress(zos, fileList[i], base + File.separator + fileList[i].getName());
                }
            }
        } else {
            System.out.println("base: " + base);
            zos.putNextEntry(new ZipEntry(base));
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            byte[] bytes = new byte[bufferedInputStream.available()];
            int index = 0;
            while(-1 != (index = bufferedInputStream.read(bytes, 0, bytes.length))) {
                zos.write(bytes, 0, index);
            }

            bufferedInputStream.close();
        }
    }

    public static void fileDecompress(File file) {
        FileInputStream fileInputStream = null;
        ZipInputStream zipInputStream = null;
        try {
            ZipFile zipFile = new ZipFile(file);
            fileInputStream = new FileInputStream(file);
            zipInputStream = new ZipInputStream(fileInputStream);

            ZipEntry zipEntry = null;
            while(null != (zipEntry = zipInputStream.getNextEntry())) {
                System.out.println("decompress file: " + zipEntry.getName());

                File outFile = new File(file.getAbsolutePath().split(file.getName())[0] + zipEntry.getName());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));

                byte[] bytes = new byte[bufferedInputStream.available()];
                int index = 0;
                while(-1 != (index = bufferedInputStream.read(bytes, 0, bytes.length))) {
                    bufferedOutputStream.write(bytes, 0, index);
                }

                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                bufferedInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
//        File file = new File("C:/Users/Administrator/Desktop/慕思IM - 线上 - log");
//        fileCompress(file);
//        try {
//            fileCompress(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        File file = new File("C:/Users/Administrator/Desktop/慕思IM - 线上.zip");
//        fileDecompress(file);

        File file = new File("C:/Users/Administrator/Desktop/慕思IM - 线上 - log");
        handleCompress(file);



    }

}
