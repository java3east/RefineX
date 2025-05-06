package net.refinedsolution.util.issue;

import org.jetbrains.annotations.NotNull;

/**
 * Default implementation of the {@link Issue} interface.
 * @author Java3east
 */
public record Issue(@NotNull Severity severity, @NotNull TraceEntry trace,
                    @NotNull String message, @NotNull String fixSuggestion) {
}
