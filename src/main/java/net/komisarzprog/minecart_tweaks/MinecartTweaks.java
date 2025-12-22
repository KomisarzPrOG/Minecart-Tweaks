package net.komisarzprog.minecart_tweaks;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecartTweaks implements ModInitializer {
	public static final String MOD_ID = "minecart_tweaks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		System.out.println("[MinecartTweaks] loaded!");
	}
}