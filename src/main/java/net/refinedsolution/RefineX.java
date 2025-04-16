package net.refinedsolution;

import net.refinedsolution.lua.RunnerImpl;
import net.refinedsolution.context.refex.natives.SIM;
import net.refinedsolution.util.issue.IssueManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for RefineX
 * @author Java3east
 */
public class RefineX {
    public static final IssueManager manager = new IssueManager();
    public static List<Thread> threadRegister = new ArrayList<>();

    public static void onThreadFinish(Thread thread) {
        threadRegister.remove(thread);
        if (threadRegister.isEmpty()) {
            System.exit(manager.getIssues().length);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: RefineX <path>");
            return;
        }

        String path = args[0];
        File pathFile = new File(path);
        if (!pathFile.exists()) {
            System.out.println("Path " + path + " does not exist");
            return;
        }

        RunnerImpl runner = new RunnerImpl();
        runner.addNamespace(SIM.class);
        runner.loadFile("./lib/native.lua");
        runner.loadFile("./lib/simulation.lua");
        runner.loadFile("./lib/test.lua");
        runner.loadFile(path);
    }
}
