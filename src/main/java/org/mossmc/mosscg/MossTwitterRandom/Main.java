package org.mossmc.mosscg.MossTwitterRandom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Random;

import static org.mossmc.mosscg.MossTwitterRandom.Logger.*;
import static org.mossmc.mosscg.MossTwitterRandom.ReadWebInfo.*;

public class Main {
    //动态参考地址：https://nitter.cz/mosscghere/status/1643917454782140416#m
    public static void main(String[] args) {
        init();
        sendInfo("欢迎使用MossTwitterRandom抽奖插件~");
        sendInfo("软件版本：" + BasicInfo.version);
        sendInfo("软件作者：" + BasicInfo.author);
        run();
    }

    public static void run() {
        sendInfo("----------------------------------");
        sendInfo("请选择WEB文件获取方式~");
        sendInfo("1.本地文件（弃用）");
        sendInfo("2.在线获取");
        sendInfo("3.纯ID（txt一行一个）");
        sendInfo("请输入选项前编号：");
        BasicInfo.fileGetType = readInput();
        String link,data;
        switch (BasicInfo.fileGetType) {
            case "1":
                sendInfo("----------------------------------");
                sendInfo("此处需要输入本地html文件路径呢~");
                sendInfo("请使用nitter动态页面保存的html哦~记得往下划拉使得页面内加载了所有的评论~");
                sendInfo("示例页面：https://nitter.cz/mosscghere/status/1643917454782140416#m");
                sendInfo("嘛，注意大小写和文件后缀名哦~");
                sendInfo("举个栗子：D:\\IDEA\\IdeaProjects\\MossTwitterRandom\\out\\artifacts\\MossTwitterRandom\\choujiang.html");
                sendInfo("请输入文件路径：");
                link = readInput();
                data = GetWebInfo.getData(link);
                if (data==null) {
                    sendWarn("数据读取失败！请检查文件状态！");
                    return;
                }
                spiltLocalData(data);
                break;
            case "2":
                sendInfo("----------------------------------");
                sendInfo("此处需要输入推特ID呢~");
                sendInfo("嘛，注意大小写哦~");
                sendInfo("举个栗子：MossCG");
                sendInfo("请输入推特ID：");
                BasicInfo.twitterID = readInput();
                sendInfo("----------------------------------");
                sendInfo("此处需要输入动态编号或文件路径呢~");
                sendInfo("如动态链接为https://twitter.com/mosscghere/status/1643917454782140416");
                sendInfo("则动态编号即为：1643917454782140416");
                sendInfo("请输入动态编号文件路径：");
                BasicInfo.statusID = readInput();
                sendInfo("----------------------------------");
                sendInfo("正在读取数据ing......");
                link = GetWebInfo.getLink(BasicInfo.twitterID,BasicInfo.statusID);
                data = GetWebInfo.getData(link);
                if (data==null) {
                    sendWarn("数据读取失败！请检查网络连接！");
                    return;
                }
                spiltOnlineData(data);
                break;
            case "3":
                sendInfo("----------------------------------");
                sendInfo("此处需要输入txt文件路径呢~");
                sendInfo("请注意是一行一个ID哦~");
                sendInfo("嘛，注意大小写和文件后缀名哦~");
                sendInfo("举个栗子：D:\\IDEA\\IdeaProjects\\MossTwitterRandom\\out\\artifacts\\MossTwitterRandom\\cj.txt");
                sendInfo("请输入文件路径：");
                link = readInput();
                spiltIDData(link);
                break;
            default:
                break;
        }
        sendInfo("----------------------------------");
        sendInfo("请选择抽出个数~");
        sendInfo("直接输入数字哦~记得不要大于解析出来的用户人数~");
        sendInfo("举个栗子：5");
        sendInfo("请输入抽出个数：");
        BasicInfo.count = Integer.parseInt(readInput());
        sendInfo("----------------------------------");
        sendInfo("正在抽取中......");
        Random random = new Random();
        for (int i = 1; i <= BasicInfo.count; i++) {
            int targetUser = random.nextInt(ReadWebInfo.nameList.size());
            sendInfo("第"+i+"位："+ReadWebInfo.nameList.get(targetUser));
            ReadWebInfo.nameList.remove(targetUser);
        }
        sendInfo("抽取完成！");
        sendInfo("----------------------------------");
        sendInfo("完工！下班！");
    }

    public static void init() {
        System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
        System.setProperty("sun.net.client.defaultReadTimeout", "3000");
        System.setProperty("http.keepAlive", "false");
    }

    public static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
    public static String readInput() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            sendException(e);
            return "null";
        }
    }
}
