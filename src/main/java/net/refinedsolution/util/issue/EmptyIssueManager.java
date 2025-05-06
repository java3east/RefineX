package net.refinedsolution.util.issue;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * An empty implementation of the {@link IssueManager} class.
 * This class does not log any issues and returns an empty list of issues.
 * @author Java3east
 */
public class EmptyIssueManager extends IssueManager {
    @Override
    public @NotNull Issue[] getIssues() {
        return new Issue[0];
    }

    @Override
    public @NotNull Stream<Issue> stream() {
        return Stream.empty();
    }

    @Override
    public void log(@NotNull Issue issue) {
        // Do nothing
    }
}
