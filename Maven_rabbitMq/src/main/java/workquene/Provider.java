package workquene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import util.RabbitMqUtil;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        //生产者
        //获取连接对象
        Connection connection = RabbitMqUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        //通过通道声明一个队列
        channel.queueDeclare("work",true,false,false,null);

        for (int i = 0 ; i<20 ; i++) {
            //发布消息
            channel.basicPublish("", "work", MessageProperties.PERSISTENT_TEXT_PLAIN, (i+"hello work quene").getBytes());
        }
        //关闭连接
        RabbitMqUtil.closeConnection(channel,connection);
    }

}
