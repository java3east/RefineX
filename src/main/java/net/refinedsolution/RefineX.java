package net.refinedsolution;

import net.refinedsolution.context.refex.natives.REFX;
import net.refinedsolution.context.refex.natives.TEST;
import net.refinedsolution.lua.RunnerImpl;
import net.refinedsolution.context.refex.natives.SIM;
import net.refinedsolution.util.test.Marker;
import net.refinedsolution.util.issue.*;

import java.io.File;

/**
 * Main entry point for RefineX
 * @author Java3east
 */
public class RefineX {
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
        runner.addNamespace(REFX.class);
        runner.loadFile("./lib/native.lua");
        runner.loadFile("./lib/simulation.lua");
        runner.loadFile("./lib/test.lua");
        runner.loadFile(path);
    }
}
