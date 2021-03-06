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
public class TestDeffJson {
    public static void main(String[] args) {
        String pre = "{\n" +
                "\t\"name\":\"FaiPaySvr\",\n" +
                "\t\"clusterId\":\"pro.svr.FaiPaySvr\",\n" +
                "\t\"host\":\"172.16.1.133\",\n" +
                "\t\"port\":10294,\n" +
                "\t\"readonly\":false,\n" +
                "\t\"log\":{\n" +
                "\t\t\"path\":\"/home/faier/logs/fai\",\n" +
                "\t\t\"level\":2\n" +
                "\t},\n" +
                "\t\"daopool\":{\n" +
                "\t\t\"maxSize\":150,\n" +
                "\t\t\"ip\":\"172.16.2.226\",\n" +
                "\t\t\"port\":9541,\n" +
                "\t\t\"db\":\"faiPay\",\n" +
                "\t\t\"user\":\"faidba\",\n" +
                "\t\t\"pwd\":\"db@fai-sco\"\n" +
                "\t},\n" +
                "\t\"redis\":{\n" +
                "\t\t\"name\":\"codis-faipay\",\n" +
                "\t\t\"clusterMode\":\"codis\",\n" +
                "\t\t\"proxyList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.16.1.52\",\n" +
                "\t\t\t\t\"port\":7028\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.16.1.55\",\n" +
                "\t\t\t\t\"port\":7028\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"maxSize\":20,\n" +
                "\t\t\"expire\":5400,\n" +
                "\t\t\"expireRandom\":20,\n" +
                "\t\t\"connTimeOut\":300,\n" +
                "\t\t\"recvTimeOut\":3000,\n" +
                "\t\t\"useQconf\":false,\n" +
                "\t\t\"qConfPath\":\"codis-faipay\",\n" +
                "\t\t\"connLeakSecond\":180\n" +
                "\t},\n" +
                "\t\"svr\":{\n" +
                "\t\t\"loopGroupSize\":12,\n" +
                "\t\t\"lockLength\":100,\n" +
                "\t\t\"lockLease\":2000,\n" +
                "\t\t\"providerConf\":{\n" +
                "\t\t\t\"joinPay\":{\n" +
                "\t\t\t\t\"accounts\":[\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"mchNo\":\"888105800005574\",\n" +
                "\t\t\t\t\t\t\"privateKey\":\"385e031d996d4c76ba6e3c93abbdda30\",\n" +
                "\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"mchNo\":\"888106200004763\",\n" +
                "\t\t\t\t\t\t\"privateKey\":\"82f654dde9de45e7acdb58b8618d0175\",\n" +
                "\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"mchNo\":\"888106200004792\",\n" +
                "\t\t\t\t\t\t\"privateKey\":\"ac9610b891354dc7b52ff452233ecedd\",\n" +
                "\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"notifyURL\":{\n" +
                "\t\t\t\t\t\"publicPay\":\"https://joinpay.arch.api.fkw.com/public/payNotify\",\n" +
                "\t\t\t\t\t\"publicAltPay\":\"https://joinpay.arch.api.fkw.com/public/altPayNotify\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"switchComm\":true,\n" +
                "\t\t\t\t\"noReportAids\":[],\n" +
                "\t\t\t\t\"aids\":[],\n" +
                "\t\t\t\t\"tradeMerchantIds\":{\n" +
                "\t\t\t\t\t\"1\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777111500323552\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777198800323704\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777196400323705\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777109400323706\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777124900323870\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777186000324003\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777151800324101\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777176900324102\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777157600324394\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777180000324393\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777134700324458\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777104700324496\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777157100324497\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777109400324546\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777153300324547\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777104300324606\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777163800324607\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777162900324690\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174500324691\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777119600324783\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777179100324784\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"2\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777179000323661\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777179600323871\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777157000324004\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777161000324145\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777191700324144\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777165400324261\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777108200324262\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777132800324459\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777160800324907\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777109800324908\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777149800324945\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777188300324946\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"3\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777191800322853\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174900322854\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174900322855\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777186000322976\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777195600323095\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777151000323174\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777196300325074\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777102600325075\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777179400325401\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777130400325402\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777153500325661\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777196100325662\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777175700325688\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777170100325689\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"7\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777191800322853\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174900322854\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"0\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777149100323066\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777144900322803\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777129300322974\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777151600322852\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t]\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"gzhAppIds\":[\n" +
                "\t\t\t\t\t\"wx3e5abb513e529cdf\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"jdk-version\":\"jdk1.8\",\n" +
                "\t\"jvm-args\":[\n" +
                "\t\t\"-Xms2048m\",\n" +
                "\t\t\"-Xmx2048m\",\n" +
                "\t\t\"-Xmn512m\",\n" +
                "\t\t\"-Xss512k\",\n" +
                "\t\t\"-XX:+UseG1GC\",\n" +
                "\t\t\"-XX:SurvivorRatio=8 \",\n" +
                "\t\t\"-XX:MaxTenuringThreshold=14\",\n" +
                "\t\t\"-XX:ParallelGCThreads=8\",\n" +
                "\t\t\"-XX:ConcGCThreads=8\",\n" +
                "\t\t\"-XX:+DisableExplicitGC\",\n" +
                "\t\t\"-javaagent:/home/faier/script/comm/javaAgent/fai-agent-bootstrap.jar\",\n" +
                "\t\t\"-Dagent.bootstrap.agents=JvmAgent\"\n" +
                "\t]\n" +
                "}";

        String upd = "{\n" +
                "\t\"name\":\"FaiPaySvr\",\n" +
                "\t\"clusterId\":\"pro.svr.FaiPaySvr\",\n" +
                "\t\"host\":\"172.16.1.133\",\n" +
                "\t\"port\":10294,\n" +
                "\t\"readonly\":false,\n" +
                "\t\"log\":{\n" +
                "\t\t\"path\":\"/home/faier/logs/fai\",\n" +
                "\t\t\"level\":2\n" +
                "\t},\n" +
                "\t\"daopool\":{\n" +
                "\t\t\"maxSize\":150,\n" +
                "\t\t\"ip\":\"172.16.2.226\",\n" +
                "\t\t\"port\":9541,\n" +
                "\t\t\"db\":\"faiPay\",\n" +
                "\t\t\"user\":\"faidba\",\n" +
                "\t\t\"pwd\":\"db@fai-sco\"\n" +
                "\t},\n" +
                "\t\"redis\":{\n" +
                "\t\t\"name\":\"codis-faipay\",\n" +
                "\t\t\"clusterMode\":\"codis\",\n" +
                "\t\t\"proxyList\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.16.1.52\",\n" +
                "\t\t\t\t\"port\":7028\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"ip\":\"172.16.1.55\",\n" +
                "\t\t\t\t\"port\":7028\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"maxSize\":20,\n" +
                "\t\t\"expire\":5400,\n" +
                "\t\t\"expireRandom\":20,\n" +
                "\t\t\"connTimeOut\":300,\n" +
                "\t\t\"recvTimeOut\":3000,\n" +
                "\t\t\"useQconf\":false,\n" +
                "\t\t\"qConfPath\":\"codis-faipay\",\n" +
                "\t\t\"connLeakSecond\":180\n" +
                "\t},\n" +
                "\t\"svr\":{\n" +
                "\t\t\"loopGroupSize\":12,\n" +
                "\t\t\"lockLength\":100,\n" +
                "\t\t\"lockLease\":2000,\n" +
                "\t\t\"providerConf\":{\n" +
                "\t\t\t\"joinPay\":{\n" +
                "\t\t\t\t\"accounts\":[\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"mchNo\":\"888105800005574\",\n" +
                "\t\t\t\t\t\t\"privateKey\":\"385e031d996d4c76ba6e3c93abbdda30\",\n" +
                "\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"mchNo\":\"888106200004763\",\n" +
                "\t\t\t\t\t\t\"privateKey\":\"82f654dde9de45e7acdb58b8618d0175\",\n" +
                "\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"mchNo\":\"888106200004792\",\n" +
                "\t\t\t\t\t\t\"privateKey\":\"ac9610b891354dc7b52ff452233ecedd\",\n" +
                "\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"notifyURL\":{\n" +
                "\t\t\t\t\t\"publicPay\":\"https://joinpay.arch.api.fkw.com/public/payNotify\",\n" +
                "\t\t\t\t\t\"publicAltPay\":\"https://joinpay.arch.api.fkw.com/public/altPayNotify\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"switchComm\":true,\n" +
                "\t\t\t\t\"noReportAids\":[],\n" +
                "\t\t\t\t\"aids\":[],\n" +
                "\t\t\t\t\"tradeMerchantIds\":{\n" +
                "\t\t\t\t\t\"1\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777111500323552\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777198800323704\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777196400323705\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777109400323706\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777124900323870\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777186000324003\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777151800324101\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777176900324102\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777157600324394\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777180000324393\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777134700324458\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777104700324496\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777157100324497\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777109400324546\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777153300324547\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777104300324606\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777163800324607\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777162900324690\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174500324691\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777119600324783\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777179100324784\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"2\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777179600323871\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777157000324004\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777161000324145\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777191700324144\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777165400324261\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777108200324262\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777132800324459\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777160800324907\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777109800324908\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777149800324945\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777188300324946\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"3\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777191800322853\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174900322854\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174900322855\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777186000322976\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777195600323095\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777151000323174\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777196300325074\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777102600325075\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777179400325401\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777130400325402\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777153500325661\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777196100325662\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777175700325688\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777170100325689\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"7\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777191800322853\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777174900322854\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"0\":[\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777149100323066\",\n" +
                "\t\t\t\t\t\t\t\"weight\":1\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777144900322803\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777129300322974\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"tradeMerchantId\":\"777151600322852\",\n" +
                "\t\t\t\t\t\t\t\"weight\":0\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t]\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"gzhAppIds\":[\n" +
                "\t\t\t\t\t\"wx3e5abb513e529cdf\"\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"jdk-version\":\"jdk1.8\",\n" +
                "\t\"jvm-args\":[\n" +
                "\t\t\"-Xms2048m\",\n" +
                "\t\t\"-Xmx2048m\",\n" +
                "\t\t\"-Xmn512m\",\n" +
                "\t\t\"-Xss512k\",\n" +
                "\t\t\"-XX:+UseG1GC\",\n" +
                "\t\t\"-XX:SurvivorRatio=8 \",\n" +
                "\t\t\"-XX:MaxTenuringThreshold=14\",\n" +
                "\t\t\"-XX:ParallelGCThreads=8\",\n" +
                "\t\t\"-XX:ConcGCThreads=8\",\n" +
                "\t\t\"-XX:+DisableExplicitGC\",\n" +
                "\t\t\"-javaagent:/home/faier/script/comm/javaAgent/fai-agent-bootstrap.jar\",\n" +
                "\t\t\"-Dagent.bootstrap.agents=JvmAgent\"\n" +
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
        // {"a": {"b" : {"c" : 123}, "d" : "2" }} ??????????????????
        // {"a": {"b" : {"c" : 321}, "d" : "3" }}
        // ?????????????????????  [{"a" : {"b" : {"c" : 321 }}}, {"a" : {"d" : 3 }}]

        // ???????????????????????????????????????[{"a" : {"b" : {"c" : 123 }}}, {"a" : {"b" : {"c" : 12 }}}]
        Param preContentInfo = Param.parseParam(preContent);
        Param updContentInfo = Param.parseParam(updContent);
        if (Str.isEmpty(preContentInfo) || Str.isEmpty(updContentInfo)) {
            return;
        }
        List<String > parentKeyList = new ArrayList<String>();
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
                    // ????????????
                    Param info = new Param();
                    info.setObject(key, updVal);
                    addList(parentKey, info, updlist);
                    continue;
                }
                // {"svr":{"providerConf": {"joinPay": {"tradeMerchantIds" : {"2": [] }} } },
                //  "daopool" : {}
                // }
                // ????????????
                // {"tradeMerchantIds" : {"2": [] } }
                // {"joinPay" : {"tradeMerchantIds": {}}}
                // {"providerConf" : {"joinPay": {}}}
                // {"svr" : {"providerConf": {}}}
                Param preValParam = (Param)preVal;
                Param updValParam = (Param)updVal;
                diffContent(updlist, dellist, parentKey, preValParam, updValParam);
                continue;
            }
            if (preVal.equals(updVal)) {
                continue;
            }
            if (updVal == null) {
                // ??????????????????
                Param info = new Param();
                info.setObject(key, preVal);
                addList(parentKey, info, dellist);
                continue;
            }
            // ????????????
            Param info = new Param();
            info.setObject(key, updVal);
            addList(parentKey, info, updlist);
        }
    }

    private static void addList(String parentKey, Param info, FaiList<Param> list) {
        if (list != null) {
            list.add(info);
        }
    }

}
