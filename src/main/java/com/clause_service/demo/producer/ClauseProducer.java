package com.clause_service.demo.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClauseProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // ✅ FIXED topic name
    private static final String TOPIC = "clause-splitted-topic";

    public void sendClause(String clause) {

        kafkaTemplate.send(TOPIC, clause);

        System.out.println(
                "📤 Clause sent to Kafka → "
                        + clause + "\n");
    }
}
