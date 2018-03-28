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
  
public class Sender {  
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
            // 获取操作连接   第一个参数为true ，为事务型 会忽略第二个参数，将第二个参数设置为 Session.SESSION_TRANSACTED;false为非事务型
            //发送者transacted事务（true），事务成功commit(),即session.commit()才会将消息发送到mom中 mom就是面向消息的中间件，消费者如果还设置为事务型transacted事务（true）需要commit,即session.commit()才会确认掉消息，
            //当然你可以将消费者设置为非事务（false），Session.AUTO_ACKNOWLEDGE，这样执行完接收的消息就自动消费确认掉了
            
//            发送者为非事务false，如果你有session.commit()代码，发送者会爆出javax.jms.IllegalStateException: Not a transacted session commit前的send信息还是可以上去的，后面的发送信息上不去。
            
            
            //如果session带有事务，并且事务成功提交，则消息被自动签收。如果事务回滚，则消息会被再次传送。 
//            消息事务是在生产者producer到broker或broker到consumer过程中同一个session中发生的， 
//            保证几条消息在发送过程中的原子性。 
//            在支持事务的session中，producer发送message时在message中带有transactionID。 
//            broker收到message后判断是否有transactionID，如果有就把message保存在transaction store中， 
//            等待commit或者rollback消息。 
            session = connection.createSession(Boolean.FALSE,  
                    Session.AUTO_ACKNOWLEDGE);  
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
            destination = session.createQueue("testQueue"); 
//            destination = session.createTopic("testTopic"); 
            // 得到消息生成者【发送者】  
            producer = session.createProducer(destination);  
            // 设置不持久化，此处学习，实际根据项目决定  
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
//            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 构造消息，此处写死，项目就是参数，或者方法获取  
            sendMessage(session, producer);  
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
  
    public static void sendMessage(Session session, MessageProducer producer)  
            throws Exception {  
//    	BrokerService broker = new BrokerService();  
        for (int i = 1; i <= SEND_NUMBER; i++) {  
        	
//        	User user=new User();
//        	user.setUserid(i);
//        	user.setUsermessage("ActiveMq 发送的消息");
            TextMessage message = session.createTextMessage("ActiveMq 发送的消息"  
                    + i);  
//            message.setJMSMessageID("dq"+String.valueOf(new Date().getTime()));
            if(i<=250){
            	message.setStringProperty("client", "A");
            }else{
            	message.setStringProperty("client", "B");
            }
            
            // 发送消息到目的地方  
//        	ActiveMQObjectMessage obj=(ActiveMQObjectMessage) session.createObjectMessage();
//        	obj.setObject(user);
            System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);  
//            if(ActiveMQUtil.getQueueSize("testQueue")>=1000) {
//                System.out.println("队列用户数超过1000，拒绝你的请求");
//
//            }else {
            producer.setDisableMessageTimestamp(true);
            message.setJMSTimestamp(new Date().getTime());
                producer.send(message);
                
                // 等待回复的消息  
                Queue  queue = new ActiveMQQueue("testQueue"); 
                MessageConsumer replyConsumer = session.createConsumer(queue,"JMSCorrelationID='" + message.getJMSMessageID() + "'");  
                 
                replyConsumer.setMessageListener(new MessageListener() {  
                    public void onMessage(Message m) {  
                        try {  
                            System.out.println("生产者收到消息:" + ((TextMessage) m).getText());  
                        } catch (JMSException e) {  
                        }  
                    }  
                });
                // 等待回复的消息  
                
                
//                producer.setDisableMessageID(true);
//                System.out.println(new Date().getTime());
                
                if(i%10==0){
                    System.out.println("一次提交");
                    session.commit();
                }

//            }
            
        }  
    }  
}  