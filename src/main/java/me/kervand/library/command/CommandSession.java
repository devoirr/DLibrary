package me.kervand.library.command;

import org.bukkit.command.CommandSender;

public record CommandSession(CommandSender sender, String[] args) {

}
