package ac.kr.kw.judge.problem.adapter.in.web;

import ac.kr.kw.judge.commons.apis.ApiResult;
import ac.kr.kw.judge.commons.apis.ApiUtils;
import ac.kr.kw.judge.commons.utils.ProblemFileManager;
import ac.kr.kw.judge.problem.domain.Description;
import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.domain.TestCase;
import ac.kr.kw.judge.problem.service.command.ProblemRegisterCommand;
import ac.kr.kw.judge.problem.service.port.in.ProblemRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@Api(tags = {"problems"})
@RestController
@RequiredArgsConstructor
public class ProblemRegisterController {
    private final ProblemRegisterService problemRegisterService;

    @ApiOperation(value = "새로운 문제 등록", notes = "테스트 케이스와 함께 새로운 문제 등록.")
    @PostMapping(value = "/api/problems")
    public ApiResult<Long> registerProblem(
            String name, String description,
            @RequestParam("input_description") String inputDescription,
            @RequestParam("output_description") String outputDescription,
            int time, int memory, int score,
            @RequestPart(value = "inputs") List<MultipartFile> inputFiles,
            @RequestPart(value = "outputs") List<MultipartFile> outputFiles) {
        checkArgument(inputFiles.size() == outputFiles.size(), "업로드할 input과 output파일의 수는 동일해야합니다.");

        File rootDir = new File("//problems//" + UUID.randomUUID().toString());
        rootDir.mkdirs();
        List<File> savedInputFiles = ProblemFileManager.saveToLocalRootDir(rootDir, inputFiles);
        List<File> savedOutputFiles = ProblemFileManager.saveToLocalRootDir(rootDir, outputFiles);
        List<String> outputHashes = ProblemFileManager.convertFilesToHashes(savedOutputFiles);

    ProblemRegisterCommand problemRegisterCommand = new ProblemRegisterCommand(name
            , Description.of(description, inputDescription, outputDescription)
            , Limit.of(memory, time), createTestCases(savedInputFiles, savedOutputFiles, outputHashes), score);
        return ApiUtils.success(problemRegisterService.registerNewProblem(problemRegisterCommand));
}

    private List<TestCase> createTestCases(List<File> inputFiles, List<File> outputFiles, List<String> outputFileHashes) {
        List<TestCase> result = new ArrayList<>();
        for (int i = 0; i < inputFiles.size(); i++) {
            result.add(TestCase.of(inputFiles.get(i).getPath(),
                    outputFiles.get(i).getPath(),
                    outputFileHashes.get(i),
                    (i + 1) + ""));
        }
        return result;
    }
}
