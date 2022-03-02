package ac.kr.kw.judge.commons.utils;

import ac.kr.kw.judge.commons.exception.FileUploadFailedException;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.FileHashFailedException;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemFileManager {
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

    private static void createNewFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadFailedException(e.getMessage());
        }
    }
}
