package util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqUtil {

    private static ConnectionFactory connectionFactory;
    static{
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.135.3");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        //设置虚拟主机
        connectionFactory.setVirtualHost("/cxq");
    }

    //提供链接对象
    public static Connection getConnection(){
        try {
            return connectionFactory.newConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    //关闭连接的方法
    public static void closeConnection(Channel channel,Connection conn ){
        try{
            //为了防止空指针异常，需要加上判断
           if(channel!=null) channel.close();
           if(conn!=null) conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
