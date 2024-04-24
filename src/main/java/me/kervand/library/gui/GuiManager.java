package me.kervand.library.gui;

import me.kervand.library.gui.api.CustomInventoryInterface;
import me.kervand.library.gui.api.InputInterface;
import me.kervand.library.listener.AbstractListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiManager {

    private final HashMap<Inventory, CustomInventoryInterface> inventories = new HashMap<>();

    public GuiManager(){

        new AbstractListener(){

            @EventHandler
            public void onClick(InventoryClickEvent event) {

                CustomInventoryInterface inventoryInterface = inventories.get(event.getWhoClicked().getOpenInventory().getTopInventory());

                if (inventoryInterface == null) {
                    return;
                }

                event.setCancelled(true);

                if (event.getClickedInventory() == null || !event.getClickedInventory().equals(inventoryInterface.getInventory())) {
                    return;
                }

                if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                    return;
                }

                InputInterface input = inventoryInterface.getItems().get(event.getSlot());

                if (input != null) {
                    input.onClick((Player) event.getWhoClicked(), event.getClick());
                }

            }

            @EventHandler
            public void onClick(InventoryCloseEvent event) {

                CustomInventoryInterface inventoryInterface = inventories.get(event.getInventory());

                if (inventoryInterface == null) {
                    return;
                }

                if (inventoryInterface.isRemoveOnClose()) {
                    inventoryInterface.unregister();
                }

            }

        };

    }

    public void remove(Inventory inventory) {
        inventories.remove(inventory);
    }

    public void add(Inventory inventory, CustomInventoryInterface inventoryInterface) {
        inventories.put(inventory, inventoryInterface);
    }

}
