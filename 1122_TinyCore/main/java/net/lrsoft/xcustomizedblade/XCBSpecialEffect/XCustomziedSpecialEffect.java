package net.lrsoft.xcustomizedblade.XCBSpecialEffect;

import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.IRemovable;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class XCustomziedSpecialEffect implements  ISpecialEffect{
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
    public void onImpactEffectEvent(SlashBladeEvent.ImpactEffectEvent event){

        if(!useBlade(event.sequence)) return;

        if(!SpecialEffects.isPlayer(event.user)) return;
        EntityPlayer player = (EntityPlayer) event.user;
        World world = player.world;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        switch (SpecialEffects.isEffective(player, event.blade, this)){
            case None:
                return;
            case Effective:
            	if(event.target.getRNG().nextInt(2) != 0) return;
                if(!ItemSlashBlade.ProudSoul.tryAdd(tag,-SEInfo.SECost,false)){
                    ItemSlashBlade.damageItem(event.blade, SEInfo.SECost, player);
                 }
                 XCustomizedSEWork SpecialEffect=new XCustomizedSEWork(SEInfo.SEStep,SEInfo.SERuntime,SEInfo.SEDamage);
                 SpecialEffect.workToSE(world,player);
                break;
            case NonEffective:
                if(event.target.getRNG().nextInt(5) != 0) return;
                break;
        }
        player.onEnchantmentCritical(event.target);

    }
    @SubscribeEvent
    public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event){
        if(!SpecialEffects.isPlayer(event.entity)) return; 
        EntityPlayer player = (EntityPlayer) event.entity;
        World world = player.world;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;
        switch (SpecialEffects.isEffective(player,event.blade,this)){
            case None:
                return;
            case NonEffective:
                break;
            case Effective:
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