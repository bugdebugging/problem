package ac.kr.kw.judge.problem.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public void changeInfo(Description description, Limit limit, String name) {
        this.description = description;
        this.limit = limit;
        this.name = name;
    }

    public void addTestCase(TestCase testCase){
        testCases.add(testCase);
        this.checkDuplicateTestCase();
    }
    public void deleteTestCase(TestCase testCase){
        testCases.stream().filter(tc ->tc.equals(testCase))
                .findFirst().orElseThrow(()->{
           throw new IllegalArgumentException("해당 test case의 문제가 존재하지 않습니다.");
        });
    }

    protected Problem() {
    }

    public Problem(String name, Description description, Limit limit, List<TestCase> testCases) {
        this.name = name;
        this.description = description;
        this.limit = limit;
        this.testCases = testCases;
        this.checkDuplicateTestCase();
    }
    private void checkDuplicateTestCase(){
        if(this.testCases.stream().distinct().collect(Collectors.toList()).size()!=testCases.size()){
            throw new IllegalArgumentException("중복된 tc를 여러번 올릴 수 없습니다.");
        }
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
