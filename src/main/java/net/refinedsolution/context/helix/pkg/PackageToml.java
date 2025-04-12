package net.refinedsolution.context.helix.pkg;

public class PackageToml {
    public Meta meta;
    public Script script;

    @Override
    public String toString() {
        return "PackageToml{" +
                "meta=" + meta +
                ", script=" + script +
                '}';
    }
}
