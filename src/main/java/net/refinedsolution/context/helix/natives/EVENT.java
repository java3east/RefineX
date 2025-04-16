package net.refinedsolution.context.helix.natives;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.Value;
import net.refinedsolution.lua.castable.CCastable;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.Event;
import net.refinedsolution.simulation.EventImpl;
import net.refinedsolution.util.issue.IssueImpl;
import net.refinedsolution.util.issue.IssueLevel;
import net.refinedsolution.util.issue.TraceEntry;
import org.luaj.vm2.LuaTable;

public class EVENT {
    @Native
    public static void REFX_CALL_REMOTE(Runner runner, CString name, CInt source, CInt target, CCastable<?>[] parameters) {
        CCastable<?>[] newParams = new CCastable<?>[parameters.length + 1];
        newParams[0] = source;
        System.arraycopy(parameters, 0, newParams, 1, parameters.length);
        if (source.get() == -1) newParams = parameters;
        Event event = new EventImpl().setName(name).setSource(source).setDestination(target).setData(newParams)
                .setNetworked(!source.get().equals(target.get()));
        int src = source.get();
        boolean ok = false;
        if (src != -1) {
            if (runner.getSimulator().isPresent()) {
                ok = runner.getSimulator().get().getSimulation().getServer().dispatchEvent(event);
            }
        } else {
            if (runner.getSimulator().isPresent()) {
                if (target.get() == -1) {
                    for (int i = 0; i < runner.getSimulator().get().getSimulation().getClients().size(); i++) {
                        if (runner.getSimulator().get().getSimulation().getClients().get(i).dispatchEvent(event))
                            ok = true;
                    }
                }
                for (int i = 0; i < runner.getSimulator().get().getSimulation().getClients().size(); i++) {
                    if (runner.getSimulator().get().getSimulation().getClients().get(i).getClientId() == target.get()) {
                        ok = runner.getSimulator().get().getSimulation().getClients().get(i).dispatchEvent(event);
                    }
                }
            }
        }

        if (!ok) {
            LuaTable info = (LuaTable) runner.getGlobals().get("debug").get("info")
                    .call(Value.of(1), Value.of("Sl"));
            String shortSrc = info.get("short_src").checkjstring();
            int currentLine = info.get("currentline").checkint();
            runner.getSimulator().ifPresent(sim -> sim.getSimulation().log(
                    new IssueImpl(
                            IssueLevel.WARNING,
                            "Event " + name.get() + " is not registered on " + target.get(),
                            sim.getSimulation().getName(), new TraceEntry[]{
                            new TraceEntry().setFile(new CString(shortSrc)).setLine(new CInt(currentLine))
                    }, "register the event, or wait for it to load")
            ));
        }
    }

    @Native
    public static void REFX_CALL_LOCAL(Runner runner, CString name, CCastable<?>[] parameters) {
        Event event = new EventImpl().setName(name).setSource(0).setDestination(0).setData(parameters).setNetworked(false);
        runner.getSimulator().ifPresent(simulator -> simulator.dispatchEvent(event));
    }
}
