package com.role.play.loot.proxy;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

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
    
    @SubscribeEvent
    public void fillBucket(FillBucketEvent event)
    {
        ItemStack filledBucket = event.getFilledBucket();
        if (filledBucket != null) {
            if (isBucket(filledBucket)) {
                removeLiquid(filledBucket);
            }
        }
        
        ItemStack currentItem = event.getEntityPlayer().inventory.getCurrentItem();
        if (currentItem != null) {
            if (isBucket(currentItem)) {
                removeLiquid(currentItem);
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityItemPickup(EntityItemPickupEvent event)
    {
        ItemStack item = event.getItem().getItem();
        if (isBucket(item)) {
            removeLiquid(item);
        }
    }
    
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        event.player.inventory.mainInventory.stream().filter(this::isBucket).forEach(this::removeLiquid);
    }
    
    private void removeLiquid(ItemStack item)
    {
        if (item.getUnlocalizedName().equals("item.ceramics.clay_bucket")) {
            item.setTagCompound(null);
            return;
        }

        item.setCount(0);
    }
    
    private boolean isBucket(ItemStack item)
    {
        if (item.getUnlocalizedName().equals("item.bucket")
                || item.getUnlocalizedName().equals("item.ceramics.clay_bucket")
                || item.getUnlocalizedName().equals("item.forge.bucketFilled"))
        {
            if (!item.hasTagCompound()) {
                return false;
            }
            
            NBTTagCompound tag;
            
            assert item.getTagCompound() != null;
            if (item.getTagCompound().hasKey("fluids")) {
                tag = item.getTagCompound().getCompoundTag("fluids");
            } else {
                tag = item.getTagCompound();
            }

            if (!tag.hasKey("FluidName")) {
                return false;
            }

            if (tag.getString("FluidName").equals("hot_spring_water")) {
                return true;
            }
        }
        
        return false;
    }
}
