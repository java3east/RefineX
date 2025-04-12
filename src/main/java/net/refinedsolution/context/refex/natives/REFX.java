package net.refinedsolution.context.refex.natives;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.Client;
import net.refinedsolution.util.Color;

public class REFX {
    @Native
    public static void REFX_PRINT(Runner runner, CString[] args) {
        StringBuilder sb = new StringBuilder();
        for (CString arg : args) {
            sb.append(arg.get()).append(" ");
        }

        String simName = runner.getSimulator().isPresent() ?
                (
                        (runner.getSimulator().get() instanceof Client client) ?
                                "CLIENT:" + client.getClientId() : "SERVER"
                ) : "RefineX";

        System.out.println(Color.WHITE.ascii() + "[" + Color.BLUE.ascii() +  "INFO" + Color.WHITE.ascii() + "] ["
                + Color.BLUE.ascii() + simName + Color.WHITE.ascii() + "] " + Color.RESET.ascii() + sb.toString().trim());
    }
}
