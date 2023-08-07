package me.lia_lv.fastercart.commands;

public enum Perms {

    FASTERCART_COMMAND("fastercart.command"),
    FASTERCART_COMMAND_HELP("fastercart.command.help"),
    FASTERCART_COMMAND_ADMIN_RELOAD("fastercart.command.admin.reload"),
    FASTERCART_COMMAND_GETITEM("fastercart.command.getitem"),
    FASTERCART_COMMAND_GETITEM_OTHERS("fastercart.command.getitem.others"),
    ;

    private final String permission;

    Perms(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
