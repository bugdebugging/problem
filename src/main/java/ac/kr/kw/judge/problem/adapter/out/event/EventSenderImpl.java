package ac.kr.kw.judge.problem.adapter.out.event;

import ac.kr.kw.judge.problem.service.port.out.EventSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventSenderImpl implements EventSender {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(String topic, Object data) {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publishWithKey(String topic, String key, Object data) {
        try {
            kafkaTemplate.send(topic, key, objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
