package fanout;

import com.rabbitmq.client.*;
import util.RabbitMqUtil;

import java.io.IOException;

public class Customer3 {
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();

        //通道绑定交换机,也可以声明一个交换机
        //channel.exchangeBind()
        channel.exchangeDeclare("myExchange","fanout");

        //创建临时队列,返回一个队列名字
        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机和队列
        /**
         * 参数一：临时队列的名字
         * 参数二：交换机的名字
         * 参数三： 路由  没什么意义可以不写
         */
        channel.queueBind(queueName,"myExchange","");

        //消费消息
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者-3："+new String(body));
            }
        });
    }
}
