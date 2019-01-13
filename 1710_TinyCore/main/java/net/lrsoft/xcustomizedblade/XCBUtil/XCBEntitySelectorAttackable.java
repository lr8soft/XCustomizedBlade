package net.lrsoft.xcustomizedblade.XCBUtil;

import com.google.common.base.Predicate;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.entity.EntityGrimGrip;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;

public final class XCBEntitySelectorAttackable implements  IEntitySelector{
	
	public boolean attackAll;
    public XCBEntitySelectorAttackable(boolean input){
    	System.out.println(input);
    	this.attackAll=input;
    }
	@Override
	public boolean isEntityApplicable(Entity input) {
		  boolean result = false;

	        String entityStr = EntityList.getEntityString(input);
	        if(attackAll) {
		        if (((entityStr != null && SlashBlade.manager.attackableTargets.containsKey(entityStr) && SlashBlade.manager.attackableTargets.get(entityStr))
		                || input instanceof EntityDragonPart
		                || input instanceof EntityGrimGrip
		                || input instanceof EntityLivingBase
		        )) {
		        	result = input.isEntityAlive();
		        } 
	        }else {
	        	 if (((entityStr != null && SlashBlade.manager.attackableTargets.containsKey(entityStr) && SlashBlade.manager.attackableTargets.get(entityStr))
			                || input instanceof EntityDragonPart
			                || input instanceof EntityGrimGrip
			        )) {
	        		  result = input.isEntityAlive();
	        	 }
	        }
	        return result;
	}
}