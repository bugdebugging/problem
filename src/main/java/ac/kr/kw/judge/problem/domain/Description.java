package ac.kr.kw.judge.problem.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Description {
    @Column
    private String description;
    @Column
    private String inputDescription;
    @Column
    private String outputDescription;

    protected Description() {
    }

    private Description(String description, String inputDescription, String outputDescription) {
        this.description = description;
        this.inputDescription = inputDescription;
        this.outputDescription = outputDescription;
    }

    public static Description of(String description, String inputDescription, String outputDescription){
        return new Description(description,inputDescription,outputDescription);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(description, that.description) && Objects.equals(inputDescription, that.inputDescription) && Objects.equals(outputDescription, that.outputDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, inputDescription, outputDescription);
    }
}
