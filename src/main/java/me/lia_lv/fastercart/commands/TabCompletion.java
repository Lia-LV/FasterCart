package me.lia_lv.fastercart.commands;

import me.lia_lv.fastercart.FasterCart;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class TabCompletion implements TabCompleter {

    private final FasterCart plugin;

    public TabCompletion(FasterCart plugin) {
        this.plugin = plugin;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completeList = new ArrayList<>();
        CommandManager cmdManager = this.plugin.getCommandManager();
        if (args.length == 1) {
            Set<Class<?>> subCommandsClasses = cmdManager.getClassesInPackage("me.lia_lv.fastercart.commands.subcommands");
            subCommandsClasses.removeIf(subCmdsClasses -> !LiaCmd.class.isAssignableFrom(subCmdsClasses));
            for (Class<?> clazz : subCommandsClasses) {
                LiaCmd subCmdClass = classToLiaCmd(clazz);
                if (subCmdClass == null) {
                    continue;
                }
                if (sender.hasPermission(subCmdClass.getPerm())) {
                    completeList.add(subCmdClass.getClass().getSimpleName().toLowerCase());
                }
            }
            return completeList;
        } else if (args.length >= 2) {
            LiaCmd subCommand = cmdManager.getInputSubCommand(args[0]);
            HashMap<Integer, LinkedHashMap<String, String>> subCommandArguments = subCommand.getArguments();
            if (!sender.hasPermission(subCommand.getPerm()) || subCommandArguments == null) {
                return null;
            }

            LinkedHashMap<String, String> argList = subCommandArguments.get(args.length - 1);
            if (argList == null) {
                return null;
            }

            argList.forEach((subCmdName, permission) -> {
                if (sender.hasPermission(permission)) {
                    completeList.add(subCmdName);
                }
            });
            return completeList;
        }

        return null;
    }

    private LiaCmd classToLiaCmd(Class<?> clazz) {
        try {
            if (!LiaCmd.class.isAssignableFrom(clazz)) {
                return null;
            }
            return (LiaCmd) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            return null;
        }
    }


}
