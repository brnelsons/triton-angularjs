package com.bnelson.triton.api.model;

import com.bnelson.triton.common.model.ComparatorType;
import com.bnelson.triton.common.model.GameCommand;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.annotation.Nonnull;

public class UniqueCommandMetadata {
    private String time;
    private GameCommand command;
    private String output;
    private ComparatorType resultComparatorType;
    private String expectedResult;

    @JsonCreator
    public UniqueCommandMetadata(@JsonProperty("time") @Nonnull String time,
                                 @JsonProperty("command") @Nonnull GameCommand command,
                                 @JsonProperty("output") @Nonnull String output) {
        this.time = time;
        this.command = command;
        this.output = output;
    }

    public String getTime() {
        return time;
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
        UniqueCommandMetadata that = (UniqueCommandMetadata) o;
        return Objects.equal(time, that.time) &&
                Objects.equal(command, that.command) &&
                Objects.equal(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time, command, output);
    }

    @Override
    public String toString() {
        return "UniqueCommandMetadata{" +
                "time='" + time + '\'' +
                ", command=" + command +
                ", output='" + output + '\'' +
                '}';
    }
}
