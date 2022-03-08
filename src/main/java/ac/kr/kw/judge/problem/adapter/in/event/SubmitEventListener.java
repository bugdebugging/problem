package ac.kr.kw.judge.problem.adapter.in.event;

import ac.kr.kw.judge.problem.domain.event.ChallengeSubmitted;
import ac.kr.kw.judge.problem.service.port.in.SolutionGradingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubmitEventListener {
    private final ObjectMapper objectMapper;
    private final SolutionGradingService solutionGradingService;

    @KafkaListener(topics = "submit", groupId = "problem-grading-group")
    public void consumeSubmitEvent(String message) {
        try {
            ChallengeSubmitted challengeSubmitted = objectMapper.readValue(message, ChallengeSubmitted.class);
            solutionGradingService.gradeChallengeSubmit(challengeSubmitted.getProblemId(), challengeSubmitted);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
