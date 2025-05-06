package net.refinedsolution.util.issue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A class that manages issues.
 * It can be used to log issues and retrieve them later.
 * @author Java3east
 */
public class IssueManager implements  IssueLog, IssueLogger {
    private final IssueManager parent;
    private final List<Issue> issues = new ArrayList<>();

    public IssueManager(@NotNull IssueManager parent) {
        this.parent = parent;
    }

    public IssueManager() {
        this(new EmptyIssueManager());
    }

    @Override
    public @NotNull Issue[] getIssues() {
        return issues.toArray(new Issue[0]);
    }

    @Override
    public @NotNull Stream<Issue> stream() {
        return issues.stream();
    }

    @Override
    public void log(@NotNull Issue issue) {
        issues.add(issue);
        parent.log(issue);
    }
}
