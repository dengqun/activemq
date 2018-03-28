/**
 * @package_name: 
 * @file_name: Sender.java
 * @author: dengqun
 * @date: 2017-11-7
 * @version 1.0
 */

/**
 *@author: dengqun
 *@date: 2017-11-7 下午12:07:00
 *@version 1.0
 */
import java.io.Serializable;
import java.util.Date;

import javax.jms.Connection;  
import javax.jms.ConnectionFactory;  
import javax.jms.DeliveryMode;  
import javax.jms.Destination;  
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;  
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;  
import javax.jms.TextMessage;  
import org.apache.activemq.ActiveMQConnection;  
import org.apache.activemq.ActiveMQConnectionFactory;  
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
  
public class SenderTest {  
    private static final int SEND_NUMBER = 500;  
  
    public static void main(String[] args) {  
        // ConnectionFactory ：连接工厂，JMS 用它创建连接  
        ConnectionFactory connectionFactory; // Connection ：JMS 客户端到JMS  
        // Provider 的连接  
        Connection connection = null; // Session： 一个发送或接收消息的线程  
        Session session = null; // Destination ：消息的目的地;消息发送给谁.  
        Destination destination; // MessageProducer：消息发送者  
        MessageProducer producer; // TextMessage message;  
        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar  
        connectionFactory = new ActiveMQConnectionFactory(  
                ActiveMQConnection.DEFAULT_USER,  
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");  
        try { // 构造从工厂得到连接对象  
            connection = connectionFactory.createConnection();  
            // 启动  
            connection.start();  
            // 获取操作连接  
            session = connection.createSession(Boolean.FALSE,  
                    Session.AUTO_ACKNOWLEDGE);  
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
            destination = session.createQueue("testQueue"); 
//            destination = session.createTopic("testTopic"); 
            // 得到消息生成者【发送者】  
            producer = session.createProducer(destination);  
            // 设置不持久化，此处学习，实际根据项目决定  
//            producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 构造消息，此处写死，项目就是参数，或者方法获取  
             
            
//            producer.setDisableMessageTimestamp(true);
//            message.setJMSTimestamp(new Date().getTime());
            for(int i=1;i<=500;i++){
            	TextMessage message = session.createTextMessage(String.valueOf(i)); 
            	System.out.println("ActiveMq 发送的消息"+i);
            	producer.send(message);
            }
//            session.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (null != connection)  
                	System.out.println("不关闭连接");
//                    connection.close();  
//                session.close();
            } catch (Throwable ignore) {  
            }  
        }  
    }  
  
}  