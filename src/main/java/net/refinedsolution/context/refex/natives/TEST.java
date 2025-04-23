package net.refinedsolution.context.refex.natives;

import net.refinedsolution.RefineX;
import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CBool;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.util.Marker;

/**
 * Contains functions for testing purposes.
 * @author Java3east
 */
public class TEST {
    @Native
    public static void RefxSetMarkersEnabled(Runner runner, CBool enable) {
        RefineX.useMarkers = enable.get();
    }

    @Native
    public static void RefxTriggerMarker(Runner runner, CString name) {
        Marker marker = RefineX.markers.get(name.get());
        if (marker != null) {
            marker.setReached();
        }
    }
}
