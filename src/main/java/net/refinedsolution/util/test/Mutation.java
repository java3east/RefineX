package net.refinedsolution.util.test;

import net.refinedsolution.util.issue.TraceEntry;

import java.util.HashMap;

public record Mutation(HashMap<String, String> files, TraceEntry trace) {
}
