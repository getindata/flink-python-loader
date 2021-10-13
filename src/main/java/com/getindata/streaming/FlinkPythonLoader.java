package com.getindata.streaming;

import org.apache.flink.client.python.PythonDriver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class FlinkPythonLoader {

    private static final String DEFAULT_FILE_PATH = "/flink/python_src/flink_app.py";

    public static void main(String[] args) throws Throwable {
        String filePath = args.length > 0 ? args[0] : DEFAULT_FILE_PATH;
        File pythonFile = handleFile(filePath, (InputStream is) -> {
            File file = File.createTempFile("pyflink-", ".py");
            Files.write(file.toPath(), getProgram(is).getBytes());
            return file;
        });
        PythonDriver.main(new String[]{"-py", pythonFile.getAbsolutePath()});
    }

    private static String getProgram(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
    }

    private static File handleFile(String filePath, ThrowingFunction<InputStream, File> handleStream) throws Throwable {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filePath))) {
            return handleStream.apply(is);
        }
    }
}

@FunctionalInterface
interface ThrowingFunction<T, R> {
    R apply(T t) throws Throwable;
}
