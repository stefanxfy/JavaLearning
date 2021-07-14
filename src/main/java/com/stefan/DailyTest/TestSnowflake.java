package com.stefan.DailyTest;

import java.util.Calendar;

public class TestSnowflake {
    public static void main(String[] args) throws Exception {
        System.out.println(1 << 22);

        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1,2);
        long id = snowflakeIdGenerator.genID();
        System.out.println("ID=" + id + ", lastTimestamp=" + snowflakeIdGenerator.getLastTimestamp());
        System.out.println("ID二进制：" + Long.toBinaryString(id));
        System.out.println("解析ID:");
        System.out.println("Sequence=" + snowflakeIdGenerator.getSequence(id));
        System.out.println("WorkerId=" + snowflakeIdGenerator.getWorkerId(id));
        System.out.println("DataCenterId=" + snowflakeIdGenerator.getDataCenterId(id));
        System.out.println("GenerateDateTime=" + snowflakeIdGenerator.getGenerateDateTime(id));

        System.out.println("Sequence2=" + snowflakeIdGenerator.getSequence2(id));
        System.out.println("WorkerId2=" + snowflakeIdGenerator.getWorkerId2(id));
        System.out.println("DataCenterId2=" + snowflakeIdGenerator.getDataCenterId2(id));
        System.out.println("GenerateDateTime2=" + snowflakeIdGenerator.getGenerateDateTime2(id));

        System.out.println("-----------------------------------------");
        Thread.sleep(10000);
        id = snowflakeIdGenerator.genID();
        System.out.println("ID=" + id + ", lastTimestamp=" + snowflakeIdGenerator.getLastTimestamp());
        System.out.println("ID二进制：" + Long.toBinaryString(id));
        System.out.println("解析ID:");
        System.out.println("Sequence=" + snowflakeIdGenerator.getSequence(id));
        System.out.println("WorkerId=" + snowflakeIdGenerator.getWorkerId(id));
        System.out.println("DataCenterId=" + snowflakeIdGenerator.getDataCenterId(id));
        System.out.println("GenerateDateTime=" + snowflakeIdGenerator.getGenerateDateTime(id));

        System.out.println("Sequence2=" + snowflakeIdGenerator.getSequence2(id));
        System.out.println("WorkerId2=" + snowflakeIdGenerator.getWorkerId2(id));
        System.out.println("DataCenterId2=" + snowflakeIdGenerator.getDataCenterId2(id));
        System.out.println("GenerateDateTime2=" + snowflakeIdGenerator.getGenerateDateTime2(id));

        System.out.println(1 << 22);

    }
}
