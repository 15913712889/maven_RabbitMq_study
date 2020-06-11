package cxq;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    //生产者生产消息
    @Test
    public void  testSendMessage() throws IOException, TimeoutException {
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
        channel.queueDeclare("myqueue",false,false,false,null);

        //发布消息
        //参数一： 交换机名称
        //参数二：队列名称
        //参数三：传递消息的额外设置
        //参数四：消息的具体内容
        channel.basicPublish("","myqueue",null,"hello rabbitmq".getBytes());






        channel.close();
        connection.close();
    }
}
