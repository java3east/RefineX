package net.refinedsolution.util.issue;

/**
 * The IssueLevel enum represents the severity level of an issue.
 * @author Java3east
 */
public enum IssueLevel {
    /**
     * Errors are critical issues that stopped the simulation / tick.
     * Their message is usually the message of the exception that was thrown.
     */
    ERROR,

    /**
     * A warning is a non-critical issue, that might lead to an error in the future, or to
     * unexpected behavior.
     */
    WARNING,

    /**
     * Infos are "notes" that just inform the user about unusual, or uncommon behaviour,
     * which is not really a problem, but might be worth mentioning and may help the user
     * to understand errors and warnings occurring later on. (e.g. nil detection, empty string
     * as parameter for natives, etc.)
     */
    INFO
}
