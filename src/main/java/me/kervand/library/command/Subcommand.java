package me.kervand.library.command;

public interface Subcommand {
    void perform(CommandSession session);
}
