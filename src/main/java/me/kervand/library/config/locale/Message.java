package me.kervand.library.config.locale;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private final List<String> lines;
    private final boolean isSingle;

    public Message(List<String> lines) {
        this.lines = new ArrayList<>();
        this.lines.addAll(lines);
        isSingle = lines.size() == 1;
    }

    public Message replace(String key, String replacement) {
        List<String> temp = new ArrayList<>();
        for (String line : lines) {
            temp.add(line.replace(key, replacement));
        }
        lines.clear();
        lines.addAll(temp);
        return this;
    }

    public void send(Player player) {
        if (isSingle) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(lines.get(0)));
        } else {
            for (String message : lines) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(message));
            }
        }
    }

    public void send(CommandSender sender) {
        if (isSingle) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(lines.get(0)));
        } else {
            for (String message : lines) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(message));
            }
        }
    }
}
