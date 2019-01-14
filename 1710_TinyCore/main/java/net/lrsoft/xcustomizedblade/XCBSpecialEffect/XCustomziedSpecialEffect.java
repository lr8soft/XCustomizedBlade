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
    public XCustomziedSpecialEffect(XCustomizedSEStandard info) {
    	this.SEInfo=info;
    }
    private boolean useBlade(ItemSlashBlade.ComboSequence sequence){
        if(sequence.useScabbard) return false;
        if(sequence == ItemSlashBlade.ComboSequence.None) return false;
        if(sequence == ItemSlashBlade.ComboSequence.Noutou) return false;
        return true;
    }
    @SubscribeEvent
    public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event){
        if(!SpecialEffects.isPlayer(event.entity)) return; 
        EntityPlayer player = (EntityPlayer) event.entity;
        World world = player.worldObj;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;
        switch (SpecialEffects.isEffective(player,event.blade,this)){
            case None:
                return;
            case NonEffective:
                break;
            case Effective:
            	if(!world.isRemote){
                   	if(!ItemSlashBlade.ProudSoul.tryAdd(tag,-SEInfo.SECost,false)){
                        ItemSlashBlade.damageItem(event.blade, SEInfo.SECost, player);
                    }
                	XCustomizedSEWork SpecialEffect=new XCustomizedSEWork(SEInfo.SEStep,SEInfo.SERuntime,SEInfo.SEDamage);
                	SpecialEffect.workToSE(world,player);
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