package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    private static final String USERNAME=ActiveMQConnectionFactory.DEFAULT_USER;
    private static final String PASSWORD=ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private static final String BROKEURL=ActiveMQConnectionFactory.DEFAULT_BROKER_URL;//默认的连接
    private static final int SENDNUM=10;//发送的消息数量

    public static void main(String args[]){
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection = null;//连接
        Session session;//会话，接受或者发送消息的线程
        Destination destination;//消息目的地
        MessageConsumer messageConsumer;//消息的消费者

        //实例化工厂
        connectionFactory=new ActiveMQConnectionFactory(JMSConsumer.USERNAME,JMSConsumer.PASSWORD,JMSConsumer.BROKEURL);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue1");
            messageConsumer = session.createConsumer(destination);
            while (true){
                TextMessage textMessage =(TextMessage) messageConsumer.receive(100000);
                if(textMessage != null){
                    System.out.println("收到的消息:"+textMessage.getText());
                }else{
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
