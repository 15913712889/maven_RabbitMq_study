package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.RabbitMqUtil;

import java.io.IOException;

//生产者
public class Provider {
    public static void main(String[] args) throws IOException {
      //获取rabbitmq连接对象
        Connection connection = RabbitMqUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //只有拿到通道才能声明交换机
        /**
         * 参数一：自定义的交换机名字，任意
         * 参数二：direct 路由模式
         */
        channel.exchangeDeclare("myexchange","direct");
        //发送消息
        /**
         * 指定一个路由key
         */
        String cxq = "error";
        //通道通过basicPublish发布消息
        channel.basicPublish("myexchange",cxq,null,("这是direct模型发布的路由key["+cxq+"]").getBytes());
        RabbitMqUtil.closeConnection(channel,connection);
    }
}
