package com.stefan.DailyTest;

import fai.comm.util.FaiList;
import fai.comm.util.Param;
import fai.comm.util.Str;

import java.util.Set;

/**
 * @author stefan
 * @date 2021/7/16 8:59
 */
public class TestDeffJson {
    public static void main(String[] args) {
        String pre = "{\n" +
                "\t\"name\":\"AlarmModuleSvr\",\n" +
                "\t\"ip\":\"172.17.0.144\",\n" +
                "\t\"port\":10590,\n" +
                "\t\"readonly\":false,\n" +
                "\t\"log\":{\n" +
                "\t\t\"path\":\"/home/faier/logs/fai\",\n" +
                "\t\t\"level\":3\n" +
                "\t},\n" +
                "\t\"daopool\":{\n" +
                "\t\t\"name\":\"alarm\",\n" +
                "\t\t\"maxSize\":50,\n" +
                "\t\t\"ip\":\"172.17.0.143\",\n" +
                "\t\t\"port\":9666,\n" +
                "\t\t\"db\":\"alarm\",\n" +
                "\t\t\"user\":\"faidb\",\n" +
                "\t\t\"pwd\":\"DB@0nline\",\n" +
                "\t\t\"serverName\":\"AlarmModuleSvr\",\n" +
                "\t\t\"dbInstanceName\":\"alarm\",\n" +
                "\t\t\"serverType\":1,\n" +
                "\t\t\"openStatisticser\":true,\n" +
                "\t\t\"slowTime\":1000,\n" +
                "\t\t\"daoTimeOut\":3600\n" +
                "\t},\n" +
                "\t\"daopoolList\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"name\":\"alarm\",\n" +
                "\t\t\t\"maxSize\":30,\n" +
                "\t\t\t\"ip\":\"172.17.0.143\",\n" +
                "\t\t\t\"port\":9666,\n" +
                "\t\t\t\"db\":\"alarm\",\n" +
                "\t\t\t\"user\":\"faidb\",\n" +
                "\t\t\t\"pwd\":\"DB@0nline\",\n" +
                "\t\t\t\"serverName\":\"AlarmModuleSvr\",\n" +
                "\t\t\t\"dbInstanceName\":\"alarm\",\n" +
                "\t\t\t\"serverType\":1,\n" +
                "\t\t\t\"openStatisticser\":true,\n" +
                "\t\t\t\"slowTime\":1000,\n" +
                "\t\t\t\"daoTimeOut\":3600\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"redis\":{\n" +
                "\t\t\"name\":\"codis-alarm\",\n" +
                "\t\t\"clusterMode\":\"codis\",\n" +
                "\t\t\"proxyList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.144\",\n" +
                "\t\t\t\t\"port\":6401\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.144\",\n" +
                "\t\t\t\t\"port\":6402\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"ip\":\"172.17.0.144\",\n" +
                "\t\t\"port\":6401,\n" +
                "\t\t\"maxSize\":40,\n" +
                "\t\t\"expire\":3600,\n" +
                "\t\t\"connTimeOut\":500,\n" +
                "\t\t\"recvTimeOut\":1000,\n" +
                "\t\t\"useQconf\":false,\n" +
                "\t\t\"qConfPath\":\"redis-comm\",\n" +
                "\t\t\"openStatisticser\":true,\n" +
                "\t\t\"serverName\":\"AlarmModuleSvr\",\n" +
                "\t\t\"serverType\":1,\n" +
                "\t\t\"redisSlowTime\":300\n" +
                "\t},\n" +
                "\t\"svr\":{\n" +
                "\t\t\"lockLength\":1000,\n" +
                "\t\t\"mcDb\":\"monitor\",\n" +
                "\t\t\"mcDbType\":3,\n" +
                "\t\t\"ignoreUrl\":\"http://mc.aaa.cn/alarm/alarm-ignore?alarmId=\",\n" +
                "\t\t\"submitUrl\":\"http://mc.aaa.cn/alarm/alarm-confirm?id=\",\n" +
                "\t\t\"detailUrl\":\"http://mc.aaa.cn/open/alarm/detail\",\n" +
                "\t\t\"delayRandom\":30,\n" +
                "\t\t\"systemShieldTime\":1800,\n" +
                "\t\t\"smtpHost\":\"172.16.3.7\",\n" +
                "\t\t\"smtpPort\":10125,\n" +
                "\t\t\"recipientMaxCount\":2,\n" +
                "\t\t\"fromArr\":[\n" +
                "\t\t\t\"faier@faidns.com\",\n" +
                "\t\t\t\"faier1@faidns.com\",\n" +
                "\t\t\t\"faier2@faidns.com\",\n" +
                "\t\t\t\"faier3@faidns.com\",\n" +
                "\t\t\t\"faier4@faidns.com\",\n" +
                "\t\t\t\"faier5@faidns.com\"\n" +
                "\t\t],\n" +
                "\t\t\"defaultReciverList\":[\n" +
                "\t\t\t466\n" +
                "\t\t],\n" +
                "\t\t\"noEmailSidList\":[\n" +
                "\t\t\t466\n" +
                "\t\t],\n" +
                "\t\t\"escalation\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\":\"notifyLeader\",\n" +
                "\t\t\t\t\"min\":10,\n" +
                "\t\t\t\t\"max\":100,\n" +
                "\t\t\t\t\"time\":1800,\n" +
                "\t\t\t\t\"proportion\":30\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\":\"phone\",\n" +
                "\t\t\t\t\"min\":10,\n" +
                "\t\t\t\t\"max\":100,\n" +
                "\t\t\t\t\"time\":1800,\n" +
                "\t\t\t\t\"proportion\":40,\n" +
                "\t\t\t\t\"cntLimit\":1,\n" +
                "\t\t\t\t\"reTry\":true,\n" +
                "\t\t\t\t\"tryMaxCnt\":3,\n" +
                "\t\t\t\t\"tryPeriod\":600\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"jdk-version\":\"jdk1.8\",\n" +
                "\t\"jvm-args\":[\n" +
                "\t\t\"-Xms128m\",\n" +
                "\t\t\"-Xmx128m\",\n" +
                "\t\t\"-Xmn64m\",\n" +
                "\t\t\"-Xss512k\",\n" +
                "\t\t\"-XX:ParallelGCThreads=8\",\n" +
                "\t\t\"-XX:+UseConcMarkSweepGC\",\n" +
                "\t\t\"-XX:+UseParNewGC\"\n" +
                "\t]\n" +
                "}";

        String upd = "{\n" +
                "\t\"ip\":\"172.17.0.144\",\n" +
                "\t\"port\":10590,\n" +
                "\t\"host\":10590,\n" +
                "\t\"readonly\":false,\n" +
                "\t\"log\":{\n" +
                "\t\t\"path\":\"/home/faier/logs/fai\",\n" +
                "\t\t\"level\":3\n" +
                "\t},\n" +
                "\t\"daopool\":{\n" +
                "\t\t\"name\":\"alarm\",\n" +
                "\t\t\"maxSize\":30,\n" +
                "\t\t\"ip\":\"172.17.0.143\",\n" +
                "\t\t\"port\":9666,\n" +
                "\t\t\"db\":\"alarm\",\n" +
                "\t\t\"user\":\"faidb\",\n" +
                "\t\t\"pwd\":\"DB@0nline\",\n" +
                "\t\t\"serverName\":\"AlarmModuleSvr\",\n" +
                "\t\t\"dbInstanceName\":\"alarm\",\n" +
                "\t\t\"serverType\":1,\n" +
                "\t\t\"openStatisticser\":true,\n" +
                "\t\t\"slowTime\":1000\n" +
                "\t},\n" +
                "\t\"daopoolList\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"name\":\"alarm\",\n" +
                "\t\t\t\"maxSize\":30,\n" +
                "\t\t\t\"ip\":\"172.17.0.143\",\n" +
                "\t\t\t\"port\":9666,\n" +
                "\t\t\t\"db\":\"alarm\",\n" +
                "\t\t\t\"user\":\"faidb\",\n" +
                "\t\t\t\"pwd\":\"DB@0nline\",\n" +
                "\t\t\t\"serverName\":\"AlarmModuleSvr\",\n" +
                "\t\t\t\"dbInstanceName\":\"alarm\",\n" +
                "\t\t\t\"serverType\":1,\n" +
                "\t\t\t\"openStatisticser\":true,\n" +
                "\t\t\t\"slowTime\":1000,\n" +
                "\t\t\t\"daoTimeOut\":3600\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"redis\":{\n" +
                "\t\t\"name\":\"codis-alarm\",\n" +
                "\t\t\"clusterMode\":\"codis\",\n" +
                "\t\t\"clusterMode1\":\"codis1\",\n" +
                "\t\t\"proxyList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.144\",\n" +
                "\t\t\t\t\"port\":6401\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.144\",\n" +
                "\t\t\t\t\"port\":6402\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"ip\":\"172.17.0.144\",\n" +
                "\t\t\"port\":6401,\n" +
                "\t\t\"maxSize\":40,\n" +
                "\t\t\"expire\":3600,\n" +
                "\t\t\"connTimeOut\":500,\n" +
                "\t\t\"recvTimeOut\":1000,\n" +
                "\t\t\"useQconf\":false,\n" +
                "\t\t\"qConfPath\":\"redis-comm\",\n" +
                "\t\t\"openStatisticser\":true,\n" +
                "\t\t\"serverName\":\"AlarmModuleSvr\",\n" +
                "\t\t\"serverType\":1,\n" +
                "\t\t\"redisSlowTime\":300\n" +
                "\t},\n" +
                "\t\"svr\":{\n" +
                "\t\t\"lockLength\":1000,\n" +
                "\t\t\"mcDb\":\"monitor\",\n" +
                "\t\t\"mcDbType\":3,\n" +
                "\t\t\"ignoreUrl\":\"http://mc.aaa.cn/alarm/alarm-ignore?alarmId=\",\n" +
                "\t\t\"submitUrl\":\"http://mc.aaa.cn/alarm/alarm-confirm?id=\",\n" +
                "\t\t\"detailUrl\":\"http://mc.aaa.cn/open/alarm/detail\",\n" +
                "\t\t\"delayRandom\":30,\n" +
                "\t\t\"systemShieldTime\":1800,\n" +
                "\t\t\"smtpHost\":\"172.16.3.7\",\n" +
                "\t\t\"smtpPort\":10125,\n" +
                "\t\t\"recipientMaxCount\":2,\n" +
                "\t\t\"fromArr\":[\n" +
                "\t\t\t\"faier@faidns.com\",\n" +
                "\t\t\t\"faier1@faidns.com\",\n" +
                "\t\t\t\"faier2@faidns.com\",\n" +
                "\t\t\t\"faier3@faidns.com\",\n" +
                "\t\t\t\"faier4@faidns.com\",\n" +
                "\t\t\t\"faier5@faidns.com\"\n" +
                "\t\t],\n" +
                "\t\t\"defaultReciverList\":[\n" +
                "\t\t\t466\n" +
                "\t\t],\n" +
                "\t\t\"noEmailSidList\":[\n" +
                "\t\t\t467\n" +
                "\t\t],\n" +
                "\t\t\"escalation\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\":\"notifyLeader\",\n" +
                "\t\t\t\t\"min\":10,\n" +
                "\t\t\t\t\"max\":100,\n" +
                "\t\t\t\t\"time\":1800,\n" +
                "\t\t\t\t\"proportion\":30\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"name\":\"phone\",\n" +
                "\t\t\t\t\"min\":10,\n" +
                "\t\t\t\t\"max\":100,\n" +
                "\t\t\t\t\"time\":1800,\n" +
                "\t\t\t\t\"proportion\":40,\n" +
                "\t\t\t\t\"cntLimit\":1,\n" +
                "\t\t\t\t\"reTry\":true,\n" +
                "\t\t\t\t\"tryMaxCnt\":3,\n" +
                "\t\t\t\t\"tryPeriod\":600\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"jdk-version\":\"jdk1.8\",\n" +
                "\t\"jvm-args\":[\n" +
                "\t\t\"-Xms128m\",\n" +
                "\t\t\"-Xmx128m\",\n" +
                "\t\t\"-Xmw128m\",\n" +
                "\t\t\"-Xmn64m\",\n" +
                "\t\t\"-Xss512k\",\n" +
                "\t\t\"-XX:ParallelGCThreads=8\",\n" +
                "\t\t\"-XX:+UseConcMarkSweepGC\",\n" +
                "\t\t\"-XX:+UseParNewGC\"\n" +
                "\t]\n" +
                "}";
        FaiList<Param> updlist = new FaiList<Param>();
        FaiList<Param> dellist = new FaiList<Param>();
        FaiList<Param> addlist = new FaiList<Param>();
        diffContent(updlist, dellist, addlist, pre, upd);
        System.out.println("del=" + dellist);
        System.out.println("add=" + addlist);
        System.out.println("updlist=" + updlist);
        Param info = Param.parseParam(pre);
        margeDiffContent(updlist, "upd", info);
        margeDiffContent(dellist, "del", info);
        margeDiffContent(addlist, "add", info);

        System.out.println(info);
    }

    public static void margeDiffContent(FaiList<Param> updlist, String option, Param info) {
        //del=[{"daopool":{"daoTimeOut":3600}},{"name":"AlarmModuleSvr"}]
        //add=[{"host":10590},{"redis":{"clusterMode1":"codis1"}}]
        //updlist=[{"daopool":{"maxSize":30}},{"jvm-args":["-Xms128m","-Xmx128m","-Xmw128m","-Xmn64m","-Xss512k","-XX:ParallelGCThreads=8","-XX:+UseConcMarkSweepGC","-XX:+UseParNewGC"]},{"svr":{"noEmailSidList":[467]}}]
        for (Param param : updlist) {
            margeDiffContent(option, param, info);
        }
    }

    public static void margeDiffContent(String option, Param diffParam, Param info) {
        Set<String> keys =  diffParam.entrySet();
        for (String key : keys) {
            Object val = diffParam.getObject(key);
            if (val instanceof Param) {
                Param valInfo = (Param) val;
                margeDiffContent(option, valInfo, info.getParam(key));
                continue;
            }

            if ( "upd".equals(option) || "add".equals(option)) {
                info.setObject(key, val);
            }
            if ("del".equals(option)) {
                info.remove(key);
            }
        }
    }

    public static void diffContent(FaiList<Param> updlist, FaiList<Param> dellist, FaiList<Param> addlist, String preContent, String updContent) {
        // {"a": {"b" : {"c" : 123}, "d" : "2" }} 以这个为标准
        // {"a": {"b" : {"c" : 321}, "d" : "3" }}
        // 找出来的的效果  [{"a" : {"b" : {"c" : 321 }}}, {"a" : {"d" : 3 }}]

        // 如果有多处修改，用数组保存[{"a" : {"b" : {"c" : 123 }}}, {"a" : {"b" : {"c" : 12 }}}]
        Param preContentInfo = Param.parseParam(preContent);
        Param updContentInfo = Param.parseParam(updContent);
        if (Str.isEmpty(preContentInfo) || Str.isEmpty(updContentInfo)) {
            return;
        }
        diffContent(updlist, dellist, null, preContentInfo, updContentInfo);
        diffContent(null, addlist, null, updContentInfo, preContentInfo);
    }

    public static void diffContent(FaiList<Param> updlist, FaiList<Param> dellist, String parentKey, Param preContentInfo, Param updContentInfo) {
        if (Str.isEmpty(preContentInfo) || Str.isEmpty(updContentInfo)) {
            return;
        }

        for (String key : preContentInfo.entrySet()) {
            Object preVal = preContentInfo.getObject(key);
            Object updVal = updContentInfo.getObject(key);
            if (preVal instanceof Param) {
                if (!(updVal instanceof Param)) {
                    // 修改部分
                    Param info = new Param();
                    info.setObject(key, updVal);
                    addList(parentKey, info, updlist);
                    continue;
                }
                // 递归比较
                Param preValParam = (Param)preVal;
                Param updValParam = (Param)updVal;
                diffContent(updlist, dellist, key, preValParam, updValParam);
                continue;
            }
            if (preVal.equals(updVal)) {
                continue;
            }
            if (updVal == null) {
                // 可能被删除了
                Param info = new Param();
                info.setObject(key, preVal);
                addList(parentKey, info, dellist);
                continue;
            }
            // 修改部分
            Param info = new Param();
            info.setObject(key, updVal); 
            addList(parentKey, info, updlist);
        }
    }

    private static void addList(String parentKey, Param info, FaiList<Param> list) {
        if (parentKey != null) {
            Param parentParam = new Param();
            parentParam.setParam(parentKey, info);
            info = parentParam;
        }
        if (list != null) {
            list.add(info);
        }
    }

}
