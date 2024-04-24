package me.kervand.library.gui;

import com.mojang.datafixers.util.Pair;
import me.kervand.library.LibraryPlugin;
import me.kervand.library.gui.api.CustomInventoryInterface;
import me.kervand.library.gui.api.InputInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.function.IntPredicate;

public abstract class PaginatedGuiBuilder extends GuiBuilder {

    protected int page = 0;

    public abstract int getItemsPerPage();
    public abstract Pagination<Pair<ItemStack, InputInterface>> getFillItems();
    public abstract IntPredicate getSlotFillPredicate();

    @Override
    public CustomInventoryInterface build(LibraryPlugin plugin) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getTitle());

        GuiBuilder builder = this;

        String title = getTitle();
        CustomInventoryInterface inventoryInterface = new CustomInventoryInterface() {

            private boolean isRegister = true;
            private final HashMap<Integer, InputInterface> items = new HashMap<>();

            @Override
            public int getPage() {
                return page;
            }

            @Override
            public void setPage(int page1) {
                page = page1;

                System.out.println("Page " + page1);
                System.out.println("Total items: " + getFillItems().size());

                List<Pair<ItemStack, InputInterface>> list = getFillItems().getPage(page1);

                for (int slot = 0; slot < getSize(); slot++) {
                    if (getSlotFillPredicate().test(slot)) {
                        inventory.setItem(slot, null);
                    }
                }
                for (Pair<ItemStack, InputInterface> itemStackInputInterfacePair : list) {
                    int slot = 0;
                    while (slot < getSize()) {
                        if (inventory.getItem(slot) == null && getSlotFillPredicate().test(slot)) {
                            break;
                        }
                        slot++;
                    }
                    setItem(slot, itemStackInputInterfacePair.getFirst(), itemStackInputInterfacePair.getSecond());
                }
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
                return getFillItems();
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

            @Override
            public void open(Player player) {
                if (getInventory() == null) {
                    return;
                }

                setPage(0);
                player.openInventory(getInventory());
            }
        };

        plugin.getGuiManager().add(inventory, inventoryInterface);

        return inventoryInterface;
    }
}
