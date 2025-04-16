package net.refinedsolution;

import net.refinedsolution.context.refex.natives.TEST;
import net.refinedsolution.lua.RunnerImpl;
import net.refinedsolution.context.refex.natives.SIM;
import net.refinedsolution.util.Marker;
import net.refinedsolution.util.issue.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main entry point for RefineX
 * @author Java3east
 */
public class RefineX {
    public static final IssueManager manager = new IssueManager();
    public static List<Thread> threadRegister = new ArrayList<>();
    public static boolean useMarkers = false;
    public static HashMap<String, Marker> markers = new HashMap<>();

    public static void onThreadFinish(Thread thread) {
        threadRegister.remove(thread);
        if (threadRegister.isEmpty()) {
            for (String key : markers.keySet()) {
                Marker marker = markers.get(key);
                if (marker != null && !marker.isReached()) {
                    Issue issue = new IssueImpl(
                            IssueLevel.WARNING,
                            "Function '" + marker.getFunctionName() + "' was never reached",
                            "RefineX", new TraceEntry[]{ marker.getTrace() },
                            "delete the function or call it"
                    );
                    manager.log(issue);
                    System.out.println(issue);
                }
            }
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
        runner.addNamespace(TEST.class);
        runner.loadFile("./lib/native.lua");
        runner.loadFile("./lib/simulation.lua");
        runner.loadFile("./lib/test.lua");
        runner.loadFile(path);
    }
}
