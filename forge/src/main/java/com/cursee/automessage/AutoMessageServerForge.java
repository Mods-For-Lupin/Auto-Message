package com.cursee.automessage;

import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;

public class AutoMessageServerForge {

    public AutoMessageServerForge(final ServerAboutToStartEvent serverAboutToStartEvent) {
        AutoMessageServer.init(serverAboutToStartEvent.getServer());


    }
}
