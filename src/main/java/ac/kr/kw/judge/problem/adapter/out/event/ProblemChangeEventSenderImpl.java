package ac.kr.kw.judge.problem.adapter.out.event;

import ac.kr.kw.judge.commons.exception.EventPublishFailedException;
import ac.kr.kw.judge.problem.dto.ProblemDto;
import ac.kr.kw.judge.problem.service.port.out.ProblemChangeEventSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemChangeEventSenderImpl implements ProblemChangeEventSender {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String,String>kafkaTemplate;

    @Override
    public void publish(ProblemDto problemDto) {
        try {
            String data = objectMapper.writeValueAsString(problemDto);
            kafkaTemplate.send("problem",data);
        } catch (JsonProcessingException e) {
            throw new EventPublishFailedException(e.getMessage());
        }
    }
}
