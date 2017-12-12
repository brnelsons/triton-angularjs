package com.bnelson.triton.common.model;

import com.bnelson.triton.domain.model.GameCommandType;
import com.google.common.base.Objects;

public class GameCommand {
    private String name;
    private GameCommandType type;
    private String exe;
    private ComparatorType resultComparatorType;
    private String expectedResult;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public GameCommandType getType() {
        return type;
    }

    public void setType(GameCommandType type) {
        this.type = type;
    }

    public String getExe() {
        return exe;
    }

    public void setExe(String exe) {
        this.exe = exe;
    }

    public ComparatorType getResultComparatorType() {
        return resultComparatorType;
    }

    public void setResultComparatorType(ComparatorType resultComparatorType) {
        this.resultComparatorType = resultComparatorType;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCommand that = (GameCommand) o;
        return Objects.equal(name, that.name) &&
                type == that.type &&
                Objects.equal(exe, that.exe) &&
                resultComparatorType == that.resultComparatorType &&
                Objects.equal(expectedResult, that.expectedResult);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, type, exe, resultComparatorType, expectedResult);
    }
}
