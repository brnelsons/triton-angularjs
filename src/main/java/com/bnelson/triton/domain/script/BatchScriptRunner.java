package com.bnelson.triton.domain.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BatchScriptRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchScriptRunner.class);

    private final String command;

    public BatchScriptRunner(String command) {
        this.command = command;
    }

    @Nullable
    public OutputDelegate.Output run() {
        ProcessBuilder pb = ScriptUtil.getProcessBuilder(command);
        pb.redirectInput();
        pb.redirectOutput();
        try {
            Process process = pb.start();
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            return new OutputReader(input, process);
        } catch (IOException e) {
            LOGGER.error("something bad happened while running the script {}" ,command, e);
        }
        return null;
    }
}
