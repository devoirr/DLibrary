package me.kervand.library;

import lombok.Getter;
import me.kervand.library.gui.GuiManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LibraryPlugin extends JavaPlugin {

    private static @Getter LibraryPlugin instance;
    private @Getter GuiManager guiManager;

    @Override
    public void onEnable() {
        instance = this;
        guiManager = new GuiManager();
    }
}
