package me.kervand.library.gui.api;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface InputInterface {
    void onClick(Player clickPlayer, ClickType clickType);
}
