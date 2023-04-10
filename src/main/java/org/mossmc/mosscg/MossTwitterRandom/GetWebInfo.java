package org.mossmc.mosscg.MossTwitterRandom;

import sun.net.www.protocol.http.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.mossmc.mosscg.MossTwitterRandom.Logger.sendInfo;

public class GetWebInfo {
    public static String getLink(String twitterID,String statusID) {
        return "https://nitter.cz/"+twitterID+"/status/"+statusID+"#m";
    }

    public static String getData(String target) {
        int n = 10;
        for (int i=0;i<n;i++) {
            sendInfo("正在尝试获取数据......第"+(i+1)+"次尝试......");
            try {
                String data = "";
                if (target.startsWith("https://")) data = readHttpsData(target);
                if (target.startsWith("http://")) data = readHttpData(target);
                if (!target.startsWith("https://") && !target.startsWith("http://")) data = readLocalData(target);
                sendInfo("数据获取成功！");
                return data;
            } catch (Exception e) {
                sendInfo("第"+(i+1)+"次数据获取失败了......原因是："+e.getMessage());
            }
        }
        return null;
    }
    public static String readLocalData(String target) throws Exception {
        File file = new File(target);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath())));
        String inputLine;
        StringBuilder readInfo = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            readInfo.append(inputLine);
        }
        reader.close();
        return readInfo.toString();
    }
    public static String readHttpsData(String target) throws Exception {
        URL targetURL = new URL(target);
        HttpsURLConnection connection = (HttpsURLConnection) targetURL.openConnection();
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent", "MossTwitterRandom/"+BasicInfo.version);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder readInfo = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            readInfo.append(inputLine);
        }
        reader.close();
        return readInfo.toString();
    }
    public static String readHttpData(String target) throws Exception {
        URL targetURL = new URL(target);
        HttpURLConnection connection = (HttpURLConnection) targetURL.openConnection();
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent", "MossTwitterRandom/"+BasicInfo.version);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder readInfo = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            readInfo.append(inputLine);
        }
        reader.close();
        return readInfo.toString();
    }
}
