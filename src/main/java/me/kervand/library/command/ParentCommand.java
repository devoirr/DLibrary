package me.kervand.library.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ParentCommand {

    final Map<String, Subcommand> subcommands = new HashMap<>();
    Consumer<CommandSession> noArgs, unknownArg;
    Function<CommandSession, List<String>> completion;
    String permission, description, usage;

    final String name;
    final List<String> aliases = new ArrayList<>();

    final List<Function<CommandSession, Boolean>> requirements = new ArrayList<>();

    public ParentCommand(String name) {
        this.name = name;
    }

    public ParentCommand withUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public ParentCommand withPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public ParentCommand withDescription(String description) {
        this.description = description;
        return this;
    }

    public ParentCommand withRequirement(Function<CommandSession, Boolean> requirement) {
        requirements.add(requirement);
        return this;
    }

    public ParentCommand addSubcommand(String name, Subcommand func) {
        subcommands.put(name, func);
        return this;
    }

    public ParentCommand withAliases(String... aliases) {
        this.aliases.addAll(List.of(aliases));
        return this;
    }

    public ParentCommand withCompletion(Function<CommandSession, List<String>> completion) {
        this.completion = completion;
        return this;
    }

    public ParentCommand noArgsHandler(Consumer<CommandSession> noArgs) {
        this.noArgs = noArgs;
        return this;
    }

    public ParentCommand unknownArgsHandler(Consumer<CommandSession> unknownArgs) {
        unknownArg = unknownArgs;
        return this;
    }

    public abstract ParentCommand prepare();

    public void register() {

        Command command = new Command(name) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {

                CommandSession session = new CommandSession(sender, args);
                for (Function<CommandSession, Boolean> requirement : requirements) {
                    if (!requirement.apply(session)) {
                        return true;
                    }
                }

                if (args.length == 0) {
                    if (noArgs != null) {
                        noArgs.accept(session);
                    }
                } else {
                    Subcommand subcommand = subcommands.get(args[0]);
                    if (subcommand == null) {
                        unknownArg.accept(session);
                    } else {
                        subcommand.perform(new CommandSession(sender, Arrays.copyOfRange(args, 1, args.length)));
                    }
                }

                return true;
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
                if (completion != null) {
                    return completion.apply(new CommandSession(sender, args));
                } else {
                    return Collections.emptyList();
                }
            }
        }.setAliases(aliases);
        if (permission != null) {
            command.setPermission(permission);
        }
        if (description != null) {
            command.setDescription(description);
        }
        if (usage != null) {
            command.setUsage(usage);
        }

        Bukkit.getCommandMap().register("xCore", command);

    }
}
