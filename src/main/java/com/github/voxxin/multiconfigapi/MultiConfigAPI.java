package com.github.voxxin.multiconfigapi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;


@Environment(EnvType.CLIENT)
public class MultiConfigAPI implements ClientModInitializer {
    public static final String MODID = "multiconfigapi";
    public static final String MODNAME = "Multi-Config-API";
    public static final Logger LOGGER = LogManager.getLogger(MODNAME);
    public static final String MODVERSION = Objects.requireNonNull(FabricLoader.getInstance().getModContainer(MODID).orElse(null)).getMetadata().getVersion().getFriendlyString();


    @Override
    public void onInitializeClient() {
    }
}
