package net.refinedsolution.context.helix.natives;

import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.lua.Value;
import net.refinedsolution.lua.castable.CCastable;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.Event;
import net.refinedsolution.simulation.EventImpl;
import net.refinedsolution.util.issue.Issue;
import net.refinedsolution.util.issue.Severity;
import net.refinedsolution.util.issue.TraceEntry;
import org.luaj.vm2.LuaTable;

/**
 * Events can be dispatched on simulators.
 * @author Java3east
 */
public class EVENT {
    @Native
    public static void REFX_CALL_REMOTE(LuaInterface runner, CString name, CInt source, CInt target, CCastable<?>[] parameters) {
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
    }

    @Native
    public static void REFX_CALL_LOCAL(LuaInterface runner, CString name, CCastable<?>[] parameters) {
        Event event = new EventImpl().setName(name).setSource(0).setDestination(0).setData(parameters).setNetworked(false);
        runner.getSimulator().ifPresent(simulator -> simulator.dispatchEvent(event));
    }
}
