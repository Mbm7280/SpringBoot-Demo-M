package ex.work.hard;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import ex.util.ConnUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息的生产者
 */
public class P {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection conn = ConnUtil.getConn();
        // 创建通道
        Channel channel = conn.createChannel();
        // 创建/ 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 定义消息的内容
        for(int i = 0; i < 50;i++){
            String message = "" + i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            // 输出
            System.out.println(" [x] Sent '" + message + "'");

            // 线程 睡觉
            Thread.sleep(i * 10);
        }

        // 通道关闭
        channel.close();
        // 连接关闭
        conn.close();

    }

}
