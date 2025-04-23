package net.refinedsolution.util;

import net.refinedsolution.util.issue.TraceEntry;

/**
 * A marker is a special type of issue, that can be used to mark a specific point in the simulation.
 * @author Java3east
 */
public class Marker {
    private final String name;
    private final String funName;
    private final TraceEntry trace;
    private boolean reached = false;

    public Marker(String name, String funName, TraceEntry trace) {
        this.name = name;
        this.funName = funName;
        this.trace = trace;
    }

    public String getName() {
        return  this.name;
    }

    public String getFunctionName() {
        return funName;
    }

    public void setReached() {
        this.reached = true;
    }

    public boolean isReached() {
        return reached;
    }

    public TraceEntry getTrace() {
        return trace;
    }

    @Override
    public String toString() {
        return "Marker{" +
                "name='" + name + '\'' +
                ", fnName='" + getFunctionName() + '\'' +
                ", reached=" + reached +
                '}';
    }
}
