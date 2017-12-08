package com.bnelson.triton.domain.model;

public class GameCommand {
    private String name;
    private GameCommandType type;
    private String exe;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameCommand that = (GameCommand) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != that.type) return false;
        return exe != null ? exe.equals(that.exe) : that.exe == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (exe != null ? exe.hashCode() : 0);
        return result;
    }
}
