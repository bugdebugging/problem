package ac.kr.kw.judge.problem.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Embedded
    private Description description;

    @Embedded
    private Limit limit;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "test_cases", joinColumns = @JoinColumn(name = "problem_id"))
    private List<TestCase> testCases = new ArrayList<>();

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private int numOfSuccess;

    private int numOfFailed;

    private int numOfSubmits;

    protected Problem() {
    }

    public Problem(String name, Description description, Limit limit, List<TestCase> testCases) {
        this.name = name;
        this.description = description;
        this.limit = limit;
        this.testCases = testCases;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Limit getLimit() {
        return limit;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getNumOfSuccess() {
        return numOfSuccess;
    }

    public int getNumOfFailed() {
        return numOfFailed;
    }

    public int getNumOfSubmits() {
        return numOfSubmits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return Objects.equals(id, problem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
