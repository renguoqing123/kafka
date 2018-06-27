package com.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class KafkaProducer {
    public Producer<String, String> producer;
    public String                   TOPIC = "t_cdr";
    String                          key   = "1000";

    public KafkaProducer() throws IOException {
        Properties props = new Properties();
        InputStream in = KafkaProducer.class.getClass().getResourceAsStream("/kafkaProducer.Properties");
        props.load(in);
        producer = new Producer<String, String>(new ProducerConfig(props));
    }

    public KafkaProducer(String key, String topic, String msg) throws IOException {
        this();
        this.key = key;
        this.TOPIC = topic;
        produce(msg);
    }

    void produce(String data) {
        KeyedMessage<String, String> s = new KeyedMessage<String, String>(TOPIC, key, data);
        producer.send(s);
        producer.close();
    }

    public static void main(String[] args) throws IOException {
        new KafkaProducer("10", "t_cdr", "welcome to kafka!");
        while (true) {
            Scanner sc = new Scanner(System.in);
            String data = sc.nextLine();
            if (data != null) {
                new KafkaProducer().produce(data);
            }
        }
    }
}
