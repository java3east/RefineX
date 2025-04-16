package net.refinedsolution.context.refex.natives;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.Client;
import net.refinedsolution.util.Color;
import net.refinedsolution.util.StringUtils;

public class REFX {
    @Native
    public static void REFX_PRINT(Runner runner, CString[] args) {
        StringBuilder sb = new StringBuilder();
        for (CString arg : args) {
            sb.append(arg.get()).append(" ");
        }

        String simName = runner.getSimulator().isPresent() ?
                (
                        "[" + runner.getSimulator().get().getSimulation().getName() + "] [" +
                        ((runner.getSimulator().get() instanceof Client client) ?
                                "CLIENT:" + client.getClientId() : "SERVER") + "]"
                ) : "RefineX";

        simName = StringUtils.fixedXLength(simName, 4);

        System.out.println(Color.WHITE.ascii() + "[" + Color.BLUE.ascii() +  "INFO" + Color.WHITE.ascii() + "] "
                + simName + " " + Color.RESET.ascii() + sb.toString().trim());
    }
}
