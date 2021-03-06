/**
 * @package_name: 
 * @file_name: Receiver.java
 * @author: dengqun
 * @date: 2017-11-7
 * @version 1.0
 */

/**
 *@author: dengqun
 *@date: 2017-11-7 下午1:11:58
 *@version 1.0
 */
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
  
public class Receiverb {  
    public static void main(String[] args) {  
        // ConnectionFactory ：连接工厂，JMS 用它创建连接  
        ConnectionFactory connectionFactory;  
        // Connection ：JMS 客户端到JMS Provider 的连接  
        Connection connection = null;  
        // Session： 一个发送或接收消息的线程  
        final Session session;  
        // Destination ：消息的目的地;消息发送给谁.  
        Destination destination;  
        // 消费者，消息接收者  
        MessageConsumer consumer;  
        connectionFactory = new ActiveMQConnectionFactory(  
                ActiveMQConnection.DEFAULT_USER,  
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");  
        try {  
            // 构造从工厂得到连接对象  
            connection = connectionFactory.createConnection();  
            // 启动  
            connection.start();  
            // 获取操作连接  
            session = connection.createSession(Boolean.FALSE,  
                    Session.AUTO_ACKNOWLEDGE);  
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
            destination = session.createQueue("testQueue"); 
//            destination = session.createTopic("testTopic"); 
            consumer = session.createConsumer(destination,"client='B'");  
//            while (true) {  
                // 设置接收者接收消息的等待时间，不管有没有收到，到了时间，断开连接，为了便于测试，这里定为100s  
//                TextMessage message = (TextMessage) consumer.receive(); 
//            	ActiveMQObjectMessage obj=(ActiveMQObjectMessage) consumer.receive(); 
//            	User u=(User) obj.getObject();
//            	System.out.println(u.getUserid());
//            	System.out.println(u.getUsermessage());
//            	 System.out.println("CorrelationID"+obj.getJMSCorrelationID());
//                System.out.println(message.getJMSReplyTo());
//                System.out.println(message.getJMSCorrelationID());
//                System.out.println(message.getJMSDeliveryMode());
//                System.out.println(message.getJMSDestination());
//                System.out.println(message.getJMSMessageID());
//                System.out.println(message.getJMSExpiration());
//                if (null != message) {  
//                    System.out.println("收到消息" + message.getText());  
//                } else {  
//                    break;  
//                }  
//            }  
          while(true){  
        	  
            consumer.setMessageListener(new MessageListener () {
				@Override
				public void onMessage(Message message) {
//				 	ActiveMQObjectMessage obj=(ActiveMQObjectMessage) message; 
					
					 TextMessage text = (TextMessage) message;
//	            	User u = null;
//					try {
//						u = (User) obj.getObject();
//					} catch (JMSException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//	            	System.out.println(u.getUserid());
//	            	System.out.println(u.getUsermessage());
	            	 try {
						System.out.println("CorrelationID:"+text.getJMSCorrelationID());
						System.out.println("messageID:"+text.getJMSMessageID());
						System.out.println("收到消息" + text.getText());
						
						   // 回复一个消息 
						 Queue  queue = new ActiveMQQueue("testQueue"); 
						 MessageProducer producer = session.createProducer(queue);  
		                  
		                    Message replyMessage = session.createTextMessage("回复生产者来自b。。。。"+text.getText());  
		                    // 设置JMSCorrelationID为刚才收到的消息的ID  
		                    replyMessage.setJMSCorrelationID(text.getJMSMessageID()); 
		                    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		                    producer.send(replyMessage);  
		                    
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	 
					
				}
            });
            
          }
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (null != connection)  
//                    connection.close();  
                	System.out.println("kkkkk");
            } catch (Throwable ignore) {  
            }  
        }  
    }  
}  
