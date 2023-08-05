package me.lia_lv.fastercart.commands;

import com.google.common.reflect.ClassPath;
import me.lia_lv.fastercart.FasterCart;
import me.lia_lv.fastercart.config.DefaultConfig;
import me.lia_lv.fastercart.languages.LocaleConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandManager implements CommandExecutor {

    private final FasterCart plugin;
    private final DefaultConfig config;
    private final LocaleConfig locale;

    public CommandManager(FasterCart plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfigManager();
        this.locale = this.plugin.getLocaleManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission(Perms.FASTERCART_COMMAND.getPermission())) {
            sender.sendMessage(config.getPrefix() + locale.getPermissionError());
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(argumentsProposalProcessing(sender, locale.getArgumentsProposal()));
            return true;
        }

        LiaCmd subCmdExecutor = this.getInputSubCommand(args[0]);
        if (subCmdExecutor == null) {
            sender.sendMessage(config.getPrefix() + locale.getCommandNotExist());
            return true;
        }

        if (!sender.hasPermission(subCmdExecutor.getPerm())) {
            sender.sendMessage(config.getPrefix() + locale.getPermissionError());
            return true;
        }

        String[] argsForSubCmd;
        if (args.length >= 2) {
            argsForSubCmd = Arrays.copyOfRange(args, 1, args.length);
        } else {
            argsForSubCmd = null;
        }

        return subCmdExecutor.execute(this.plugin, sender, argsForSubCmd);
    }

    public LiaCmd getInputSubCommand(String subCmdName) {
        try {
            Class<?> subCmdClass = Class.forName("me.lia_lv.fastercart.commands.subcommands." + subCmdName.toLowerCase());
            if (LiaCmd.class.isAssignableFrom(subCmdClass)) {
                return (LiaCmd) subCmdClass.getConstructor().newInstance();
            }
        } catch (ClassNotFoundException e) {
            return null;
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException ignored) {}

        return null;
    }

    public Set<Class<?>> getClassesInPackage(String packagePath) {
        ClassLoader loader = this.plugin.getClass().getClassLoader();
        try {
            return ClassPath.from(loader).getAllClasses().stream()
                    .filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packagePath))
                    .map(ClassPath.ClassInfo::load).collect(Collectors.toSet());
        } catch (IOException ex) {
            return Collections.emptySet();
        }
    }

    private String argumentsProposalProcessing(CommandSender sender, String localeMsg) {
        if (!localeMsg.toLowerCase().contains("[proposal_placeholder]")) {
            return localeMsg;
        }
        StringBuilder argProposal = new StringBuilder("[");
        Set<Class<?>> subCommands = getClassesInPackage("me.lia_lv.fastercart.commands.subcommands");
        for (Class<?> clazz : subCommands) {
            if (!LiaCmd.class.isAssignableFrom(clazz)) {
                continue;
            }
            try {
                LiaCmd subCmdClass = (LiaCmd) clazz.getConstructor().newInstance();
                if (sender.hasPermission(subCmdClass.getPerm())) {
                    argProposal.append(subCmdClass.getClass().getSimpleName().toLowerCase()).append("|");
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {}
        }
        argProposal.replace(argProposal.toString().lastIndexOf("|"), argProposal.toString().lastIndexOf("|") + 1, "]");

        return localeMsg.replaceAll("(?i)" + Pattern.quote("[proposal_placeholder]"), argProposal.toString());
    }


}
