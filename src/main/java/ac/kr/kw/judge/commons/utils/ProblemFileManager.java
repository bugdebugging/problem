package ac.kr.kw.judge.commons.utils;

import ac.kr.kw.judge.commons.exception.FileUploadFailedException;
import ac.kr.kw.judge.commons.exception.FileHashFailedException;
import ac.kr.kw.judge.commons.exception.SourceCodeCreateException;
import ac.kr.kw.judge.problem.domain.ProgrammingLanguage;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProblemFileManager {
    public static final String executeWorkRootDir="//execute//";

    public static List<File> saveToLocalRootDir(File rootDir, List<MultipartFile> files) {
        List<File> result = new ArrayList<>();
        for (MultipartFile file : files) {
            File targetFile = new File(rootDir, file.getOriginalFilename());
            createNewFile(targetFile);
            try (FileOutputStream fos = new FileOutputStream(targetFile);
                 BufferedOutputStream bos = new BufferedOutputStream(fos);) {
                bos.write(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileUploadFailedException(e.getMessage());
            }
            result.add(targetFile);
        }
        return result;
    }

    public static List<String> convertFilesToHashes(List<File> files) {
        return files.stream()
                .map(file -> {
                    try (FileInputStream fis = new FileInputStream(file);) {
                        return Base64.getEncoder().encodeToString(DigestUtils.md5Digest(fis));
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new FileHashFailedException(e.getMessage());
                    }
                }).collect(Collectors.toList());
    }

    public static String createSourceCodeFile(File rootDir, String sourceCode, ProgrammingLanguage programmingLanguage) {
        File targetFile = new File(rootDir, programmingLanguage.getFileName());
        createNewFile(targetFile);

        try (FileOutputStream fos = new FileOutputStream(targetFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos);) {
            bos.write(sourceCode.getBytes());
            return targetFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SourceCodeCreateException(e.getMessage());
        }
    }

    public static File createExecuteDir(){
        File rootDir=new File(executeWorkRootDir+ UUID.randomUUID().toString());
        rootDir.mkdir();
        return rootDir;
    }

    private static void createNewFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadFailedException(e.getMessage());
        }
    }
}
