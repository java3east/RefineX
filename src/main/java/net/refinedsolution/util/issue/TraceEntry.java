package net.refinedsolution.util.issue;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import org.jetbrains.annotations.NotNull;

@ACastable
public class TraceEntry {
    private String file;
    private int line;

    /**
     * Sets the file of the trace entry.
     * @param file the file name
     */
    @ACastable.Field(name = "file")
    public void setFile(@NotNull CString file) {
        this.file = file.get();
    }

    /**
     * Sets the line of the trace entry.
     * @param line the line
     */
    @ACastable.Field(name = "line")
    public void setLine(@NotNull CInt line) {
        this.line = line.get();
    }

    /**
     * Returns the file of the trace entry.
     * @return the file name
     */
    @ACastable.PackField(name = "file")
    public @NotNull CString getFile() {
        return new CString(file);
    }

    /**
     * Returns the line of the trace entry.
     * @return the line
     */
    @ACastable.PackField(name = "line")
    public @NotNull CInt getLine() {
        return new CInt(line);
    }

    @Override
    public String toString() {
        return file + ":" + line;
    }
}
