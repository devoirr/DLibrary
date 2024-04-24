package me.kervand.library.gui.api;

import me.kervand.library.gui.GuiBuilder;
import me.kervand.library.gui.Pagination;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public interface CustomInventoryInterface {

    int getSize();

    String getTitle();

    Inventory getInventory();

    boolean isRegistered();
    void unregister();

    int getPage();
    void setPage(int page);

    boolean isRemoveOnClose();

    HashMap<Integer, InputInterface> getItems();
    Pagination<?> getPaginator();

    CustomInventoryInterface setItem(int slot, ItemStack itemStack, InputInterface inputInterface);

    GuiBuilder getBuilder();

    default void open(Player player) {
        if (getInventory() == null) {
            return;
        }

        player.openInventory(getInventory());
    }

    default void refresh(Player player) {
        if (getInventory() == null) {
            return;
        }

        player.updateInventory();
    }

}