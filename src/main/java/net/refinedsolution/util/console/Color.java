package net.refinedsolution.util.console;

/**
 * A color enum for the console.
 * @author Java3east
 */
public enum Color {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),

    RESET("\u001B[0m")
;
    private final String ascii;

    Color(String ascii) {
        this.ascii = ascii;
    }

    public String ascii() {
        return ascii;
    }
}
