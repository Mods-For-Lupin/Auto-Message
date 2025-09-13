package com.cursee.automessage;

import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

public class AutoMessageServerNeoForge {

  public AutoMessageServerNeoForge(final ServerAboutToStartEvent serverAboutToStartEvent) {
    AutoMessageServer.init(serverAboutToStartEvent.getServer());


  }

}
