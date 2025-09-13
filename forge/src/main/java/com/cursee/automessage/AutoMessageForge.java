package com.cursee.automessage;

import com.cursee.automessage.core.registry.ModRegistryForge;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class AutoMessageForge {

  public static BusGroup BUS_GROUP;

  public AutoMessageForge(FMLJavaModLoadingContext context) {
    AutoMessage.init();
    BUS_GROUP = context.getModBusGroup();
    ModRegistryForge.register(BUS_GROUP);
      if (FMLEnvironment.dist == Dist.CLIENT) {
          ServerAboutToStartEvent.BUS.addListener(AutoMessageClientForge::new);
      }
    ServerAboutToStartEvent.BUS.addListener(AutoMessageServerForge::new);
    EntityJoinLevelEvent.BUS.addListener(event -> {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)
            || !(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

      AutoMessageServer.onFirstJoinLevel(serverPlayer, serverLevel);
      AutoMessageServer.onJoinLevel(serverPlayer, serverLevel);
    });
  }

  @SuppressWarnings("removal")
  public AutoMessageForge() {
    this(FMLJavaModLoadingContext.get());
  }
}