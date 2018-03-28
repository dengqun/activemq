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
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;  
import org.apache.activemq.ActiveMQConnectionFactory;  
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
  
public class TopicReceiverTest2 {  
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
        //开启无限重投
        try {  
            // 构造从工厂得到连接对象  
            connection = connectionFactory.createConnection();  
            connection.setClientID("client2");
            // 启动  
            connection.start();  
            // 获取操作连接  
            
            session = connection.createSession(Boolean.TRUE,  
                    Session.AUTO_ACKNOWLEDGE);  
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置  
//            destination = session.createQueue("testTopic"); 
//            destination = session.createTopic("testTopic"); 
//            consumer=session.createConsumer(destination);
            Topic topic = session.createTopic("testTopic");
            consumer=session.createDurableSubscriber(topic, "testTopic2");
//            session.unsubscribe("testTopic2");
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
					
					 TextMessage text = (TextMessage) message;
	            	 try {
						System.out.println("messageID:"+text.getJMSMessageID());
						System.out.println("topic2收到消息" + text.getText());
//						if(Integer.parseInt(text.getText())>=10){
//							throw new RuntimeException("抛了个异常");
//						}
//						message.acknowledge();
//						System.out.println("客户手动代码确认！！！");
						session.commit();//确认消费了 事务型消费者才要这样
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							session.rollback();
						} catch (JMSException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
