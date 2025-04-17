package net.refinedsolution.util.test;

import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.util.issue.TraceEntry;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A mutator replaces a given string with another given string, in a given file.
 * It expects at least one test to fail after this modification. It will create one mutation after another.
 * @author Java3east
 */
public class Mutator {
    private final Pattern pattern;
    private final String repl;
    private Matcher matcher;
    private String content;
    private String path;
    private String[] lines;
    private TraceEntry trace;

    /**
     * Creates a new mutator
     * @param pattern the pattern to be replaced
     * @param repl the string to replace with
     */
    public Mutator(Pattern pattern, String repl) {
        this.pattern = pattern;
        this.repl = repl;
    }

    /**
     * Creates a new mutator
     * @param str the string to be replaced
     * @param repl the string to replace with
     */
    public Mutator(String str, String repl) {
        this(Pattern.compile("(?<![~!<>])" + Pattern.quote(str) + "(?![=])"), repl);
    }

    /**
     * Starts the mutator with the given path
     * @param path the path to a file to mutate
     */
    public void start(@NotNull String content, @NotNull String path) {
        this.content = content;
        this.path = path;
        this.matcher = pattern.matcher(content);
        this.lines = content.split("\n");
    }

    /**
     * Returns the trace for the last mutation
     * @return the trace entry
     */
    public TraceEntry trace() {
        return this.trace;
    }

    /**
     * Checks if the mutator can create a new mutation
     * @return true if the mutator can create a new mutation
     */
    public boolean hasNext() {
        if (matcher == null) return false;
        return matcher.find();
    }

    public @NotNull String next() {
        if (matcher == null) throw new IllegalStateException("Mutator not started");
        TraceEntry entry = new TraceEntry();
        StringBuilder modified = new StringBuilder();

        int position = matcher.start();
        int lineNumber = -1;
        for (int i = 0; i < lines.length; i++) {
            if (position < lines[i].length()) {
                lineNumber = i;
                break;
            }
            position -= lines[i].length() + 1;
        }

        matcher.appendReplacement(modified, repl);
        matcher.appendTail(modified);
        entry.setLine(new CInt(lineNumber)).setFile(new CString(this.path));
        this.trace = entry;
        return modified.toString();
    }
}
