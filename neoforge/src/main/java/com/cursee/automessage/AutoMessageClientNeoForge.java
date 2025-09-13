package com.cursee.automessage;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Consumer;

public class AutoMessageClientNeoForge {

    public AutoMessageClientNeoForge(final IEventBus modEventBus) {

        modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {
            event.enqueueWork(() -> {
                ExampleModClient.init();
            });
        });
    }
}
