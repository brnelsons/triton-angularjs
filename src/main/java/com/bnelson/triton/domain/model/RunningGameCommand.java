package com.bnelson.triton.domain.model;

import java.util.UUID;

public class RunningGameCommand{
    private final UUID jobUuid;
    private final GameCommand gameCommand;

    private Status status;

    public RunningGameCommand(UUID jobUuid,
                              Status status,
                              GameCommand gameCommand) {
        this.jobUuid = jobUuid;
        this.status = status;
        this.gameCommand = gameCommand;
    }

    public UUID getJobUuid() {
        return jobUuid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public GameCommand getGameCommand() {
        return gameCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RunningGameCommand that = (RunningGameCommand) o;

        return jobUuid != null ? jobUuid.equals(that.jobUuid) : that.jobUuid == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (jobUuid != null ? jobUuid.hashCode() : 0);
        return result;
    }

    public enum Status{
        WAITING,
        RUNNING,
        FAILED,
        DONE
    }
}
