package me.kervand.library.config;

import lombok.SneakyThrows;
import me.kervand.library.config.locale.MsgKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BaseConfig implements IConfig {

    private final File file;
    private FileConfiguration config;

    private final List<Consumer<IConfig>> reloadActions = new ArrayList<>();

    @SneakyThrows
    public BaseConfig(File file) {
        this.file = file;
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public FileConfiguration getHandle() {
        return config;
    }

    @Override
    public void addReloadAction(Consumer<IConfig> action) {
        reloadActions.add(action);
    }

    @SneakyThrows
    @Override
    public void save() {
        config.save(file);
    }

    @Override
    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
        reloadActions.forEach(action -> action.accept(this));
    }

    public <T extends Enum<T> & MsgKey> LocaleConfig<T> asLocaleConfig() {
        return new LocaleConfig<>(file);
    }
}
