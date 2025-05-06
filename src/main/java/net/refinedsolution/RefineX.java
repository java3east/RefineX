package net.refinedsolution;

import net.refinedsolution.context.refex.natives.TEST;
import net.refinedsolution.lua.LuaInterfaceImpl;
import net.refinedsolution.context.refex.natives.SIM;
import net.refinedsolution.lua.Value;
import net.refinedsolution.util.async.ThreadObserver;
import net.refinedsolution.util.async.ThreadObserverImpl;
import org.luaj.vm2.LuaTable;

import java.io.File;

/**
 * This class handles the initialization of the program.
 * It provides an observer for all the threads that need to finish before the program exits, and handles
 * the entry point.
 * @author Java3east
 */
public class RefineX {
    /**
     * An Observer for all the threads, that need to finish before the program exists.
     */
    private static final ThreadObserver observer = new ThreadObserverImpl();

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

        LuaInterfaceImpl runner = new LuaInterfaceImpl();
        runner.addNamespace(SIM.class);
        runner.addNamespace(TEST.class);
        runner.loadFile("./lib/native.lua");
        runner.loadFile("./lib/simulation.lua");
        runner.loadFile("./lib/test.lua");
        runner.loadFile(path);

        observer.await();
    }
}
