package com.bnelson.triton.domain.script;

import com.bnelson.triton.common.model.GameCommand;
import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import java.util.Date;

public class UniqueCommand {
    private final Date time;
    private final GameCommand command;
    private final OutputDelegate outputDelegate;

    public UniqueCommand(@Nonnull Date time,
                         @Nonnull GameCommand command,
                         @Nonnull OutputDelegate outputDelegate) {
        this.time = time;
        this.command = command;
        this.outputDelegate = outputDelegate;
    }

    public Date getTime() {
        return time;
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
        return Objects.equal(time, that.time) &&
                Objects.equal(command, that.command) &&
                Objects.equal(outputDelegate, that.outputDelegate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time, command, outputDelegate);
    }
}
