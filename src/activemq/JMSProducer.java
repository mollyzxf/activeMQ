package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class JMSProducer {

    private static final String USERNAME=ActiveMQConnectionFactory.DEFAULT_USER;
    private static final String PASSWORD=ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private static final String BROKEURL=ActiveMQConnectionFactory.DEFAULT_BROKER_URL;//默认的连接
    private static final int SENDNUM=10;//发送的消息数量

    public static void main(String[]args){

       ConnectionFactory  connectionFactory;//连接工厂
       Connection connection = null;//连接
        Session session;//会话，接受或者发送消息的线程
        Destination destination;//消息目的地
        MessageProducer messageProducer;//消息生产者
        //实例化连接工厂
        connectionFactory=new ActiveMQConnectionFactory(JMSProducer.USERNAME,JMSProducer.PASSWORD,JMSProducer.BROKEURL);

        try {
            connection = connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();
            session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            destination =session.createQueue("FirstQueue1");//创建消息队列
            messageProducer = session.createProducer(destination);//创建消息生产者
            sendMessage(session,messageProducer);
            session.commit();
        } catch (Exception e) {
            //todo Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        for(int i=0;i<JMSProducer.SENDNUM;i++){
            TextMessage message = session.createTextMessage("ActiveMQ发送的消息"+i);
            System.out.println("发送消息："+"ActiveMQ发送的消息"+i);
            messageProducer.send(message);
        }
    }
}
