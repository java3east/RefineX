package net.refinedsolution.util.test;

import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.util.issue.TraceEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A manager for mutations.
 * @author Java3east
 */
public class MutationManager {
    private final List<Mutator> mutators = new ArrayList<>() {{
        add(new Mutator("==", "~="));
    }};
    private final HashMap<String, String> files;

    /**
     * Creates a new mutation manager
     * @param files the files and their content to be mutated
     */
    public MutationManager(HashMap<String, String> files) {
        this.files = files;
    }

    public Mutation getMutation(int idx) {
        int curr = 0;
        int mutatorIdx = 0;
        int fileIdx = 0;
        while (fileIdx < files.size()) {
            String file = (String) files.keySet().toArray()[fileIdx];
            String content = files.get(file);
            while (mutatorIdx < mutators.size()) {
                Mutator mutator = mutators.get(mutatorIdx);
                mutator.start(content, file);
                if (mutator.hasNext()) {
                    curr++;
                    if (curr == idx) {
                        HashMap<String, String> mutated = new HashMap<>(files);
                        mutated.put(file, mutator.next());
                        return new Mutation(mutated, mutator.trace());
                    }
                    break;
                }
                mutatorIdx++;
            }
            mutatorIdx = 0;
            fileIdx++;
        }
        return new Mutation(files, new TraceEntry().setFile(new CString("")).setLine(new CInt(-1)));
    }
}
