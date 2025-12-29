package net.komisarzprog.minecart_tweaks;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecartTweaks implements ModInitializer {
	public static final String MOD_ID = "minecart_tweaks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final RegistryKey<DamageType> MINECART_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("minecart_tweaks", "minecart"));

	@Override
	public void onInitialize() {
		System.out.println("[MinecartTweaks] loaded!");
	}
}