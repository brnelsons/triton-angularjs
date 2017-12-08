package com.bnelson.triton.domain.script;

import com.bnelson.triton.domain.model.GameCommand;
import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import java.util.Date;

public class UniqueCommand {
    private final Date commandTime;
    private final GameCommand command;
    private final OutputDelegate outputDelegate;

    public UniqueCommand(@Nonnull Date commandTime,
                         @Nonnull GameCommand command,
                         @Nonnull OutputDelegate outputDelegate) {
        this.commandTime = commandTime;
        this.command = command;
        this.outputDelegate = outputDelegate;
    }

    public Date getCommandTime() {
        return commandTime;
    }

    public GameCommand getCommand() {
        return command;
    }

    public OutputDelegate getOutputDelegate() {
        return outputDelegate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueCommand that = (UniqueCommand) o;
        return Objects.equal(commandTime, that.commandTime) &&
                Objects.equal(command, that.command) &&
                Objects.equal(outputDelegate, that.outputDelegate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(commandTime, command, outputDelegate);
    }
}
