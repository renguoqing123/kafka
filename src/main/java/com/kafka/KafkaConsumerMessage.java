package com.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;


public class KafkaConsumerMessage {
    private KafkaConsumer<Integer, String> consumer;
    public final static String TOPIC = "t_cdr";
    
    KafkaConsumerMessage(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.84.137:9092");  // 该地址是集群的子集，用来探测集群。多个以逗号隔开
        //props.put("zookeeper.connect", "192.168.84.137:2181");
        props.put("group.id", "test-consumer-group");// cousumer的分组id  对应配置文件config/consumer.properties
        //props.put("zookeeper.session.timeout.ms", "400");
        //props.put("zookeeper.sync.time.ms", "200");
        props.put("enable.auto.commit", "true");// 自动提交offsets
        props.put("auto.commit.interval.ms", "1000");// 每隔1s，自动提交offsets
        props.put("session.timeout.ms", "30000");  // Consumer向集群发送自己的心跳，超时则认为Consumer已经死了，kafka会把它的分区分配给其他进程
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer"); // 反序列化器 
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("org.apache.kafka.clients.consumer","off");
//        consumer=Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        consumer = new KafkaConsumer<Integer, String>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));//Arrays.asList("foo", "bar") 多个topic情况下通过集合处理
    }
    
    void consumer(){
//        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//        topicCountMap.put(TOPIC, 12);
//        consumer.createMessageStreams(topicCountMap);
//        consumer.commitOffsets();
        try {
            while (true) {
                /* 读取数据，读取超时时间为100ms */
                ConsumerRecords<Integer, String> records = consumer.poll(100);
                for (ConsumerRecord<Integer, String> record : records) {
                    System.out.printf("topic = %s,offset = %d, key = %s, value = %s", record.topic(),record.offset(), record.key(), record.value());
                    System.out.println();
                }
            }
        }finally {
            consumer.close();
        }
    }
    
    public static void main(String[] args) {
        new KafkaConsumerMessage().consumer();
    }
}
