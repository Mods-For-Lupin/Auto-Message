package com.cursee.automessage;


import com.cursee.automessage.core.registry.ModRegistryNeoForge;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@Mod(Constants.MOD_ID)
public class AutoMessageNeoForge {

    public static IEventBus EVENT_BUS;

    public AutoMessageNeoForge(final FMLModContainer container) {
        AutoMessage.init();
        EVENT_BUS = container.getEventBus();
        ModRegistryNeoForge.register(EVENT_BUS);
        if (FMLEnvironment.dist == Dist.CLIENT) new AutoMessageClientNeoForge(EVENT_BUS);
        NeoForge.EVENT_BUS.addListener(AutoMessageServerNeoForge::new);
        NeoForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
            if (!(event.getEntity() instanceof ServerPlayer serverPlayer) || !(event.getLevel() instanceof ServerLevel serverLevel)) return;

            AutoMessageServer.onFirstJoinLevel(serverPlayer, serverLevel);
            AutoMessageServer.onJoinLevel(serverPlayer, serverLevel);
        });
    }
}