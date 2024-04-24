package me.kervand.library.gui;

import me.kervand.library.LibraryPlugin;
import me.kervand.library.gui.api.CustomInventoryInterface;
import me.kervand.library.gui.api.InputInterface;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class GuiBuilder {

    public abstract String getTitle();
    public abstract int getSize();
    public abstract boolean removeOnClose();

    public CustomInventoryInterface build(LibraryPlugin plugin) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getTitle());

        GuiBuilder builder = this;

        String title = getTitle();
        CustomInventoryInterface inventoryInterface = new CustomInventoryInterface() {

            private boolean isRegister = true;
            private final HashMap<Integer, InputInterface> items = new HashMap<>();

            @Override
            public int getPage() {
                return 0;
            }

            @Override
            public void setPage(int page) {

            }

            @Override
            public int getSize() {
                return inventory.getSize();
            }

            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public Inventory getInventory() {
                return inventory;
            }

            @Override
            public boolean isRegistered() {
                return isRegister;
            }

            @Override
            public void unregister() {
                isRegister = false;
                plugin.getGuiManager().remove(inventory);
                items.clear();
                inventory.clear();
            }

            @Override
            public boolean isRemoveOnClose() {
                return removeOnClose();
            }

            @Override
            public HashMap<Integer, InputInterface> getItems() {
                return items;
            }

            @Override
            public Pagination<?> getPaginator() {
                return null;
            }

            @Override
            public CustomInventoryInterface setItem(int slot, ItemStack itemStack, InputInterface inputInterface) {
                inventory.setItem(slot, itemStack);
                items.put(slot, inputInterface);
                return this;
            }

            @Override
            public GuiBuilder getBuilder() {
                return builder;
            }
        };

        plugin.getGuiManager().add(inventory, inventoryInterface);

        return inventoryInterface;
    }
}
