package me.lia_lv.fastercart.commands;

import me.lia_lv.fastercart.FasterCart;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;

public interface LiaCmd {

    String getPerm();

    HashMap<Integer, LinkedHashMap<String, String>> getArguments();

    boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args);
}
