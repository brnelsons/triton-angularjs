package com.bnelson.triton.domain.script;

import java.io.BufferedReader;
import java.io.IOException;

public class OutputReader implements OutputDelegate.Output {
    private final BufferedReader reader;
    private String line;
    private Process process;

    public OutputReader(BufferedReader reader, Process process) {
        this.reader = reader;
        this.process = process;
    }

    public boolean isRunning(){
        return process.isAlive();
    }

    @Override
    public boolean hasNext() {
        try {
            return (line = reader.readLine()) != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String next() {
        return line;
    }
}
