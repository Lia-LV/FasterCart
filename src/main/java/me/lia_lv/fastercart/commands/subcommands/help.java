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
    public boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args) {
        sender.sendMessage("help command executed."); //TODO: help Command Need Work
        return true;
    }


}
