package me.lia_lv.fastercart.languages;

import com.osiris.dyml.exceptions.*;
import me.lia_lv.fastercart.FasterCart;
import me.lia_lv.fastercart.config.Configuration;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocaleConfig {

    private final FasterCart plugin;
    private String CONFIG_FILE_RELOADED;
    private String LOCALE_FILE_RELOADED;
    private String RELOAD_ERROR;
    private String PERMISSION_ERROR;
    private String COMMAND_NOT_EXIST;
    private String ARGUMENTS_PROPOSAL;
    private String INVALID_ARGUMENTS_ERROR;
    private String CONSOLE_MUST_SELECT_TARGET;
    private String INVALID_AMOUNT_ERROR;
    private String GETITEM_MESSAGE;
    private String GETITEM_TO_OTHERS_MESSAGE;
    private String GETITEM_RECEIVED_FROM_OTHERS_MESSAGE;
    private String PLAYER_NOT_FOUND_ERROR;
    private List<String> COMMAND_DESCRIPTIONS_HELP;
    private List<String> COMMAND_DESCRIPTIONS_RELOAD;
    private List<String> COMMAND_DESCRIPTIONS_GETITEM;

    public String getConfigFileReloaded() {
        return ChatColor.translateAlternateColorCodes('&', CONFIG_FILE_RELOADED);
    }

    public String getLocaleFileReloaded() {
        return ChatColor.translateAlternateColorCodes('&', LOCALE_FILE_RELOADED);
    }

    public String getReloadError() {
        return ChatColor.translateAlternateColorCodes('&', RELOAD_ERROR);
    }

    public String getPermissionError() {
        return ChatColor.translateAlternateColorCodes('&', PERMISSION_ERROR);
    }

    public String getCommandNotExist() {
        return ChatColor.translateAlternateColorCodes('&', COMMAND_NOT_EXIST);
    }

    public String getArgumentsProposal() {
        return ChatColor.translateAlternateColorCodes('&', ARGUMENTS_PROPOSAL);
    }

    public String getInvalidArgumentError() {
        return ChatColor.translateAlternateColorCodes('&', INVALID_ARGUMENTS_ERROR);
    }

    public String getConsoleNoSelectTargetError() {
        return ChatColor.translateAlternateColorCodes('&', CONSOLE_MUST_SELECT_TARGET);
    }

    public String getInvalidAmountError() {
        return ChatColor.translateAlternateColorCodes('&', INVALID_AMOUNT_ERROR);
    }

    public String getGetItemMessage() {
        return ChatColor.translateAlternateColorCodes('&', GETITEM_MESSAGE);
    }

    public String getGetitemToOthersMessage() {
        return ChatColor.translateAlternateColorCodes('&', GETITEM_TO_OTHERS_MESSAGE);
    }

    public String getGetitemReceivedFromOthersMessage() {
        return ChatColor.translateAlternateColorCodes('&', GETITEM_RECEIVED_FROM_OTHERS_MESSAGE);
    }

    public String getPlayerNotFoundError() {
        return ChatColor.translateAlternateColorCodes('&', PLAYER_NOT_FOUND_ERROR);
    }

    public List<String> getHelpCommandDescriptions() {
        List<String> results = new ArrayList<>();
        for (String str : COMMAND_DESCRIPTIONS_HELP) {
            results.add(ChatColor.translateAlternateColorCodes('&', str));
        }
        return results;
    }

    public List<String> getReloadCommandDescriptions() {
        List<String> results = new ArrayList<>();
        for (String str : COMMAND_DESCRIPTIONS_RELOAD) {
            results.add(ChatColor.translateAlternateColorCodes('&', str));
        }
        return results;
    }

    public List<String> getGetitemCommandDescriptions() {
        List<String> results = new ArrayList<>();
        for (String str : COMMAND_DESCRIPTIONS_GETITEM) {
            results.add(ChatColor.translateAlternateColorCodes('&', str));
        }
        return results;
    }

    public LocaleConfig(FasterCart plugin) {
        this.plugin = plugin;
    }

    public void load() throws YamlReaderException, IOException, DuplicateKeyException, IllegalListException, YamlWriterException, NotLoadedException, IllegalKeyException {
        String lang = this.plugin.getConfigManager().getLocale().toUpperCase();
        Configuration locale = new Configuration(FasterCart.getInstance(), "Locale", "Locale_" + lang + ".yml");

        locale.addCommentsOnly("Commands", 0, "Commands action messages");
        CONFIG_FILE_RELOADED = locale.get("Commands.ConfigReloaded", "&eThe config file has been reloaded.", 0);
        LOCALE_FILE_RELOADED = locale.get("Commands.LocaleReloaded", "&eThe locale file has been reloaded.", 0);
        RELOAD_ERROR = locale.get("Commands.ReloadError", "&cThere was a problem loading the configs file. Please check the console.", 0);
        PERMISSION_ERROR = locale.get("Commands.PermissionError", "&cYou don't have permission for this command!", 0);
        COMMAND_NOT_EXIST = locale.get("Commands.NotExistCommand", "&cThis command doesn't exist.", 0);
        ARGUMENTS_PROPOSAL = locale.get("Commands.ArgumentsProposal", "&cUsage: /fc [proposal_placeholder]", 0);
        INVALID_ARGUMENTS_ERROR = locale.get("Commands.InvalidArgumentsError", "&cInvalid arguments included in command!", 0);
        CONSOLE_MUST_SELECT_TARGET = locale.get("Commands.ConsoleMustSelectTarget", "&cThe console must select a target when using this command!", 0);
        INVALID_AMOUNT_ERROR = locale.get("Commands.InvalidAmountError", "&cThe amount must be no more than [amount_placeholder].", 0);
        GETITEM_MESSAGE = locale.get("Commands.GetItem", "&eYou got [amount_placeholder] acceleration item(s).", 0);
        GETITEM_TO_OTHERS_MESSAGE = locale.get("Commands.GetItemToOthers", "&eYou gave [amount_placeholder] acceleration item(s) to [player_placeholder].", 0);
        GETITEM_RECEIVED_FROM_OTHERS_MESSAGE = locale.get("Commands.GetItemReceived", "&eYou received [amount_placeholder] acceleration item(s) from [sender_placeholder].", 0);
        PLAYER_NOT_FOUND_ERROR = locale.get("Commands.PlayerNotFoundError", "&cThe player specified doesn't exist.", 0);
        locale.addCommentsOnly("Descriptions", 1, "Descriptions of commands ( Displayed in /fc help )");
        COMMAND_DESCRIPTIONS_HELP = locale.get("Descriptions.Help", new String[]{"&9Usage - /fc help [command]", "&6- Displays the descriptions of [command]"}, 0);
        COMMAND_DESCRIPTIONS_RELOAD = locale.get("Descriptions.Reload", new String[]{"&9Usage - /fc reload", "&6- Reload Config & Locale files."}, 0);
        COMMAND_DESCRIPTIONS_GETITEM = locale.get("Descriptions.GetItem", new String[]{"&9Usage - /fc getitem (amount) (player)", "&6- Get a acceleration item(s).", "&6- If you set a player, give items to that player."}, 0);

        locale.save(true);
    }


}
