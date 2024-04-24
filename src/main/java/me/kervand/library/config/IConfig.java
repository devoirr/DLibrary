package me.kervand.library.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.function.Consumer;

public interface IConfig {

    FileConfiguration getHandle();
    void addReloadAction(Consumer<IConfig> action);

    void save();
    void reload();

}
