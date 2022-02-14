package ac.kr.kw.judge.problem.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Embeddable
public class Limit {
    @Column
    private int memory;
    @Column
    private int time;

    private static final int MAXIMUM_MEM_LIMIT = 512;
    private static final int MAXIMUM_TIME_LIMIT = 10;

    protected Limit() {
    }

    private Limit(int memory, int time) {
        this.memory = memory;
        this.time = time;
    }

    public static Limit of(int memory, int time) {
        checkArgument(memory <= MAXIMUM_MEM_LIMIT, "설정가능한 메모리 최대:" + MAXIMUM_MEM_LIMIT);
        checkArgument(time <= MAXIMUM_TIME_LIMIT, "설정가능한 시간 최대:" + MAXIMUM_TIME_LIMIT);
        return new Limit(memory, time);
    }

    public int getMemory() {
        return memory;
    }

    public int getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Limit limit = (Limit) o;
        return memory == limit.memory && time == limit.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memory, time);
    }
}
