package net.refinedsolution.lua;

import net.refinedsolution.RefineX;
import net.refinedsolution.lua.nat.Call;
import net.refinedsolution.lua.nat.Exists;
import net.refinedsolution.lua.nat.NativeReference;
import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.util.guid.GUID;
import net.refinedsolution.util.async.ThreadObserver;
import net.refinedsolution.util.async.ThreadObserverImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class implements the Runner interface and provides methods to load Lua scripts and call Lua functions.
 * @author Java3east
 */
public class RunnerImpl implements Runner {

    private GUID guid;
    private final Globals globals;
    private final File directory;
    private final List<Class<?>> namespaces = new ArrayList<>();
    private final Optional<Simulator> simulator;
    private final ThreadObserver observer = new ThreadObserverImpl();

    /**
     * Creates a new default runner.
     */
    public RunnerImpl() {
        GUID.register(this);
        this.globals = JsePlatform.debugGlobals();
        this.globals.set("REFX_ID", guid.lua());
        this.globals.set("REFX_FIND", new Exists());
        this.globals.set("REFX_CALL", new Call());
        this.directory = new File(System.getProperty("user.dir"));
        this.simulator = Optional.empty();
        RefineX.observe(this);
    }

    public RunnerImpl(@NotNull Simulator simulator) {
        GUID.register(this);
        this.globals = JsePlatform.debugGlobals();
        this.globals.set("REFX_ID", guid.lua());
        this.globals.set("REFX_FIND", new Exists());
        this.globals.set("REFX_CALL", new Call());
        this.directory = new File(System.getProperty("user.dir"));
        this.simulator = Optional.of(simulator);
        RefineX.observe(this);
    }

    /**
     * Adds a namespace to the list of available namespaces for this runner.
     * @param namespace the class of the namespace
     */
    public void addNamespace(Class<?> namespace) {
        namespaces.add(namespace);
    }

    @Override
    public @NotNull Globals getGlobals() {
        return this.globals;
    }

    @Override
    public void loadStr(@NotNull String string) {
        this.globals.load(string).call();
    }

    @Override
    public void loadFile(@NotNull String filename) {
        this.globals.loadfile(this.directory.getAbsolutePath() + "/" + filename).call();
    }

    @Override
    public @NotNull File getLocalDirectory() {
        return this.directory;
    }

    @Override
    public @NotNull List<Class<?>> getNamespaces() {
        return namespaces;
    }

    @Override
    public @NotNull LuaValue call(@Nullable Object o, @NotNull String mName, @NotNull Varargs varargs) {
        LuaValue[] vals = new LuaValue[varargs.narg()];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = varargs.arg(i + 1);
        }
        return NativeReference.call(this, mName, vals);
    }

    @Override
    public Optional<Simulator> getSimulator() {
        return this.simulator;
    }

    @Override
    public void observe(@NotNull Thread thread) {
        this.observer.observe(thread);
    }

    @Override
    public void observe(@NotNull ThreadObserver observer) {
        this.observer.observe(observer);
    }

    @Override
    public void await() {
        this.observer.await();
    }

    @Override
    public void setGUID(@NotNull GUID guid) {

    }

    @Override
    public @NotNull GUID getGUID() {
        return this.guid;
    }
}
