package topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitMqUtil;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMqUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        //声明交换机及其类型
        /**
         * 参数一：交换机名
         * 参数二：模型类型
         */
        channel.exchangeDeclare("mytopic","topic");

        //声明路由key
        String routekey = "caixiaobai.cxq";

        //发布消息
        channel.basicPublish("mytopic",routekey,null,("这里是topics动态路由模型，roukey:["+routekey+" ]").getBytes());

        RabbitMqUtil.closeConnection(channel,connection);
    }
}
