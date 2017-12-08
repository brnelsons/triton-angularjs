package com.bnelson.triton.api.model;

import com.bnelson.triton.domain.model.GameCommand;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.annotation.Nonnull;

public class UniqueCommandMetadata {
    @JsonProperty("time") private final String time;
    @JsonProperty("command") private final GameCommand command;
    @JsonProperty("output") private final String output;

    public UniqueCommandMetadata(@Nonnull String time,
                                 @Nonnull GameCommand command,
                                 @Nonnull String output) {
        this.time = time;
        this.command = command;
        this.output = output;
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
