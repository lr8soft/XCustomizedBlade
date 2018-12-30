package net.lrsoft.xcustomizedblade.XCBSpeicalAttack;

import java.util.List;

import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class XCustomizedSpecialAttack extends SpecialAttackBase {
	private XCustomizedSAInfo SAInfo;
	public XCustomizedSpecialAttack(XCustomizedSAInfo info) {
		this.SAInfo=info;
	}

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
        if(!world.isRemote){
            if(!ItemSlashBlade.ProudSoul.tryAdd(tag,SAInfo.SAcost,false)){
                ItemSlashBlade.damageItem(stack, 10, player);
            }
            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();
            Entity target = null;
            int entityId = ItemSlashBlade.TargetEntityId.get(tag);
            if(entityId != 0){
                Entity tmp = world.getEntityByID(entityId);
                if(tmp != null){
                    if(tmp.getDistance(player) < 50.0f)
                        target = tmp;
                }
            }
            if(target == null){
                target = getEntityToWatch(player);
            }
            if(target != null){
                  ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashDim);
                  int cost =120;
                  ItemSlashBlade.damageItem(stack, cost, player);
                  StylishRankManager.setNextAttackType(player, StylishRankManager.AttackTypes.PhantomSword);
                  blade.attackTargetEntity(stack, target, player, true);
                  player.onCriticalHit(target);
                  target.motionX = 0;target.motionY = 0;target.motionZ = 0;
                  if(target instanceof EntityLivingBase){
                      blade.setDaunting((EntityLivingBase)target);
                      ((EntityLivingBase) target).hurtTime = 5;
                      ((EntityLivingBase) target).hurtResistantTime = 5;
                      this.SAInfo.workToEntity(world, player, (EntityLivingBase)target);
                  }else {
                	  this.SAInfo.workMaximumBet(world, player, 2);
                  }
                  
          }
        }
    }

	@Override
	public String toString() {
		return this.SAInfo.SAName;
	}
	////From SlashBlade,by author flammpfeil
    private Entity getEntityToWatch(EntityPlayer player){
    	World world = player.world;
        Entity target = null;
        for(int dist = 2; dist < 20; dist+=2){
            AxisAlignedBB bb = player.getEntityBoundingBox();
            Vec3d vec = player.getLookVec();
            vec = vec.normalize();
            bb = bb.grow(2.0f, 0.25f, 2.0f);
            bb = bb.offset(vec.x*(float)dist,vec.y*(float)dist,vec.z*(float)dist);

            List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());
            float distance = 30.0f;
            for(Entity curEntity : list){
                float curDist = curEntity.getDistance(player);
                if(curDist < distance)
                {
                    target = curEntity;
                    distance = curDist;
                }
            }
            if(target != null)
                break;
        }
        return target;
    }
}

