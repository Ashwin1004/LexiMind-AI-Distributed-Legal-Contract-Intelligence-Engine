package com.clause_service.demo.consumer;

import com.clause_service.demo.producer.ClauseProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClauseConsumer {

    @Autowired
    private ClauseProducer producer;

    @KafkaListener(
            topics = "text-extracted-topic",
            groupId = "clause-group"
    )
    public void consumeExtractedText(String message) {

        System.out.println(
                "\n📩 Received Extracted Text Event:\n");

        System.out.println(message);

        // Split clauses
        splitClauses(message);
    }

    // -------- Clause Splitting --------

    private void splitClauses(String text) {

        System.out.println(
                "\n📑 Splitting into Clauses...\n");

        // Split using period or newline
        String[] clauses =
                text.split("\\.|\\n");

        int count = 1;

        for (String clause : clauses) {

            clause = clause.trim();

            if (!clause.isEmpty()) {

                String formattedClause =
                        "Clause " + count +
                                " → " + clause;

                System.out.println(
                        formattedClause + "\n");

                // Send to Kafka
                producer.sendClause(formattedClause);

                count++;
            }
        }
    }
}
