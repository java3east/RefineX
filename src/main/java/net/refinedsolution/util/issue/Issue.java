package net.refinedsolution.util.issue;

import org.jetbrains.annotations.NotNull;

/**
 * Issues represent problems that occur during the simulation. They can be of different types, e.g.: ERROR, WARNING.
 * Each Issue carries a message, their type, the name of the simulation they occurred in and a stack trace or place
 * where a change might be needed.
 * @author Java3east
 */
public interface Issue {
    /**
     * Returns the severity level of this issue.
     * @return the severity level of this issue
     */
    @NotNull IssueLevel getSeverity();

    /**
     * Returns a short description, of what happened, leading to this issue.
     * @return the short description of this issue
     */
    @NotNull String getMessage();

    /**
     * Returns the name of the simulation this issue occurred in.
     * @return the name of the simulation this issue occurred in
     */
    @NotNull String getEnvironmentName();

    /**
     * Returns the trace, which is a list of entries, that describe the steps leading to this issue.
     * @return the trace of this issue
     */
    @NotNull TraceEntry[] getTrace();
}
