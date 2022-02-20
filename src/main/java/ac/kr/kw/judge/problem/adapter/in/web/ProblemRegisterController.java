package ac.kr.kw.judge.problem.adapter.in.web;

import ac.kr.kw.judge.problem.adapter.out.execute.exception.FileHashFailedException;
import ac.kr.kw.judge.problem.domain.TestCase;
import ac.kr.kw.judge.problem.dto.ProblemRegisterRequest;
import ac.kr.kw.judge.problem.service.ProblemRegisterService;
import ac.kr.kw.judge.problem.service.command.ProblemRegisterCommand;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProblemRegisterController {
    private final ProblemRegisterService problemRegisterService;

    @PostMapping(value = "/api/problems")
    public Long registerProblem(
            @RequestPart(value = "problem") ProblemRegisterRequest problemRegisterRequest,
            @RequestPart(value = "inputs") List<MultipartFile> inputFiles,
            @RequestPart(value = "outputs") List<MultipartFile> outputFiles) {

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

        ProblemRegisterCommand problemRegisterCommand = new ProblemRegisterCommand(problemRegisterRequest.getName(),
                problemRegisterRequest.getDescription(),
                problemRegisterRequest.getLimit(),
                testcases);
        return problemRegisterService.registerNewProblem(problemRegisterCommand);
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
