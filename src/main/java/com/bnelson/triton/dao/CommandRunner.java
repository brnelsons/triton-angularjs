package com.bnelson.triton.dao;

import com.bnelson.triton.pojo.Game;
import com.bnelson.triton.pojo.GameCommand;
import com.google.common.collect.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class CommandRunner {

    private final ExecutorService executor;
    private final boolean isWindows;
    private final Map<String, String> jobResultMap;
    private final List<String> runningJobList;

    @Autowired
    public CommandRunner() {
        isWindows = System.getProperty("os.name").contains("win");
        executor = Executors.newFixedThreadPool(2);
        jobResultMap = new HashMap<>();
        runningJobList = new ArrayList<>();
    }

    public Map<String, String> getAllFinishedJobs(){
        return ImmutableMap.copyOf(jobResultMap);
    }

    public List<String> getAllRunningJobs(){
        return ImmutableList.copyOf(runningJobList);
    }

    public String getResult(Game game, GameCommand gameCommand){
        String key = getKey(game, gameCommand);
        return jobResultMap.get(key);
    }

    public boolean addJob(Game game, GameCommand gameCommand) {
        String key = getKey(game, gameCommand);
        if(runningJobList.contains(key)){
            return false;
        }
        executor.submit(() -> {
                    StringBuilder jobOutput = new StringBuilder();
                    try {
                        Process p = new ProcessBuilder(isWindows ? "cmd /c" : "/bin/bash", replaceJobParams(game, gameCommand)).start();
                        InputStream inputStream = p.getInputStream();
                        p.waitFor(1, TimeUnit.MINUTES);
                        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
                            while (buffer.read() != -1) {
                                jobOutput.append(buffer.readLine());
                                jobOutput.append("\n");
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        runningJobList.remove(key);
                        jobResultMap.put(key, jobOutput.toString());
                    }
                }
        );
        return true;
    }

    private String getKey(Game game, GameCommand gameCommand) {
        return game.getGameName() + game.getServerName() + gameCommand.getName();
    }

    //TODO make this accept "extras" for job running specifics so we can inject variables into codes
    private String replaceJobParams(Game game, GameCommand gameCommand) {
        return "\"" + gameCommand.getExe()
                .replace("{gameName}", game.getGameName())
                .replace("{serverName}", game.getServerName()) +
                "\"";
    }

}
