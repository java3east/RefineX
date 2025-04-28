package net.refinedsolution.context.helix.natives;

import net.refinedsolution.database.Database;
import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CCastable;
import net.refinedsolution.lua.nat.Native;

import java.util.Arrays;
import java.util.Optional;

/**
 * Database related native functions.
 * @author Java3east
 */
public class DATABASE {
    @Native
    public static void rawExecute(Runner runner, String sql, CCastable<?>[] parameters) {
        Optional<Database> db = runner.getDatabase();
        if (db.isEmpty()) throw new IllegalStateException("Database connections are only available in context with simulation.");
        Database database = db.get();
        database.execute(sql, Arrays.stream(parameters).map(CCastable::get).toArray());
    }
}
