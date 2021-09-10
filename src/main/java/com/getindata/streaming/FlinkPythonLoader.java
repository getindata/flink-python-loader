package com.getindata.streaming;

import org.apache.flink.client.python.PythonDriver;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.stream.Collectors;


public class FlinkPythonLoader {
    public static void main(String[] args) throws Throwable {
        File file = File.createTempFile("pyflink-", ".py");
        Files.write(file.toPath(), getProgram().getBytes());
        PythonDriver.main(new String[]{"-py", file.getAbsolutePath()});
    }

    private static String getProgram() {
        return new BufferedReader(
                new InputStreamReader(FlinkPythonLoader.class.getClassLoader().getResourceAsStream("program.py"))
        ).lines().collect(Collectors.joining("\n"));
    }
}
