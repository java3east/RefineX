package net.refinedsolution.context.refex.natives;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.Client;
import net.refinedsolution.util.Color;
import net.refinedsolution.util.StringUtils;
import net.refinedsolution.util.issue.Issue;
import net.refinedsolution.util.issue.IssueImpl;
import net.refinedsolution.util.issue.IssueLevel;
import net.refinedsolution.util.issue.TraceEntry;

/**
 * Contains RefineX related functions.
 * @author Java3east
 */
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

    @Native
    public static void REFX_ERROR(Runner runner, IssueLevel level, CString msg, CString fix, TraceEntry[] trace) {
        String simName = runner.getSimulator().isPresent() ?
                runner.getSimulator().get().getSimulation().getName() : "RefineX";

        String[] errParts = msg.get().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < errParts.length; i++) {
            sb.append(errParts[i]).append(" ");
        }

        if (fix.get().equals("?") && sb.toString().trim().equals("attempt to call nil"))
            fix = new CString("check for nil");

        Issue issue = new IssueImpl(level, sb.toString().trim(), simName, trace, fix.get());
        System.out.println(issue);

        runner.getSimulator().ifPresent(sim -> sim.getSimulation().log(issue));
    }
}
