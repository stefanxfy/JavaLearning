package jvmLearning.managementLearning;


import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Chat extends NotificationBroadcasterSupport implements ChatMBean {
    private String ObjectName = "chat:name=stefan";
    private int seq = 0;
    @Override
    public void hi() {
        Notification notify =
                //通知名称；谁发起的通知；序列号；发起通知时间；发送的消息
                new Notification("stefan.hi",this,++seq,System.currentTimeMillis(),"stefan 你好");
        sendNotification(notify);
    }

    public String getObjectName() {
        return ObjectName;
    }

}
