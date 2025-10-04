package com.cursee.automessage;

import com.cursee.automessage.core.message.ClientMessageService;
import com.cursee.automessage.core.message.util.MessageType;
import com.cursee.automessage.core.util.JsonFileHelper;
import com.cursee.automessage.platform.Services;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;

public class AutoMessageClient {

  private static final String AUTOMESSAGE_DATA_DIR_FILEPATH =
      Services.PLATFORM.getGameDirectory() + File.separator + "automessage_data";
  private static final String CLIENT_SERVER_TAGS_FILEPATH =
      Services.PLATFORM.getGameDirectory() + File.separator + "automessage_data" + File.separator
          + "client_server_tags.json";


  public static Minecraft CLIENT;

  public static void init(Minecraft client) {

    CLIENT = client;

    ClientMessageService.load();

    File autoMessageDataDirectory = new File(AUTOMESSAGE_DATA_DIR_FILEPATH);
    if (!autoMessageDataDirectory.isDirectory()) {
      autoMessageDataDirectory.mkdirs();
    }
    JsonFileHelper.addTag(CLIENT_SERVER_TAGS_FILEPATH, "World Name",
        "your_message_identifier.firstJoin");
    JsonFileHelper.addTag(CLIENT_SERVER_TAGS_FILEPATH, "127.0.0.1",
        "your_message_identifier.firstJoin");
  }

  // send when a player joins a level, checking against being tagged as joined already
  public static void onFirstJoinLevel(LocalPlayer player, ClientLevel level) {

    if (level == null || !ClientMessageService.instance.general.enabled) {
      return;
    }

    CLIENT = Minecraft.getInstance();

    ClientMessageService.instance.ON_FIRST_JOIN_MESSAGES.forEach(message -> {

//      String serverName =
//          CLIENT.hasSingleplayerServer() ? Objects.requireNonNull(CLIENT.getSingleplayerServer())
//              .getWorldData().getLevelName() : Objects.requireNonNull(CLIENT.getCurrentServer()).ip;

      String serverName;

      if (CLIENT.hasSingleplayerServer()) {
        MinecraftServer server = Objects.requireNonNull(CLIENT.getSingleplayerServer());
        serverName = server.getWorldPath(LevelResource.PLAYER_DATA_DIR).toString();
        serverName = serverName.replaceAll("(?s)^.*?(?=" + server.getWorldData().getLevelName() + ")", "").replace(File.separatorChar + "playerdata", "");
      } else {
        serverName = Objects.requireNonNull(CLIENT.getCurrentServer()).ip;
      }

      // if pack intro has ever been sent before, ignore it.
      if (message.pack_intro && JsonFileHelper.anyKeyContainsTag(
          new File(CLIENT_SERVER_TAGS_FILEPATH), message.identifier + ".firstJoin")) {
        return;
      }

      if (JsonFileHelper.hasKey(CLIENT_SERVER_TAGS_FILEPATH, serverName)) {
        Constants.LOG.info(
            "Client had key {}, so tags that were already generated are being checked.",
            serverName);
      }

      boolean hasTag = JsonFileHelper.hasTag(CLIENT_SERVER_TAGS_FILEPATH, serverName,
          message.identifier + ".firstJoin");
      if (message.repeats || !hasTag) {

        if (message.type == MessageType.CHAT) {
          if (message.link != null && !message.link.isEmpty()) {
//                  player.sendSystemMessage(Component.literal(
//                      message.text.replace("%player%", player.getDisplayName().getString())
//                          .replace("%link%", message.link)).withStyle(style -> {
//                      return style.withClickEvent(
//                          new ClickEvent(ClickEvent.Action.OPEN_URL, message.link));
//                  }));

            player.displayClientMessage(Component.literal(
                message.text.replace("%player%", player.getDisplayName().getString())
                    .replace("%link%", message.link)).withStyle(style -> {

              URI uri;
              try {
                uri = new URI(message.link);
                return style.withClickEvent(new ClickEvent.OpenUrl(uri));
              } catch (URISyntaxException ignored) {
              }

              return style;
            }), false);
          } else {
//                  player.sendSystemMessage(Component.literal(
//                      message.text.replace("%player%", player.getDisplayName().getString())));
            player.displayClientMessage(Component.literal(
                message.text.replace("%player%", player.getDisplayName().getString())), false);
          }
        } else {
          player.displayClientMessage(Component.literal(
              message.text.replace("%player%", player.getDisplayName().getString())), true);
        }

        if (!message.repeats && !hasTag) {
          JsonFileHelper.addTag(CLIENT_SERVER_TAGS_FILEPATH, serverName,
              message.identifier + ".firstJoin");
        }
      }
    });
  }

  // sent every time a player joins a level, disregarding repeats
  public static void onJoinLevel(LocalPlayer player, ClientLevel level) {
    if (level == null || !ClientMessageService.instance.general.enabled) {
      return;
    }
    ClientMessageService.instance.ON_JOIN_LEVEL_MESSAGES.forEach(message -> {

      if (message.type == MessageType.CHAT) {
        if (message.link != null && !message.link.isEmpty()) {
//                player.sendSystemMessage(Component.literal(
//                    message.text.replace("%player%", player.getDisplayName().getString())
//                        .replace("%link%", message.link)).withStyle(style -> {
//                    return style.withClickEvent(
//                        new ClickEvent(ClickEvent.Action.OPEN_URL, message.link));
//                }));

          player.displayClientMessage(Component.literal(
              message.text.replace("%player%", player.getDisplayName().getString())
                  .replace("%link%", message.link)).withStyle(style -> {

            URI uri;
            try {
              uri = new URI(message.link);
              return style.withClickEvent(new ClickEvent.OpenUrl(uri));
            } catch (URISyntaxException ignored) {
            }

            return style;
          }), false);
        } else {
//                player.sendSystemMessage(Component.literal(
//                    message.text.replace("%player%", player.getDisplayName().getString())));
          player.displayClientMessage(Component.literal(
              message.text.replace("%player%", player.getDisplayName().getString())), false);
        }
      } else {
        player.displayClientMessage(Component.literal(
            message.text.replace("%player%", player.getDisplayName().getString())), true);
      }
    });
  }

  public static void onDeath(Player player) {
    ClientMessageService.instance.ON_DEATH_MESSAGES.forEach(message -> {

      if (message.type == MessageType.CHAT) {
        if (message.link != null && !message.link.isEmpty()) {
          player.displayClientMessage(Component.literal(
              message.text.replace("%player%", player.getDisplayName().getString())
                  .replace("%link%", message.link)).withStyle(style -> {

            URI uri;
            try {
              uri = new URI(message.link);
              return style.withClickEvent(new ClickEvent.OpenUrl(uri));
            } catch (URISyntaxException ignored) {
            }

            return style;
          }), false);
        } else {
          player.displayClientMessage(Component.literal(
              message.text.replace("%player%", player.getDisplayName().getString())), false);
        }
      } else {
        player.displayClientMessage(Component.literal(
            message.text.replace("%player%", player.getDisplayName().getString())), true);
      }
    });
  }

  public static void onRespawn(LocalPlayer player) {
    ClientMessageService.instance.ON_RESPAWN_MESSAGES.forEach(message -> {

      if (message.type == MessageType.CHAT) {
        if (message.link != null && !message.link.isEmpty()) {
          player.displayClientMessage(Component.literal(
              message.text.replace("%player%", player.getDisplayName().getString())
                  .replace("%link%", message.link)).withStyle(style -> {

            URI uri;
            try {
              uri = new URI(message.link);
              return style.withClickEvent(new ClickEvent.OpenUrl(uri));
            } catch (URISyntaxException ignored) {
            }

            return style;
          }), false);
        } else {
          player.displayClientMessage(Component.literal(
              message.text.replace("%player%", player.getDisplayName().getString())), false);
        }
      } else {
        player.displayClientMessage(Component.literal(
            message.text.replace("%player%", player.getDisplayName().getString())), true);
      }
    });
  }
}
