package me.lia_lv.fastercart.commands.subcommands;

import me.lia_lv.fastercart.FasterCart;
import me.lia_lv.fastercart.commands.CommandManager;
import me.lia_lv.fastercart.commands.LiaCmd;
import me.lia_lv.fastercart.commands.Perms;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class help implements LiaCmd {

    @Override
    public String getPerm() {
        return Perms.FASTERCART_COMMAND_HELP.getPermission();
    }

    @Override
    public HashMap<Integer, LinkedHashMap<String, String>> getArguments() {
        HashMap<Integer, LinkedHashMap<String, String>> argList = new HashMap<>();
        CommandManager cmdManager = FasterCart.getInstance().getCommandManager();

        Set<Class<?>> classes = cmdManager.getClassesInPackage("me.lia_lv.fastercart.commands.subcommands");
        LinkedHashMap<String, String> args = new LinkedHashMap<>();
        for (Class<?> clazz : classes) {
            if (!LiaCmd.class.isAssignableFrom(clazz)) {
                continue;
            }
            try {
                LiaCmd subCmdClass = (LiaCmd) clazz.getConstructor().newInstance();
                String subCommandName = subCmdClass.getClass().getSimpleName().toLowerCase();
                args.put(subCommandName, subCmdClass.getPerm());
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {}
        }
        if (!args.isEmpty()) {
            argList.put(1, args);
        }

        return argList.isEmpty() ? null : argList;
    }

    @Override
    public List<String> getDescriptionMsg() {
        return FasterCart.getInstance().getLocaleManager().getHelpCommandDescriptions();
    }

    @Override
    public boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args) {
        //TODO: help Command Need Work (getClass and getDescriptionMsg)
        if (args == null) {
            for (String str : this.getDescriptionMsg()) {
                sender.sendMessage(str);
            }
            return true;
        }

        if (args.length >= 1) {
            Class<?> subCmdClass;
            try {
                subCmdClass = Class.forName("me.lia_lv.fastercart.commands.subcommands." + args[0].toLowerCase());
                if (LiaCmd.class.isAssignableFrom(subCmdClass)) {
                    LiaCmd liaSubCmdClass = (LiaCmd) subCmdClass.getConstructor().newInstance();
                    for (String str : liaSubCmdClass.getDescriptionMsg()) {
                        sender.sendMessage(str);
                    }
                } else {
                    sender.sendMessage(plugin.getConfigManager().getPrefix() + plugin.getLocaleManager().getCommandNotExist());
                    return true;
                }
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
                sender.sendMessage(plugin.getConfigManager().getPrefix() + plugin.getLocaleManager().getCommandNotExist());
                return true;
            }
        }

        return true;
    }


}
