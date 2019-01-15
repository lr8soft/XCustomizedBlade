package net.lrsoft.xcustomizedblade.XCBSpecialEffect;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class XCustomziedSpecialEffect implements ISpecialEffect{
    private XCustomizedSEStandard SEInfo;
    private int interval=0;
    public XCustomziedSpecialEffect(XCustomizedSEStandard info) {
    	this.SEInfo=info;
    }
    private boolean useBlade(ItemSlashBlade.ComboSequence sequence){
        if(sequence.useScabbard) return false;
        if(sequence == ItemSlashBlade.ComboSequence.None) return false;
        if(sequence == ItemSlashBlade.ComboSequence.Noutou) return false;
        if(sequence == ItemSlashBlade.ComboSequence.SlashEdge) return false;
        if(sequence == ItemSlashBlade.ComboSequence.ReturnEdge) return false;
        return true;
    }
    @SubscribeEvent
    public void onImpactEffectEvent(SlashBladeEvent.ImpactEffectEvent event){

        if(!useBlade(event.sequence)) return;

        if(!SpecialEffects.isPlayer(event.user)) return;
        EntityPlayer player = (EntityPlayer) event.user;
        World world = player.worldObj;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        switch (SpecialEffects.isEffective(player, event.blade, this)){
            case None:
                return;
            case Effective:
                break;
            case NonEffective:
            	ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.None);
                break;
        }
        player.onEnchantmentCritical(event.target);

    }

    @SubscribeEvent
    public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event){
        if(!SpecialEffects.isPlayer(event.entity)) return; 
        EntityPlayer player = (EntityPlayer) event.entity;
        World world = player.worldObj;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        boolean ret=useBlade(ItemSlashBlade.getComboSequence(tag));
        if(!ret) return;
        switch (SpecialEffects.isEffective(player,event.blade,this)){
            case None:
            	return;
            case NonEffective:
            	return;
            case Effective:
            	if(interval<=0) {
    	            if(!ItemSlashBlade.ProudSoul.tryAdd(tag,-SEInfo.SECost,false)){
  	                  ItemSlashBlade.damageItem(event.blade, SEInfo.SECost, player);
    	            }
    	            XCustomizedSEWork SpecialEffect=new XCustomizedSEWork(SEInfo.SEStep,SEInfo.SERuntime,SEInfo.SEDamage);
    	            SpecialEffect.workToSE(world,player);
    	            interval+=5;
            	}else {
            		interval--;
            	}

	            break;
        }

    }
	@Override
	public int getDefaultRequiredLevel() {
		return SEInfo.SELevel;
	}
	@Override
	public String getEffectKey() {
		return SEInfo.SEName;
	}
	@Override
	public void register() {
		 SlashBladeHooks.EventBus.register(this);
	}
}	