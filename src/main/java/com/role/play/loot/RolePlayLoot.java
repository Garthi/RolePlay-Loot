package com.role.play.loot;

import com.role.play.loot.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = RolePlayLoot.ID,
        name = RolePlayLoot.NAME,
        version = RolePlayLoot.VERSION
)
public class RolePlayLoot
{
    public static final String ID = "roleplayloot";
    public static final String NAME = "RolePlay-Loot";
    public static final String VERSION = "0.1.1";

    @SidedProxy(
            clientSide = "com.role.play.loot.proxy.ClientProxy",
            serverSide = "com.role.play.loot.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.Instance
    public static RolePlayLoot instance;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(proxy);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
}
