package net.lrsoft.xcustomizedblade.XCBSpecialEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mods.flammpfeil.slashblade.entity.EntityMaximumBetManager;
import mods.flammpfeil.slashblade.entity.EntitySakuraEndManager;
import mods.flammpfeil.slashblade.entity.EntitySlashDimension;
import mods.flammpfeil.slashblade.entity.EntitySpearManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class XCustomizedSEWork {
	private String[] SEStep;
	private int[] SERuntime;
	private double[] SEDamage;
	public XCustomizedSEWork(String step[],int runtime[],double damage[]) {
		this.SEStep=step;this.SERuntime=runtime;this.SEDamage=damage;
	}
	public void workToSE(World world,EntityPlayer player) {
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
		switch(type) {
			case 0://blindness
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS,(int)time,2));
				break;
			case 1://ABSORPTION
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION,(int)time,2));
				break;
			case 2://STRENGTH
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,(int)time,2));
				break;
			case 3://digSlowdown
				player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE,(int)time,2));
				break;
			case 4://digSpeed
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED,(int)time,2));
				break;
			case 5://fireResistance
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,(int)time,2));
				break;
			case 6://harm
				player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE,(int)time,2));
				break;
			case 7://heal
				player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST,(int)time,2));
				break;
			case 8://hunger
				player.addPotionEffect(new PotionEffect(MobEffects.HUNGER,(int)time,2));
				break;
			case 9://invisibility
				player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY,(int)time,2));
				break;
			case 10://jump
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST,(int)time,2));
				break;
			case 11://moveSlowdown
				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,(int)time,2));
				break;
			case 12://moveSpeed
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED,(int)time,2));
				break;
			case 13://nightVision
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,(int)time,2));
				break;
			case 14://poison
				player.addPotionEffect(new PotionEffect(MobEffects.POISON,(int)time,2));
				break;
			case 15://regeneration
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,(int)time,2));
				break;
			case 16://resistance
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,(int)time,2));
				break;
			case 17://waterBreathing
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING,(int)time,2));
				break;
			case 18://weakness
				player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,(int)time,2));
				break;
			case 19://wither
				player.addPotionEffect(new PotionEffect(MobEffects.WITHER,(int)time,2));
				break;
		}
	}
	public void workSlashDimension(World world,EntityPlayer player,float damage) {
		Entity target=getEntityToWatch(player);
		if(target==null) return;
		EntitySlashDimension dim = new EntitySlashDimension(world, player, damage);
        if (dim != null) {
            dim.setPosition(target.posX, target.posY, target.posZ);
            dim.setLifeTime(10);
            dim.setIsSlashDimension(true);
            world.spawnEntity(dim);
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
                world.spawnEntity(entityDrive);
            }
        }
	}
	public void workSakuraEnd(World world,EntityPlayer player,int runtime) {
		for(int i=0;i<runtime;i++) {
	        EntitySakuraEndManager entityDA = new EntitySakuraEndManager(world, player);
	        entityDA.setInvisible(true);
	        if (entityDA != null) {
	            world.spawnEntity(entityDA);
	        }
		}
	}
	public void workMaximumBet(World world,EntityPlayer player,int runtime) {
		for(int i=0;i<runtime;i++) {
		    EntityMaximumBetManager entityDA = new EntityMaximumBetManager(world, player);
	        if (entityDA != null) {
	            world.spawnEntity(entityDA);
	        }
		}
	}
	public void workThunder(EntityPlayer player,World world,int runtime) {
		Entity target=getEntityToWatch(player);
		if(target==null) return;
		for(int i=0;i<runtime;i++) {
			world.addWeatherEffect(new EntityLightningBolt(world,target.posX,target.posY,target.posZ,false));
		}
	}
	public void workSpear(World world,EntityPlayer player,int runtime) {
		double playerDist = 3.5;
		for(int i=0;i<runtime;i++) {
			  if(!player.onGround)
				  playerDist *= 0.35f;
		      player.motionX = -Math.sin(Math.toRadians(player.rotationYaw)) * playerDist;
		      player.motionZ =  Math.cos(Math.toRadians(player.rotationYaw)) * playerDist;
	          EntitySpearManager entityDA = new EntitySpearManager(world, player, false);
	          entityDA.setLifeTime(7);
	          if (entityDA != null) {
	              world.spawnEntity(entityDA);
	          }
		}
	}
	public void workExplode(World world,EntityPlayer player,double damage) {
		Entity target=getEntityToWatch(player);
		if(target==null) return;
		world.createExplosion(player, target.posX, target.posY, target.posZ, (float)damage, false);
	}
	 private Entity getEntityToWatch(EntityPlayer player){
	    	World world = player.world;
	        Entity target = null;
	        for(int dist = 2; dist < 20; dist+=2){
	            AxisAlignedBB bb = player.getEntityBoundingBox();
	            Vec3d vec = player.getLookVec();
	            vec = vec.normalize();
	            bb = bb.grow(2.0f, 0.25f, 2.0f);
	            bb = bb.offset(vec.x*(float)dist,vec.y*(float)dist,vec.z*(float)dist);

	            List<Entity> list= world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());
	            float distance = 30.0f;
	            for(Entity curEntity : list){
	                float curDist = curEntity.getDistance(player);
	                if(curDist < distance&&curEntity instanceof EntityLivingBase)
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
