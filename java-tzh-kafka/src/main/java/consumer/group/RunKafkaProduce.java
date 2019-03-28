package consumer.group;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;
 
public class RunKafkaProduce {
 
    private final Producer<String, String> producer;
    public final static String TOPIC = "tangzihao";
 
    private RunKafkaProduce(){
 
        Properties props = new Properties();
 
        // 此处配置的是kafka的broker地址:端口列表
//        props.put("metadata.broker.list", "192.168.1.244:9092,192.168.1.245:9092,192.168.1.246:9092");
        props.put("metadata.broker.list", "192.168.1.138:9092,192.168.1.175:9092,192.168.1.180:9092");
        
        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
 
        props.put("partitioner.class", "production.partition.SimplePartitioner");
 
        //request.required.acks
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
        props.put("request.required.acks","1");
 
        producer = new Producer<String, String>(new ProducerConfig(props));
    }
 
    void produce() {
        final int COUNT = 10;
        Random rnd = new Random();
        int messageCount = 0;
        for (long nEvents = 0; nEvents < COUNT; nEvents++) { 
            long runtime = new Date().getTime();  
            String ip = "192.168.1." + rnd.nextInt(255); 
            String msg = runtime + ",生产者," + ip; 
			   //eventKey必须有（即使自己的分区算法不会用到这个key，也不能设为null或者""）,否者自己的分区算法根本得不到调用
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("tangzihao", ip, msg);
            System.out.println(msg);
            producer.send(data);
			   try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            	
            }
			messageCount++;
     }
        System.out.println("Producer端一共产生了" + messageCount + "条消息！");
    }
 
    public static void main( String[] args )
    {
        new RunKafkaProduce().produce();
    }
}
