package me.kervand.library.utility;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Objects;

public class ItemUtility {

    public ItemBuilder getBuilderFromConfig(ConfigurationSection section) throws Exception {

        if (!section.getKeys(false).contains("material")) {
            throw new Exception("No material specified.");
        }

        String materialName = section.getString("material");
        Material material = Material.matchMaterial(Objects.requireNonNull(materialName));

        if (material == null) {
            throw new Exception("Unknown material " + materialName);
        }

        ItemBuilder builder = new ItemBuilder(material);
        if (section.getKeys(false).contains("amount")) {
            builder.setAmount(section.getInt("amount"));
        }

        if (section.getKeys(false).contains("name")) {
            builder.setName(MiniMessage.miniMessage().deserialize(section.getString("name")));
        }

        if (section.getKeys(false).contains("lore")) {
            builder.setLore(section.getStringList("lore").stream().map(
                    string -> MiniMessage.miniMessage().deserialize(string)
            ).toList());
        }

        return builder;

    }

}
