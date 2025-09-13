package com.cursee.automessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;

public class AutoMessageClientForge {

  public AutoMessageClientForge(final ServerAboutToStartEvent serverAboutToStartEvent) {
    AutoMessageClient.init(Minecraft.getInstance());

    EntityJoinLevelEvent.BUS.addListener(event -> {

      Entity entity = event.getEntity();
      Level level = event.getLevel();
        if (!(entity instanceof LocalPlayer localPlayer)
            || !(level instanceof ClientLevel clientLevel)) {
            return;
        }
      AutoMessageClient.onFirstJoinLevel(localPlayer, clientLevel);
      AutoMessageClient.onJoinLevel(localPlayer, clientLevel);
    });
  }
}
