package workquene;

import com.rabbitmq.client.*;
import util.RabbitMqUtil;

import java.io.IOException;

public class Customer2 {
    //消费者1
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMqUtil.getConnection();
        //获取通道
        final Channel channel = connection.createChannel();

        //一次只消费一个消息
        channel.basicQos(1);
        //绑定队列
        channel.queueDeclare("work",true,false,false,null);

        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                System.out.println("消费者-2"+ new String(body));

                /**
                 * 参数一：确认队列中哪个具体消息
                 * 参数二：是否开启多个消息同时确认改为false
                 */
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });

        
    }
}
