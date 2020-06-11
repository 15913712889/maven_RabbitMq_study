package topics;

import com.rabbitmq.client.*;
import util.RabbitMqUtil;

import java.io.IOException;

public class Customer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机及其类型
        channel.exchangeDeclare("mytopic","topic");
        //获取临时队列
        String queue = channel.queueDeclare().getQueue();
        //绑定对队列和交换机  动态通配符形式
        //#号表示不管后面有几个，只要有前面匹配的都可以进行消费
        channel.queueBind(queue,"mytopic","caixiaobai.#");

        //消费消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者-1："+new String(body));
            }
        });


    }
}
