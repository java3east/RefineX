package net.refinedsolution.util.issue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class IssueManager implements  IssueLog, IssueLogger {
    private final Optional<IssueManager> parent;
    private final List<Issue> issues = new ArrayList<>();

    public IssueManager(@Nullable IssueManager parent) {
        this.parent = Optional.ofNullable(parent);
    }

    public IssueManager() {
        this(null);
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
        parent.ifPresent(parent -> parent.log(issue));
    }
}
