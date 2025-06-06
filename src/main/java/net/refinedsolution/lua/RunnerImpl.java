package net.refinedsolution.lua;

import net.refinedsolution.database.Database;
import net.refinedsolution.lua.castable.CCastable;
import net.refinedsolution.lua.nat.Call;
import net.refinedsolution.lua.nat.Exists;
import net.refinedsolution.lua.nat.NativeReference;
import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.util.GUID;
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

    private final long id;
    private final Globals globals;
    private final File directory;
    private final List<Class<?>> namespaces = new ArrayList<>();
    private final Optional<Simulator> simulator;

    /**
     * Creates a new default runner.
     */
    public RunnerImpl() {
        this.id = GUID.identify(this);
        this.globals = JsePlatform.debugGlobals();
        this.globals.set("REFX_ID", this.id);
        this.globals.set("REFX_FIND", new Exists());
        this.globals.set("REFX_CALL", new Call());
        this.directory = new File(System.getProperty("user.dir"));
        this.simulator = Optional.empty();
    }

    public RunnerImpl(@NotNull Simulator simulator) {
        this.id = GUID.identify(this);
        this.globals = JsePlatform.debugGlobals();
        this.globals.set("REFX_ID", this.id);
        this.globals.set("REFX_FIND", new Exists());
        this.globals.set("REFX_CALL", new Call());
        this.directory = new File(System.getProperty("user.dir"));
        this.simulator = Optional.of(simulator);
    }

    /**
     * Adds a namespace to the list of available namespaces for this runner.
     * @param namespace the class of the namespace
     */
    public void addNamespace(Class<?> namespace) {
        namespaces.add(namespace);
    }

    @Override
    public long getId() {
        return this.id;
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
    public Optional<Database> getDatabase() {
        if (this.getSimulator().isEmpty()) return Optional.empty();
        return Optional.of(this.getSimulator().get().getSimulation().getDatabase());
    }

    @Override
    public Optional<CCastable<?>> getValue(@NotNull String name, @NotNull Class<? extends CCastable<?>> type) {
        String[] parts = name.split("\\.");
        LuaValue value = this.globals;
        for (String part : parts) {
            value = value.get(part);
            if (value.isnil()) {
                return Optional.empty();
            }
        }
        return Optional.of((CCastable<?>) Value.castTo(value, type));
    }
}
