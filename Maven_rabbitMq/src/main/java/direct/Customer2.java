package direct;

import com.rabbitmq.client.*;
import util.RabbitMqUtil;

import java.io.IOException;

//消费者1
public class Customer2 {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMqUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //通道声明交换机及其类型
        channel.exchangeDeclare("myexchange","direct");

        //创建临时队列
        String queue = channel.queueDeclare().getQueue();

        //基于路由key去绑定交换机 和队列
        /**
         * 参数一:临时队列名字
         * 参数二：自己的交换机名字
         * 参数三：路由key
         */
        //绑定多个路由Key那么这个消费者就会消费同一个交换机的不同的路由key的消息
        channel.queueBind(queue,"myexchange","error");
        channel.queueBind(queue,"myexchange","cxq");
        channel.queueBind(queue,"myexchange","info");
        channel.queueBind(queue,"myexchange","warning");

        //获取消费消息
        /**
         * 参数一：临时队列名
         * 参数二：是否自动确认消息
         * 参数三：消费消息的方法
         */
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者-1："+new String(body));
            }
        });

    }
}
