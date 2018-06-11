package com.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class KafkaProducer {
    private final Producer<String,String> producer;
    public final static String TOPIC = "t_cdr";
    
    private KafkaProducer() throws IOException{
        Properties props = new Properties();
//        ResourceBundle resource = ResourceBundle.getBundle("kafka");
        InputStream in =KafkaProducer.class.getClass().getResourceAsStream("/kafkaProducer.Properties");
        props.load(in);
        producer = new Producer<String, String>(new ProducerConfig(props));
    }
    
    void produce(String data) {
        String key="1000";
//            String data = "hello kafka message OK";
        KeyedMessage<String, String> s = new KeyedMessage<String,String>(TOPIC, key, data);
        producer.send(s);
        producer.close();
    }
    public static void main(String[] args) throws IOException {
        while (true) {
            Scanner sc=new Scanner(System.in);
            String data=sc.nextLine();
            if(data!=null) {
                new KafkaProducer().produce(data);
            }
        }
    }
}
