package me.lia_lv.fastercart.commands;

public enum Perms {

    FASTERCART_COMMAND("fastercart.command"),
    FASTERCART_COMMAND_HELP("fastercart.command.help"),
    FASTERCART_COMMAND_ADMIN_RELOAD("fastercart.command.admin.reload"),
    ;

    private final String permission;

    Perms(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
