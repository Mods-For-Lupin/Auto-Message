package com.cursee.automessage;

import com.cursee.automessage.core.registry.ModRegistryForge;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class AutoMessageForge {

    public static IEventBus EVENT_BUS;

    public AutoMessageForge(FMLJavaModLoadingContext context) {
        AutoMessage.init();
        EVENT_BUS = context.getModEventBus();
        ModRegistryForge.register(EVENT_BUS);
        if (FMLEnvironment.dist == Dist.CLIENT) MinecraftForge.EVENT_BUS.addListener(AutoMessageClientForge::new);
        MinecraftForge.EVENT_BUS.addListener(AutoMessageServerForge::new);
        MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
            if (!(event.getEntity() instanceof ServerPlayer serverPlayer) || !(event.getLevel() instanceof ServerLevel serverLevel)) return;

            AutoMessageServer.onFirstJoinLevel(serverPlayer, serverLevel);
            AutoMessageServer.onJoinLevel(serverPlayer, serverLevel);
        });
    }

    @SuppressWarnings("removal")
    public AutoMessageForge() {
        this(FMLJavaModLoadingContext.get());
    }
}