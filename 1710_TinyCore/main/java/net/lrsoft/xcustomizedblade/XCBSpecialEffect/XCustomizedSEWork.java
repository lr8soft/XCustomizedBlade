package net.lrsoft.xcustomizedblade.XCBSpecialEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mods.flammpfeil.slashblade.EntityDirectAttackDummy;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.entity.EntityMaximumBetManager;
import mods.flammpfeil.slashblade.entity.EntitySakuraEndManager;
import mods.flammpfeil.slashblade.entity.EntitySlashDimension;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class XCustomizedSEWork {
	private String[] SEStep;
	private int[] SERuntime;
	private double[] SEDamage;
	public XCustomizedSEWork(String step[],int runtime[],double damage[]) {
		this.SEStep=step;this.SERuntime=runtime;this.SEDamage=damage;
	}
	public void workToSE(World world,EntityPlayer player) {
		if(world==null||player==null) return;
		for(int i=0;i<this.SEStep.length;i++) {
			switch(SEStep[i]) {
				case "PS":
					workPhantomSword(world,player,(float)this.SEDamage[i],this.SERuntime[i]);
					break;
				case "SE":
					workSakuraEnd(world,player,this.SERuntime[i]);
					break;
				case "MB":
					workMaximumBet(world,player,this.SERuntime[i]);
					break;
				case "SP":
					workSpear(world,player,this.SERuntime[i]);
					break;
				case "EP":
					workExplode(world,player,this.SEDamage[i]);
					break;
				case "LN":
					workThunder(player,world,this.SERuntime[i]);
					break;
				case "SD":
					workSlashDimension(world,player,(float)this.SEDamage[i]);
					break;
				case "PE":
					workPotionEffect(player,this.SERuntime[i],(float)this.SEDamage[i]);
					break;
			}
		}
	}
	public void workPotionEffect(EntityPlayer player,int type,float time) {
		try {
			player.addPotionEffect(new PotionEffect(type,(int)time,2));
		}catch(Exception e) {
			System.out.println("XCustomizedSE:Invalid PotionEffect ID.");
		}
	}
	public void workSlashDimension(World world,EntityPlayer player,float damage) {
		EntityLivingBase target=(EntityLivingBase) getEntityToWatch(player);
		if(target==null) return;
        EntitySlashDimension dim = new EntitySlashDimension(world, player, damage);
        if (dim != null) {
            Vec3 pos = player.getLookVec();
            {
                float scale = 5;
                pos.xCoord *= scale;
                pos.yCoord *= scale;
                pos.zCoord *= scale;
            }
            pos = pos.addVector(player.posX, player.posY, player.posZ);
            pos = pos.addVector(0, player.getEyeHeight(), 0);
            Vec3 offset = Vec3.createVectorHelper(player.posX, player.posY, player.posZ).addVector(0,player.getEyeHeight(),0);
            Vec3 look = player.getLookVec();
            Vec3 offsettedLook = offset.addVector(look.xCoord * 5, look.yCoord * 5, look.zCoord * 5);
            MovingObjectPosition movingobjectposition = world.rayTraceBlocks(offset, offsettedLook);
            if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                Block block = world.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
                if(block != null && block.isCollidable()){
                    Vec3 tmppos = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
                    if(1 < tmppos.distanceTo(Vec3.createVectorHelper(player.posX, player.posY, player.posZ))){
                        pos = tmppos;
                    }
                }
            }
            dim.setPosition(pos.xCoord, pos.yCoord, pos.zCoord);
            dim.setLifeTime(10);
            dim.setIsSlashDimension(true);
            world.spawnEntityInWorld(dim);
        }
	}
	public void workPhantomSword(World world,EntityPlayer player,float damage,int runtime) {
        for(int i=0;i<runtime;i++) {
        	boolean isBurst = (i % 2 == 0);
        	EntityWitherSword entityDrive = new EntityWitherSword(world, player, damage,(float)Math.tan(3*i));
            if (entityDrive != null) {
                entityDrive.setInterval((int) (1+i*0.05));
                entityDrive.setLifeTime(50);
                int color =isBurst ? -0x40DBDB : -0xD0D0FF;
                entityDrive.setColor(color);
                entityDrive.setBurst(isBurst);
                world.spawnEntityInWorld(entityDrive);
            }
        }
	}
	public void workSakuraEnd(World world,EntityPlayer player,int runtime) {
		for(int i=0;i<runtime;i++) {
	        EntitySakuraEndManager entityDA = new EntitySakuraEndManager(world, player);
	        if (entityDA != null) {
	            world.spawnEntityInWorld(entityDA);
	        }
		}
	}
	public void workMaximumBet(World world,EntityPlayer player,int runtime) {
		for(int i=0;i<runtime;i++) {
		    EntityMaximumBetManager entityDA = new EntityMaximumBetManager(world, player);
	        if (entityDA != null) {
	            world.spawnEntityInWorld(entityDA);
	        }
		}
	}
	public void workThunder(EntityPlayer player,World world,int runtime) {
		Entity target=getEntityToWatch(player);
		if(target==null) return;
		for(int i=0;i<runtime;i++) {
			if(target==null) return;
			world.addWeatherEffect(new EntityLightningBolt(world,target.posX,target.posY,target.posZ));
		}
	}
	public void workSpear(World world,EntityPlayer player,int runtime) {
		double playerDist = 3.5;
		for(int i=0;i<runtime;i++) {
			  if(!player.onGround)
				  playerDist *= 0.35f;
		      player.motionX = -Math.sin(Math.toRadians(player.rotationYaw)) * playerDist;
		      player.motionZ =  Math.cos(Math.toRadians(player.rotationYaw)) * playerDist;
	          player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(),10,0,true));
	          EntityDirectAttackDummy entityDA = new EntityDirectAttackDummy(world, player, false);
	          entityDA.setLifeTime(7);
	          if (entityDA != null) {
	        	  world.spawnEntityInWorld(entityDA);
	          }
		}
	}
	public void workExplode(World world,EntityPlayer player,double damage) {
		Entity target=getEntityToWatch(player);
		if(target==null) return;
		world.createExplosion(player, target.posX, target.posY, target.posZ, (float)damage, false);
	}
	private Entity getEntityToWatch(EntityPlayer player){
	    World world = player.worldObj;
	    Entity target = null;
	    for(int dist = 2; dist < 8; dist+=2){
	        AxisAlignedBB bb = player.boundingBox.copy();
	        Vec3 vec = player.getLookVec();
	        vec = vec.normalize();
	        bb = bb.expand(2.0f, 0.25f, 2.0f);
	        bb = bb.offset(vec.xCoord*(float)dist,vec.yCoord*(float)dist,vec.zCoord*(float)dist);

	        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, bb,ItemSlashBlade.AttackableSelector);
	        float distance = 16.0f;
	        for(Entity curEntity : list){
	            float curDist = curEntity.getDistanceToEntity(player);
	            if(curDist < distance&&curEntity instanceof EntityLivingBase){
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
