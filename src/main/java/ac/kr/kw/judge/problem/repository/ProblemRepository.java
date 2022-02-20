package ac.kr.kw.judge.problem.repository;

import ac.kr.kw.judge.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    @Query("SELECT P FROM Problem P LEFT JOIN FETCH P.testCases WHERE  P.id = :problemId")
    Optional<Problem> findByIdWithTestCases(@Param("problemId") Long problemId);
}
