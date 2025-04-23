package net.refinedsolution.util.issue;

import net.refinedsolution.util.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Default implementation of the {@link Issue} interface.
 * @author Java3east
 */
public class IssueImpl implements Issue {
    private final IssueLevel severity;
    private final String message;
    private final String environmentName;
    private final TraceEntry[] trace;
    private final String fixSuggestion;

    public IssueImpl(@NotNull IssueLevel severity, @NotNull String message, @NotNull String environmentName,
                     @NotNull TraceEntry[] trace, String fixSuggestion) {
        this.severity = severity;
        this.message = message;
        this.environmentName = environmentName;
        this.trace = trace;
        this.fixSuggestion = fixSuggestion;
    }

    public IssueImpl(@NotNull IssueLevel severity, @NotNull String message, @NotNull String environmentName,
                     TraceEntry... trace) {
        this(severity, message, environmentName, trace, "?");
    }

    public IssueImpl(@NotNull IssueLevel severity, @NotNull String message) {
        this(severity, message, "Unknown", new TraceEntry[0], "?");
    }

    public IssueImpl(@NotNull IssueLevel level) {
        this(level, "?", "Unknown", new TraceEntry[0], "?");
    }

    @Override
    public @NotNull IssueLevel getSeverity() {
        return this.severity;
    }

    @Override
    public @NotNull String getMessage() {
        return this.message;
    }

    @Override
    public @NotNull String getEnvironmentName() {
        return this.environmentName;
    }

    @Override
    public @NotNull TraceEntry[] getTrace() {
        return this.trace;
    }

    @Override
    public @NotNull String getFixSuggestion() {
        return this.fixSuggestion;
    }

    @Override
    public String toString() {
        return Color.WHITE.ascii() + "[" + this.severity.getColor().ascii() + this.severity + Color.WHITE.ascii() +
                "] [" + this.severity.getColor().ascii() + this.environmentName + Color.WHITE.ascii() + "] " +
                this.severity.getColor().ascii() + this.message + ".\n    Try to " + this.fixSuggestion +
                " (@" + this.trace[0] + ")";
    }
}
