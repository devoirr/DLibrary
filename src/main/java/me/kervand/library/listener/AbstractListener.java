package me.kervand.library.listener;

import me.kervand.library.LibraryPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class AbstractListener implements Listener {

    public AbstractListener() {
        Bukkit.getPluginManager().registerEvents(this, LibraryPlugin.getInstance());
    }
}
