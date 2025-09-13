package com.cursee.automessage.core.registry;

import com.cursee.automessage.AutoMessageForge;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.RegisterEvent;

public class ModRegistryForge {

  public static void register(final BusGroup modEventBus) {
    bind(Registries.BLOCK, ModBlocks::register);
    bind(Registries.ITEM, ModItems::register);
  }

  private static <T> void bind(ResourceKey<Registry<T>> registry,
      Consumer<BiConsumer<T, ResourceLocation>> source) {
    RegisterEvent.getBus(AutoMessageForge.BUS_GROUP).addListener((RegisterEvent event) -> {
      if (registry.equals(event.getRegistryKey())) {
        source.accept((t, rl) -> event.register(registry, rl, () -> t));
      }
    });
  }
}
