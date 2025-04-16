package net.refinedsolution.util.issue;

import org.jetbrains.annotations.NotNull;

/**
 * This interface is used to log issues.
 * @author Java3east
 */
public interface IssueLogger {
    /**
     * Logs a new issue.
     * @param issue the issue to log
     */
    void log(@NotNull Issue issue);
}
