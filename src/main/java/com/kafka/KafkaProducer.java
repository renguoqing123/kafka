package com.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducer {
    private final Producer<String,String> producer;
    public final static String TOPIC = "t_cdr";
    
    private KafkaProducer(){
        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.84.137:9092"); // 该地址是集群的子集，用来探测集群。多个以逗号隔开
        // key的序列化方式
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        // 设置分区策略，默认时取模，或者自己根据key写路由算法
        props.put("partitioner.class", "kafka.producer.DefaultPartitioner");
        //指定消息发送是同步模式还是异步模式。可选值为async和sync。默认值为sync。
        props.put("producer.type", "sync");
        //key的类型需要和serializer保持一致，如果key是String，则需要配置为kafka.serializer.StringEncoder，如果不配置，默认为kafka.serializer.DefaultEncoder，即二进制格式
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        // 设置消息确认模式
        // 0:不保证消息的到达确认，只管发送，低延迟但是会出现消息的丢失，在某个server失败的情况下，有点像TCP
        // 1:发送消息，并会等待leader 收到确认后，一定的可靠性
        // 2:发送消息，等待leader收到确认，并进行复制操作后，才返回，最高的可靠性
        props.put("request.required.acks", "1");
        producer = new Producer<String, String>(new ProducerConfig(props));
    }
    
    void produce(String data) {
        for(int i=0;i<100;i++){
            String key="1000";
            data=data+i;
//            String data = "hello kafka message OK";
            KeyedMessage<String, String> s = new KeyedMessage<String,String>(TOPIC, key, data);
            producer.send(s);
        }
          producer.close();
    }
    public static void main(String[] args) {
        String data="传输的数据(send)";
        new KafkaProducer().produce(data);
    }
}
