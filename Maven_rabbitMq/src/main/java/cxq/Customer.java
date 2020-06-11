package cxq;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {
    //消费者
    @Test
    public void test() throws IOException, TimeoutException {
        //创建mq的连接工厂
        ConnectionFactory cf = new ConnectionFactory();
        //设置连接主机
        cf.setHost("192.168.135.3");
        //设置端口号  5672
        cf.setPort(5672);
        //设置哪个虚拟主机,自己创建的
        cf.setVirtualHost("/cxq");
        //设置访问虚拟主机的用户名和密码
        cf.setUsername("root");
        cf.setPassword("root");


        //获取连接对象
        Connection connection = cf.newConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //通过通道绑定队列
        //参数一： 队列的名字 不存在就自动创建
        //参数二： 用来定义队列的特性是否要持久化 true为持久化 false为不持久化
        //参数三：是否独占队列
        //参数四：是否在消费完成后自动删除队列 true自动删除 false 不自动删除
        //参数五：额外附加参数
        //注意事项 消费者中队列的参数要和生产者中的参数一样，不然可能回报错

        channel.queueDeclare("myqueue",false,false,false,null);


        //消费消息
        /*
         *   参数一:消费哪个队列的消息 队列名称
         *   参数二：开启消息的自动确认机制
         *   参数三：消费时的回调接口
         * */
        channel.basicConsume("myqueue",true,new DefaultConsumer(channel){
            //最后一个参数：消息队列中取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                //输出这个消息
                System.out.println("new String(body) = "+new String(body));
            }
        });

        //消费者端不建议关闭通道和连接
        //关闭通道
       // channel.close();
        //关闭连接
        //connection.close();
    }
}
