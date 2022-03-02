package ac.kr.kw.judge.problem.adapter.in.web;

import ac.kr.kw.judge.commons.apis.ApiResult;
import ac.kr.kw.judge.commons.apis.ApiUtils;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.FileHashFailedException;
import ac.kr.kw.judge.problem.domain.Description;
import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.domain.TestCase;
import ac.kr.kw.judge.problem.dto.ProblemRegisterRequest;
import ac.kr.kw.judge.problem.service.command.ProblemRegisterCommand;
import ac.kr.kw.judge.problem.service.port.in.ProblemRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        List<String> inputFilePaths = saveFilesToLocal(rootDir, inputFiles);
        List<String> outputFilePaths = saveFilesToLocal(rootDir, outputFiles);
        List<String> outputHashes = extractHashFromOutputFile(outputFilePaths);

        List<TestCase> testcases = new ArrayList<>();
        for (int i = 0; i < inputFilePaths.size(); i++) {
            testcases.add(TestCase.of(inputFilePaths.get(i),
                    outputFilePaths.get(i),
                    outputHashes.get(i),
                    (i + 1) + ""));
        }

        ProblemRegisterCommand problemRegisterCommand = new ProblemRegisterCommand(name
                , Description.of(description, inputDescription, outputDescription)
                , Limit.of(memory, time), testcases, score);
        return ApiUtils.success(problemRegisterService.registerNewProblem(problemRegisterCommand));
    }

    private List<String> saveFilesToLocal(File rootFile, List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    File destination = new File(rootFile, file.getOriginalFilename());
                    try {
                        destination.createNewFile();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    try (FileOutputStream fos = new FileOutputStream(destination);
                         BufferedOutputStream bos = new BufferedOutputStream(fos);) {
                        bos.write(file.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return destination.getPath();
                }).collect(Collectors.toList());
    }

    private List<String> extractHashFromOutputFile(List<String> outputFilePaths) {
        return outputFilePaths.stream()
                .map(outputFile -> {
                    try {
                        return Base64.getEncoder().encodeToString(DigestUtils.md5Digest(new FileInputStream(outputFile)));
                    } catch (IOException e) {
                        throw new FileHashFailedException(e.getMessage());
                    }
                }).collect(Collectors.toList());
    }
}
