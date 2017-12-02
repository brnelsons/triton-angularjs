package com.bnelson.triton.api.model;

import com.bnelson.triton.service.pojo.GameCommand;
import com.google.common.base.Objects;

import javax.annotation.Nonnull;

public class UniqueCommandRpc {
    private final String commandTime;
    private final GameCommand command;
    private final String output;

    public UniqueCommandRpc(@Nonnull String commandTime,
                            @Nonnull GameCommand command,
                            @Nonnull String output) {
        this.commandTime = commandTime;
        this.command = command;
        this.output = output;
    }

    public String getCommandTime() {
        return commandTime;
    }

    public GameCommand getCommand() {
        return command;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueCommandRpc that = (UniqueCommandRpc) o;
        return Objects.equal(commandTime, that.commandTime) &&
                Objects.equal(command, that.command) &&
                Objects.equal(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(commandTime, command, output);
    }
}
