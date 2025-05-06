package net.refinedsolution.lua.castable;

import org.luaj.vm2.LuaValue;

/**
 * This interface is used to mark classes that can be casted to lua values.
 * @author Java3east
 */
public interface ICastable {
    /**
     * Returns the value of this castable as a lua value.
     * @return the lua value for this object
     */
    LuaValue lua();
}
