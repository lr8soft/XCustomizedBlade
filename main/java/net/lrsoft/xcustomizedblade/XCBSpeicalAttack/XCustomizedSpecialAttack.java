package net.lrsoft.xcustomizedblade.XCBSpeicalAttack;

import java.util.List;

import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class XCustomizedSpecialAttack extends SpecialAttackBase {
	private String speicalAttackName;
	private float distance;
	private boolean useArrow,useDrive,useSakuraEnd;
	private int arrow;private double arrowdamage;
	public XCustomizedSpecialAttack(String name,float dis,boolean useArrow,int arrow,double arrowdamage,
			boolean useDrive,boolean useSakuraEnd) {
		this.speicalAttackName=name;this.arrow=arrow;
		this.useArrow=useArrow;this.useDrive=useDrive;this.useSakuraEnd=useSakuraEnd;
		this.arrowdamage=arrowdamage;this.distance=dis;
	}
	@Override
	public void doSpacialAttack(ItemStack blade, EntityPlayer player) {
        World world = player.worldObj;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
        int entity=ItemSlashBlade.TargetEntityId.get(tag);
        Entity tmp = world.getEntityByID(entity),target=null;
        if(!world.isRemote){
  			target=tmp;
  				if(target == null){
	                target = getEntityToWatch(player);
  				}
        		if(tmp.getDistanceToEntity(player)<distance) {
                	if(useArrow==true) {
                		for(int i=0;i<arrow;i++) {
                			EntityWitherSword attArrow = new EntityWitherSword(world, player, (float)arrowdamage,90.0f);
                			attArrow.setInterval((int) (1+i*0.1));;
                			attArrow.setColor(0x00FFFF);
                			attArrow.setLifeTime(50);
                			attArrow.setTargetEntityId(target.getEntityId());
                			world.spawnEntityInWorld(attArrow);
                		}
                	}
                	if(useDrive==true) {
                		
                	}
        		}
        }
	}

	@Override
	public String toString() {
		return this.speicalAttackName;
	}
	
    private Entity getEntityToWatch(EntityPlayer player){
    	////From SlashBlade,by author flammpfeil
        World world = player.worldObj;
        Entity target = null;
        for(int dist = 2; dist < 20; dist+=2){
            AxisAlignedBB bb = player.boundingBox.copy();
            Vec3 vec = player.getLookVec();
            vec = vec.normalize();
            bb = bb.expand(2.0f, 0.25f, 2.0f);
            bb = bb.offset(vec.xCoord*(float)dist,vec.yCoord*(float)dist,vec.zCoord*(float)dist);

            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, bb, ItemSlashBlade.AttackableSelector);
            float distance = 30.0f;
            for(Entity curEntity : list){
                float curDist = curEntity.getDistanceToEntity(player);
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
