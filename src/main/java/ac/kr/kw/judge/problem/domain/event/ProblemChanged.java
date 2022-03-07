package ac.kr.kw.judge.problem.domain.event;

import ac.kr.kw.judge.problem.domain.Problem;

public class ProblemChanged {
    private Long id;
    private String name;
    private String description;
    private String inputDescription;
    private String outputDescription;
    private int score;

    private ProblemChanged(Long id, String name, String description, String inputDescription, String outputDescription, int score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.inputDescription = inputDescription;
        this.outputDescription = outputDescription;
        this.score = score;
    }

    public static ProblemChanged fromEntity(Problem problem) {
        return new ProblemChanged(problem.getId(),
                problem.getName(),
                problem.getDescription().getDescription(),
                problem.getDescription().getInputDescription(),
                problem.getDescription().getOutputDescription(),
                problem.getScore());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInputDescription() {
        return inputDescription;
    }

    public String getOutputDescription() {
        return outputDescription;
    }

    public int getScore() {
        return score;
    }
}
