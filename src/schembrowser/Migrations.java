package schembrowser;

import arc.*;
import arc.files.*;
import arc.struct.*;
import arc.util.*;

import static mindustry.Vars.*;

public class Migrations {

    public static final String defaultRepoFrom = "bend-n/design-it";
    public static final String defaultRepoTo = "MindustryDesignIt/design-it";

    public static void run(SchematicBrowser mod) {
        // Change this mod's repo
        if (Core.settings.getString("mod-schematic-browser-repo", "").equals("sbxte/mindustry-schematic-browser")) {
            Core.settings.put("mod-schematic-browser-repo", "MindustryDesignIt/schematic-browser-mod");
        }

        // Remove copies of the browser that follow other ids
        for (var id : new String[]{"scheme-browser", "schematic-browser-v8"}){
            for (var setting: new String[]{"autoupdate", "massupdate", "enabled", "repo"}){
                Core.settings.remove("mod-" + id + "-" + setting);
            }
            var otherMod = mods.list().find(m -> m.name.equals(id));
            if (otherMod != null) {
                Log.info("Schematic Browser: Removing oudated version @", id);
                mods.removeMod(otherMod);
            }
        }

        // Migrate bend-n/design-it repository
        var repos = Seq.with(Core.settings.getString("schematicrepositories", "").split(";"));
        if (repos.contains(defaultRepoFrom)) {
            // Overwrite setting
            repos.replace(repo -> repo.equals(defaultRepoFrom) ? defaultRepoTo : repo);
            Core.settings.put("schematicrepositories", String.join(";", repos));

            // Rename file
            Fi origFile = mod.schematicRepoDirectory.child(defaultRepoFrom.replace("/","") + ".zip");
            Fi nextFile = mod.schematicRepoDirectory.child(defaultRepoTo.replace("/","") + ".zip");
            if (origFile.exists() && !nextFile.exists()) {
                origFile.moveTo(nextFile);
            }
            Log.info("Schematic Browser: Migrated @ to @.", defaultRepoFrom, defaultRepoTo);
        }
    }
}