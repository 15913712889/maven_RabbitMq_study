package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitMqUtil;

import java.io.IOException;

public class Provider {
    //生产者
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMqUtil.getConnection();
        //通道
        Channel channel = connection.createChannel();

        //将通道声明指定交换机
        /**
         * 参数一：交换机的名称
         * 参数二：交换机类型 当交换机的类型为fanout 代表为广播类型
         */
        channel.exchangeDeclare("myExchange","fanout");

        //发送消息
        channel.basicPublish("myExchange","",null,"fanout type message".getBytes());

        RabbitMqUtil.closeConnection(channel,connection);
    }
}
