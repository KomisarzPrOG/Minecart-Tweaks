package net.komisarzprog.minecart_tweaks;

public class MinecartTweaksConfig
{
    public static double furnaceMinecartSpeed = 32D;

    public static double normalMinecartSpeed = 8D;

    public static int furnaceBurnTime = 72000;

    public static int fuelPerItem = 3600;

    public static boolean furnaceMinecartsLoadChunks = true;

    public static boolean allowAllFuels = true;

    public static double getFurnaceMinecartSpeed()
    {
        return Math.max(0.1, furnaceMinecartSpeed * 0.05);
    }

    public static double getNormalMinecartSpeed()
    {
        return Math.max(0.1, normalMinecartSpeed * 0.05);
    }
}
