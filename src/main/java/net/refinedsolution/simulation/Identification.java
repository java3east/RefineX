package net.refinedsolution.simulation;

import net.refinedsolution.lua.ACastable;

/**
 * Contains the players identifiers and his name
 * @author Java3east
 */
@ACastable
public class Identification {
    private int source = -1;
    private String license = null;
    private String name = null;

    @ACastable.PackField(name = "source")
    public int getSource() {
        return this.source;
    }

    @ACastable.PackField(name = "license")
    public String getLicense() {
        return this.license;
    }

    @ACastable.PackField(name = "name")
    public String getName() {
        return this.name;
    }

    @ACastable.Field(name = "source")
    public void setSource(int source) {
        this.source = source;
    }

    @ACastable.Field(name = "license")
    public void setLicense(String license) {
        this.license = license;
    }

    @ACastable.Field(name = "name")
    public void setName(String name) {
        this.name = name;
    }
}
