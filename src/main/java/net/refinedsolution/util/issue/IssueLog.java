package net.refinedsolution.util.issue;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Issue logs contain a list of logged issues.
 * @author Java3east
 */
public interface IssueLog {
    /**
     * Returns all issues
     * @return the issues
     */
    @NotNull Issue[] getIssues();

    /**
     * Returns the issues as a stream
     * @return the issues as a stream
     */
    @NotNull Stream<Issue> stream();
}
