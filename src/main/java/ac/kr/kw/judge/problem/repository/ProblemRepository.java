package ac.kr.kw.judge.problem.repository;

import ac.kr.kw.judge.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
