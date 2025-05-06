package net.refinedsolution.util.issue;

/**
 * A trace entry allows to store a file and a line number, related to errors, warnings
 * or other issues.
 * @author Java3east
 */
public class TraceEntry {
    private String file;
    private int line;

    public TraceEntry(String file, int line) {
        this.file = file;
        this.line = line;
    }

    public TraceEntry() {}

    @Override
    public String toString() {
        return file + ":" + line;
    }
}
