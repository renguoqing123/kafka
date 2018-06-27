package com.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class KafkaConsumerMessage {
    private KafkaConsumer<Integer, String> consumer;
    public final static String TOPIC = "t_cdr";
    
    KafkaConsumerMessage() throws IOException{
        Properties props = new Properties();
        InputStream in =KafkaProducer.class.getClass().getResourceAsStream("/kafkaConsumer.Properties");
        props.load(in);
        consumer = new KafkaConsumer<Integer, String>(props);
        ResourceBundle resource = ResourceBundle.getBundle("kafkaTopic");
        String topicName=resource.getString("topicName");
        String[] topicNames=topicName.split(",");
        consumer.subscribe(Arrays.asList(topicNames));//Arrays.asList("foo", "bar") 多个topic情况下通过集合处理 Collections.singletonList(topicName)
    }
    
    void consumer(){
        Map<String, Object> topicMap = new ConcurrentHashMap<String, Object>();
        try {
            while (true) {
                /* 读取数据，读取超时时间为1000ms */
                ConsumerRecords<Integer, String> records = consumer.poll(1000);
                for (ConsumerRecord<Integer, String> record : records) {
                    System.out.printf("topic = %s,offset = %d, key = %s, value = %s", record.topic(),record.offset(), record.key(), record.value());
                    System.out.println();
                    topicMap.put("topic",record.topic());
                    topicMap.put("offset",record.offset());
                    topicMap.put("key",record.key());
                    topicMap.put("value",record.value());
                }
                if(topicMap.size()>0){
                    System.out.printf("topicMap=%s",topicMap);
                    System.out.println("将数据以接口的形式推送出去");
                    topicMap.clear();
                }
            }
        }finally {
            consumer.close();
        }
    }
    
    public static void main(String[] args) throws IOException {
        new KafkaConsumerMessage().consumer();
    }
}
