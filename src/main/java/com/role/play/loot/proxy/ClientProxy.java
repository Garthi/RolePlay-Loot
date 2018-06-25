package com.role.play.loot.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author Martin "Garth" Zander <garth@new-crusader.de>
 * @package RolePlay-Loot
 */

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        //
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        // 
    }
}
