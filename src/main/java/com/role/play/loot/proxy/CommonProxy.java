package com.role.play.loot.proxy;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;

/**
 * @author Martin "Garth" Zander <garth@new-crusader.de>
 * @package RolePlay-Loot
 */

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public void init(FMLInitializationEvent event)
    {
        //
    }

    @SubscribeEvent
    public void fishingLoot(ItemFishedEvent event)
    {
        List<ItemStack> drops = event.getDrops();

        drops.stream().filter(drop -> !drop.getUnlocalizedName().startsWith("item.fish.")
                && !drop.getUnlocalizedName().equals("item.nameTag")
                && !drop.getUnlocalizedName().equals("item.stick")
                && !drop.getUnlocalizedName().equals("tile.waterlily")).forEach(drop -> {
            drop.setCount(0);
        });
        
    }

    @SubscribeEvent
    public void monsterLoot(LivingDropsEvent event)
    {
        String entityName = event.getEntityLiving().getName();
        List<EntityItem> drops = event.getDrops();

        for (EntityItem drop: drops) {
            
            ItemStack dropItem = drop.getItem();
            
            switch (entityName) {
                case "Zombie":
                case "Husk":
                case "Zombie Horse":
                case "Zombie Pigman":
                case "Zombie Villager": {
                    if (!dropItem.getUnlocalizedName().equals("item.rottenFlesh")) {
                        dropItem.setCount(0);
                    }
                    break;
                }
                case "Skeleton":
                case "Stray":
                case "Skeleton Horse": {
                    if (dropItem.getUnlocalizedName().equals("item.arrow")) {
                        Random rand = new Random();
                        dropItem.setCount((rand.nextInt(3) + 1));
                    } else if (!dropItem.getUnlocalizedName().equals("item.bone")) {
                        dropItem.setCount(0);
                    }
                    break;
                }
                case "Wither Skeleton":
                    if (!dropItem.getUnlocalizedName().equals("item.coal")
                            && !dropItem.getUnlocalizedName().equals("item.bone"))
                    {
                        dropItem.setCount(0);
                    }
                    break;
                case "Spider":
                case "Cave Spider": {
                    if (!dropItem.getUnlocalizedName().equals("item.string")
                            && !dropItem.getUnlocalizedName().equals("item.spiderEye"))
                    {
                        dropItem.setCount(0);
                    }
                    break;
                }
                case "Creeper":
                    if (dropItem.getUnlocalizedName().equals("item.sulphur")) {
                        dropItem.setCount(1);
                    } else {
                        dropItem.setCount(0);
                    }
                    break;
                case "Witch":
                    if (!dropItem.getUnlocalizedName().equals("item.stick")
                            && !dropItem.getUnlocalizedName().equals("item.glassBottle")
                            && !dropItem.getUnlocalizedName().equals("item.sugar")
                            //&& !dropItem.getUnlocalizedName().equals("item.yellowDust")
                            //&& !dropItem.getUnlocalizedName().equals("item.redstone")
                            && !dropItem.getUnlocalizedName().equals("item.potion")
                            && !dropItem.getUnlocalizedName().equals("item.sulphur")
                            && !dropItem.getUnlocalizedName().equals("item.spiderEye"))
                    {
                        dropItem.setCount(0);
                    }
                    break;
                case "Vindicator":
                    if (!dropItem.getUnlocalizedName().equals("item.emerald")) {
                        dropItem.setCount(0);
                    }
                    break;
                case "Enderman":
                    if (!dropItem.getUnlocalizedName().equals("item.enderPearl")) {
                        dropItem.setCount(0);
                    }
                    break;
            }
        }
    }
}
