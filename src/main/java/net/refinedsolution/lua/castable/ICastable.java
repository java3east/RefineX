package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import org.luaj.vm2.LuaValue;

/**
 * This interface is used to mark classes that can be casted to lua values.
 * @author Java3east
 */
public interface ICastable {
    /**
     * Returns the value of this castable as a lua value.
     * Only required for classes marked with @ACastable(isDirect = true)
     * @return the lua value for this object
     */
    @ACastable.PackField(name = "value")
    LuaValue lua();
}
