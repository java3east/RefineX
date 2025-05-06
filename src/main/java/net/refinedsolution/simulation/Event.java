package net.refinedsolution.simulation;

public class Event {
    private String name;
    private boolean isNetworked;
    private int source;
    private int destination;

    public Event(String name, int source, int destination, boolean isNetworked) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.isNetworked = isNetworked;
    }

    public Event() {}

    public String getName() {
        return name;
    }

    public boolean isNetworked() {
        return isNetworked;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }
}
