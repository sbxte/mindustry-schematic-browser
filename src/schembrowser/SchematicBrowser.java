package schembrowser;

import arc.*;
import arc.files.*;
import arc.input.*;
import arc.scene.event.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.input.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.SettingsMenuDialog;
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
        Migrations.run(this);
        schematicBrowserDialog = new SchematicBrowserDialog(this);

        Events.on(ClientLoadEvent.class, e -> {
            ui.schematics.buttons.button("@schematicbrowser", Icon.host, () -> {
                ui.schematics.hide();
                schematicBrowserDialog.show();
            });
        });

        // Keybind
        // Shift + <schematic menu>
        Core.scene.addListener(new InputListener() { // FINISHME: Make it work for mobile?
            @Override
            public boolean keyDown(InputEvent event, KeyCode keycode) {
                if (!state.isMenu() && !ui.chatfrag.shown() && !ui.schematics.isShown() && !ui.database.isShown()
                        && !ui.consolefrag.shown() && !ui.content.isShown() && !Core.scene.hasKeyboard()
                        && keycode == Core.keybinds.get(Binding.schematic_menu).key && Core.input.shift()
                ) {
                    ui.schematics.hide();
                    schematicBrowserDialog.show();
                }
                return super.keyDown(event, keycode);
            }
        });

        // Settings Menu
        ui.settings.getCategories().add(settingsCategory());
    }

    public static SettingsMenuDialog.SettingsCategory settingsCategory() {
        return new SettingsMenuDialog.SettingsCategory(
                Core.bundle.format("schematicbrowser"),
//                new TextureRegionDrawable(Core.atlas.find("schematic-browser-logo")), // FINISHME: Use sprite logo
                Icon.host,
                SchematicBrowser::settingsMenuTable
        );
    }

    public static void settingsMenuTable(SettingsMenuDialog.SettingsTable table) {
        table.checkPref("schematicbrowserimporttags", true);
    }
}


