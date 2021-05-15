
package me.mateo.announce.bungee.commands;

import com.google.common.base.Joiner;
import me.mateo.announce.bungee.BungeeAnnounce;
import me.mateo.announce.bungee.utils.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;


public class AnnounceCommand extends Command {

    public AnnounceCommand() {
        super("announce", "nasgarannounce.use",  "bcast", "broadcast", "anunciar");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (args.length == 0) {
                player.sendMessage(Utils.formatComponent("&b&lNasgarAnuncios"));
                player.sendMessage(new TextComponent(""));
                player.sendMessage(Utils.formatComponent("&b/announce <message> &7- Envia un anuncio"));
            } else if (args.length >= 1) {
                if (BungeeAnnounce.getConfig().getBoolean("Formato.ACTIVADO")) {
                    String message = Joiner.on(" ").join(args);

                    for (String format : BungeeAnnounce.getConfig().getStringList("Formato.LINEAS")) {
                        ProxyServer.getInstance().getPlayers().forEach(p -> p.sendMessage(Utils.formatComponent(format.replace("{arrow}", "»").replace("{message}", message))));
                    }
                } else {
                    String message = Joiner.on(" ").join(args);

                    for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                        p.sendMessage(Utils.formatComponent(BungeeAnnounce.getConfig().getString("Prefix") + message));
                    }
                }
            }
        }
    }
}