package ac.kr.kw.judge.problem.service.port.out;

public interface EventSender {
    void publish(String topic, Object data);

    void publishWithKey(String topic, String key, Object data);
}
