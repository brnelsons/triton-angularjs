package com.bnelson.triton.dao;

import com.bnelson.triton.domain.script.BatchScriptRunner;
import com.bnelson.triton.domain.script.OutputDelegate;
import com.bnelson.triton.domain.script.ScriptUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandRunnerTest {
    @Test
    public void runTestNoWait() {
        BatchScriptRunner runner = new BatchScriptRunner("echo abc");
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.run());
        assertEquals("abc\n", output.read());
    }

    @Test
    public void runTestWait() {
        BatchScriptRunner runner = new BatchScriptRunner("echo abc 123");
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.run());
        assertEquals("abc 123\n", output.read());
    }

    @Test
    public void runTestTaskTestExplorer() {
        BatchScriptRunner runner = new BatchScriptRunner(ScriptUtil.getIsProcessRunningString("explorer.exe"));
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.run());
        assertTrue(ScriptUtil.isProcessRunningFromResult(output.read()));
    }

    @Test
    public void runTestTaskTestFalse() {
        BatchScriptRunner runner = new BatchScriptRunner(ScriptUtil.getIsProcessRunningString("notaprogram.exe"));
        OutputDelegate output = new OutputDelegate(100);
        output.addOutput(runner.run());
        assertFalse(ScriptUtil.isProcessRunningFromResult(output.read()));
    }
}