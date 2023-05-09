package org.mossmc.mosscg.MossTwitterRandom;

import java.util.*;

public class ReadWebInfo {
    public static List<String> nameList;

    public static void spiltLocalData(String data) {
        nameList = new ArrayList<>();
        String[] cut = data.split("<div id=\"r\" class=\"replies\">")[1].split("<div class=\"timeline-item thread-last \">");
        for (int i = 1; i < cut.length-1; i++) {
            String name = getLocalName(cut[i]);
            nameList.add(name);
            Logger.sendInfo("解析到用户ID："+name);
        }
        Logger.sendInfo("正在去重......原评论数量为"+nameList.size());
        Set<String> cacheSet = new HashSet<>(nameList);
        nameList = new ArrayList<>(cacheSet);
        Logger.sendInfo("解析完成！用户列表如下：");
        Logger.sendInfo(nameList.toString());
        Logger.sendInfo("解析到"+nameList.size()+"位用户！");
    }

    public static String getLocalName(String part) {
        try {
            String[] cut = part.split("<div class=\"fullname-and-username\">");
            String[] cut2 = cut[1].split("</div>");
            String[] cut3 = cut2[0].split("</a>");
            //Logger.sendWarn("解析以下文本串时出现异常："+ Arrays.toString(cut3));
            String[] cut4 = cut3[0].replace("<a ","").split("\\s+");
            for (int i = 0; i < cut4.length; i++) {
                String smallCut = cut4[i];
                if (smallCut.contains("href=")) {
                    return smallCut.split("=")[1].replace("https://nitter.cz/","").replace("\"","");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.sendWarn("解析以下文本串时出现异常："+part);
        }
        return "null";
    }

    public static void spiltOnlineData(String data) {
        nameList = new ArrayList<>();
        String[] cut = data.split("<div id=\"r\" class=\"replies\">")[1].split("<div class=\"timeline-item thread-last \">");
        for (int i = 1; i < cut.length-1; i++) {
            String name = getOnlineName(cut[i]);
            nameList.add(name);
            Logger.sendInfo("解析到用户ID："+name);
        }
        Logger.sendInfo("正在去重......");
        Set<String> cacheSet = new HashSet<>(nameList);
        nameList = new ArrayList<>(cacheSet);
        Logger.sendInfo("解析完成！解析到"+nameList.size()+"位用户！");
    }

    public static String getOnlineName(String part) {
        String[] cut = part.split("<div class=\"fullname-and-username\">");
        String[] cut2 = cut[1].split("</div>");
        String[] cut3 = cut2[0].split("</a>");
        String[] cut4 = cut3[1].replace("<a ","").split("\\s+");
        for (int i = 0; i < cut4.length; i++) {
            String smallCut = cut4[i];
            if (smallCut.contains("href=")) {
                return smallCut.split("=")[1].replace("\"/","").replace("\"","");
            }
        }
        return "null";
    }
}
