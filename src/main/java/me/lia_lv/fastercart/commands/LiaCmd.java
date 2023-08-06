package me.lia_lv.fastercart.commands;

import me.lia_lv.fastercart.FasterCart;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface LiaCmd {

    String getPerm();

    HashMap<Integer, LinkedHashMap<String, String>> getArguments();

    List<String> getDescriptionMsg();

    boolean execute(FasterCart plugin, CommandSender sender, @Nullable String[] args);
}
