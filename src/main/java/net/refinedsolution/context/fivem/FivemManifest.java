package net.refinedsolution.context.fivem;

import net.refinedsolution.resource.Manifest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class FivemManifest implements Manifest {
    @Override
    public void add(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addAll(@NotNull String key, @NotNull List<String> values) {

    }

    @Override
    public @NotNull String[] getMeta(@NotNull String key) {
        return new String[0];
    }

    @Override
    public @NotNull Optional<String> getMeta(@NotNull String key, int index) {
        return Optional.empty();
    }

    @Override
    public boolean isSet(String key) {
        return false;
    }

    @Override
    public boolean isSet(String key, int index) {
        return false;
    }

    @Override
    public void load(@NotNull String path) {

    }

    @Override
    public boolean verify() {
        return false;
    }
}
