package com.stefan.DailyTest;

import fai.comm.util.FaiList;
import fai.comm.util.Param;
import fai.comm.util.Str;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author stefan
 * @date 2021/7/16 8:59
 */
public class TestDeffJson2 {
    public static void main(String[] args) {
        String pre = "{\n" +
                "\t\"name\":\"AlarmModuleSvr\",\n" +
                "\t\"ip\":\"172.17.0.140\",\n" +
                "\t\"port\":10590,\n" +
                "\t\"readonly\":false,\n" +
                "\t\"log\":{\n" +
                "\t\t\"path\":\"/home/faier/logs/fai\",\n" +
                "\t\t\"level\":2\n" +
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
                "\t\"cacheList\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"name\":\"alarmModule\",\n" +
                "\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\"port\":6388,\n" +
                "\t\t\t\"maxActive\":40,\n" +
                "\t\t\t\"minIdle\":20,\n" +
                "\t\t\t\"maxIdle\":40,\n" +
                "\t\t\t\"expire\":3600\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\":\"test\",\n" +
                "\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\"port\":6333,\n" +
                "\t\t\t\"maxActive\":40,\n" +
                "\t\t\t\"minIdle\":20,\n" +
                "\t\t\t\"maxIdle\":40,\n" +
                "\t\t\t\"expire\":3600\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"redis\":{\n" +
                "\t\t\"name\":\"codis-alarm\",\n" +
                "\t\t\"clusterMode\":\"codis\",\n" +
                "\t\t\"proxyList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\t\"port\":6401\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\t\"port\":6402\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"ip\":\"172.17.0.140\",\n" +
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
                "\t\t\t\"faier4@faidns.com\"\n" +
                "\t\t],\n" +
                "\t\t\"defaultReciverList\":[\n" +
                "\t\t\t466\n" +
                "\t\t],\n" +
                "\t\t\"noEmailSidList\":[\n" +
                "\t\t\t466,\n" +
                "\t\t\t446\n" +
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
                "\t\t\t\t\"min\":100,\n" +
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
                "\t\t\"-Xmn64m\",\n" +
                "\t\t\"-Xss512k\",\n" +
                "\t\t\"-XX:ParallelGCThreads=8\",\n" +
                "\t\t\"-XX:+UseConcMarkSweepGC\",\n" +
                "\t\t\"-XX:+UseParNewGC\"\n" +
                "\t]\n" +
                "}";

        String upd = "{\n" +
                "\t\"name\":\"AlarmModuleSvr\",\n" +
                "\t\"ip\":\"172.17.0.140\",\n" +
                "\t\"port\":10590,\n" +
                "\t\"readonly\":false,\n" +
                "\t\"log\":{\n" +
                "\t\t\"path\":\"/home/faier/logs/fai\",\n" +
                "\t\t\"level\":2\n" +
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
                "\t\"cacheList\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"name\":\"alarmModule\",\n" +
                "\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\"port\":6388,\n" +
                "\t\t\t\"maxActive\":40,\n" +
                "\t\t\t\"minIdle\":20,\n" +
                "\t\t\t\"maxIdle\":40,\n" +
                "\t\t\t\"expire\":3600\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\":\"test\",\n" +
                "\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\"port\":6333,\n" +
                "\t\t\t\"maxActive\":40,\n" +
                "\t\t\t\"minIdle\":20,\n" +
                "\t\t\t\"maxIdle\":40,\n" +
                "\t\t\t\"expire\":3600\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"redis\":{\n" +
                "\t\t\"name\":\"codis-alarm\",\n" +
                "\t\t\"clusterMode\":\"codis\",\n" +
                "\t\t\"proxyList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\t\"port\":6401\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\t\"port\":6402\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"ip\":\"172.17.0.140\",\n" +
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
                "\t\"redis1\":{\n" +
                "\t\t\"name\":\"codis-alarm\",\n" +
                "\t\t\"clusterMode\":\"codis\",\n" +
                "\t\t\"proxyList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\t\"port\":6401\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.17.0.140\",\n" +
                "\t\t\t\t\"port\":6402\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"ip\":\"172.17.0.140\",\n" +
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
                "\t\t\t\"faier4@faidns.com\"\n" +
                "\t\t],\n" +
                "\t\t\"defaultReciverList\":[\n" +
                "\t\t\t466\n" +
                "\t\t],\n" +
                "\t\t\"noEmailSidList\":[\n" +
                "\t\t\t466,\n" +
                "\t\t\t446\n" +
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
                "\t\t\t\t\"min\":100,\n" +
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
        Param rt = new Param();
        rt.setList("upd", updlist);
        rt.setList("del", dellist);
        rt.setList("add", addlist);
        System.out.println(rt);

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
        System.out.println(diffParam);
        System.out.println(keys);
        for (String key : keys) {
            Object val = diffParam.getObject(key);
            if (val instanceof Param) {
                Param valInfo = (Param) val;
                margeDiffContent(option, valInfo, info.getParam(key));
                continue;
            }

            if ( "upd".equals(option) || "add".equals(option)) {
                System.out.println(option + " ::: " + key + " ::: " + val);
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
        int stemp = 0;
        diffContent(updlist, dellist, null, null, preContentInfo, updContentInfo, stemp);
        diffContent(null, addlist, null, null, updContentInfo, preContentInfo, stemp);
    }

    public static void diffContent(FaiList<Param> updlist, FaiList<Param> dellist, String parentKey, Param parentKeyList, Param preContentInfo, Param updContentInfo, int stemp) {
        if (Str.isEmpty(preContentInfo) || Str.isEmpty(updContentInfo)) {
            return;
        }
        for (String key : preContentInfo.entrySet()) {
            Object preVal = preContentInfo.getObject(key);
            Object updVal = updContentInfo.getObject(key);
            if (updVal == null) {
                // 可能被删除了
                Param info = new Param();
                info.setObject(key, preVal);
                addList(parentKey, parentKeyList, info, dellist);
                continue;
            }
            if (preVal.equals(updVal)) {
                continue;
            }
            // 修改部分
            if (!(preVal instanceof Param)) {
                // 普通修改部分
                Param info = new Param();
                info.setObject(key, updVal);
                addList(parentKey, parentKeyList, info, updlist);
                continue;
            }
            // param 修改 递归 diff
            // 给key加个随机后缀主要是为了使key唯一
            key = key + "&" + (stemp++);
            if (parentKey != null) {
                if (parentKeyList == null) {
                    parentKeyList = new Param();
                }
                FaiList<String> subKeyList = parentKeyList.getList(parentKey, new FaiList<String>());
                if (subKeyList.isEmpty() || !subKeyList.contains(key)) {
                    subKeyList.add(key);
                }
                parentKeyList.setList(parentKey, subKeyList);
            }
            diffContent(updlist, dellist, key, parentKeyList, (Param)preVal, (Param)updVal, stemp);
        }
    }

    private static void addList(String parentKey, Param parentKeyList, Param info, FaiList<Param> list) {
        if (parentKey != null) {
            Param parentParam = new Param();
            parentParam.setParam(getRealParentKey(parentKey), info);
            info = parentParam;
            if (parentKeyList != null) {
                for (int i = 0; i < parentKeyList.size(); i++) {
                    in : for (String key : parentKeyList.entrySet()) {
                        FaiList<String> subKeyList = parentKeyList.getList(key);
                        if (subKeyList.contains(parentKey)) {
                            parentKey = key;
                            Param parent = new Param();
                            parent.setParam(getRealParentKey(parentKey), info);
                            info = parent;
                            break in;
                        }
                    }
                }
            }
        }
        if (list != null) {
            list.add(info);
        }
    }

    private static String  getRealParentKey(String parentKey) {
        int index = parentKey.lastIndexOf("&");
        if (index > 0) {
            parentKey = parentKey.substring(0, index);
        }
        return parentKey;
    }

}
