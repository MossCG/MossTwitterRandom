package org.mossmc.mosscg.MossTwitterRandom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Logger.sendInfo("正在去重......");
        Set<String> cacheSet = new HashSet<>(nameList);
        nameList = new ArrayList<>(cacheSet);
        Logger.sendInfo("解析完成！解析到"+nameList.size()+"位用户！");
    }

    public static String getLocalName(String part) {
        String[] cut = part.split("<div class=\"fullname-and-username\">");
        String[] cut2 = cut[1].split("</div>");
        String[] cut3 = cut2[0].split("</a>");
        String[] cut4 = cut3[1].replace("<a ","").split("\\s+");
        for (int i = 0; i < cut4.length; i++) {
            String smallCut = cut4[i];
            if (smallCut.contains("href=")) {
                return smallCut.split("=")[1].replace("https://nitter.cz/","").replace("\"","");
            }
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
