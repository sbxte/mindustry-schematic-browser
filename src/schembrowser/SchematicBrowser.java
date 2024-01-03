package schembrowser;

import arc.files.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.mod.*;
import schembrowser.ui.*;

import static mindustry.Vars.*;

public class SchematicBrowser extends Mod {
    public  Fi schematicRepoDirectory;
    public SchematicBrowserDialog schematicBrowserDialog;
    public SchematicBrowser() {
        Log.info("Loaded Schematic Browser");
    }

    @Override
    public void init() {
        schematicRepoDirectory = dataDirectory.child("schematic_repo/");
        schematicBrowserDialog = new SchematicBrowserDialog(this);

        ui.schematics.buttons.button("@schematicbrowser", Icon.host, () -> {
            ui.schematics.hide();
            schematicBrowserDialog.show();
        });
    }
}
