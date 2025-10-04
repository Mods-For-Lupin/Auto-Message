package com.cursee.automessage;

import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class AutoMessageClientNeoForge {

  public AutoMessageClientNeoForge(final IEventBus modEventBus) {

    AutoMessageClient.CLIENT = Minecraft.getInstance();
    AutoMessageClient.init(Minecraft.getInstance());

    NeoForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {

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
