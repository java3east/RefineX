package net.refinedsolution.context.helix.natives;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CCastable;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.Event;
import net.refinedsolution.simulation.EventImpl;

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
        if (src != -1) runner.getSimulator().ifPresent(simulator -> simulator.getSimulation().getServer().dispatchEvent(event));
        else runner.getSimulator().flatMap(simulator -> simulator.getSimulation().getClient(target.get()))
                .ifPresent(client -> client.dispatchEvent(event));
    }
}
