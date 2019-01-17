package net.lrsoft.xcustomizedblade.XCBSpecialAttack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import mods.flammpfeil.slashblade.entity.EntityMaximumBetManager;
import mods.flammpfeil.slashblade.entity.EntitySakuraEndManager;
import mods.flammpfeil.slashblade.entity.EntitySlashDimension;
import mods.flammpfeil.slashblade.entity.EntitySpearManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.lrsoft.xcustomizedblade.InfoShow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class XCustomizedSAInfo {
	public String SAName;
	public String SAStep[];
	public int SACount[];
	public float StepDamage[];
	public int StepCount,SAcost,SANum;
	public boolean attackAll;
	public XCustomizedSAInfo(String name,int SANum,String info[],int count[],float StepDamage[],int step,int cost,
			boolean attackAll) {
		this.SANum=SANum;
		this.SAName=name;
		this.SAStep=info;
		this.SAcost=cost;
		this.SACount=count;
		this.StepCount=step;
		this.StepDamage=StepDamage;
		this.attackAll=attackAll;
	}
	public void workToEntity(World world,EntityPlayer player,EntityLivingBase target) {
		for(int i=0;i<this.StepCount;i++) {
			switch(this.SAStep[i]) {
			case "PS":
				workPhantomSword(world,player,target,this.StepDamage[i],this.SACount[i]);
				break;
			case "SE":
				workDamage(player,target,StepDamage[i]);
				workSakuraEnd(world,player,this.SACount[i]);
				break;
			case "LN":
				workDamage(player,target,StepDamage[i]);
				workThunder(world,target,this.SACount[i]);
				break;
			case "MB":
				workDamage(player,target,StepDamage[i]);
				workMaximumBet(world,player,this.SACount[i]);
				break;
			case "SP":
				workDamage(player,target,StepDamage[i]);
				workSpear(world,player,this.SACount[i]);
				break;
			case "EP":
				workExplode(world,player,target,this.StepDamage[i]);
				break;
			case "SD":
				workSlashDimension(world,player,target,this.StepDamage[i]);
				break;
			case "DL":
				try {
					workDelay(world,player,this.SACount[i]);
				}catch(NoSuchMethodError e) {}
				break;
			case "PE":
				workPotionEffect(player,this.SACount[i],(float)this.StepDamage[i]);
				break;
			}
		}
	}
	@SideOnly(Side.CLIENT)
	public void workDelay(World world,EntityPlayer player,int time) {
		try {
			FileOutputStream out=new FileOutputStream(InfoShow.getNowPath()+"/XCB_DELAY_TEMP.dat");
			for(int i=0;i<time;i++) {
				out.write(String.valueOf(Math.sin(i*Math.cos(i+Math.acos(i))+Math.PI)).getBytes());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 File file=new File(InfoShow.getNowPath()+"/XCB_DELAY_TEMP.dat");
         if(file.exists())
             file.delete();
	}
	public void workDamage(EntityPlayer player,EntityLivingBase target,float damage) {
		try{
		      target.attackEntityFrom(DamageSource.causePlayerDamage(player),damage);
		}catch(Exception k){
			  target.setHealth(target.getHealth()-damage);
		}
	}
	public void workPhantomSword(World world,EntityPlayer player,EntityLivingBase target,float damage,int runtime) {
        for(int i=0;i<runtime;i++) {
        	boolean isBurst = (i % 2 == 0);
        	EntityWitherSword entityDrive = new EntityWitherSword(world, player, damage,(float)Math.tan(3*i));
            if (entityDrive != null) {
                entityDrive.setInterval((int) (1+i*0.05));
                entityDrive.setLifeTime(50);
                int color =isBurst ? -0x40DBDB : -0xD0D0FF;
                entityDrive.setColor(color);
                entityDrive.setBurst(isBurst);
                entityDrive.setTargetEntityId(target.getEntityId());
                entityDrive.setPosition(player.posX+Math.cos(3*i)+3*Math.cos(i*Math.PI), player.posY+Math.tan(3*i), player.posZ+Math.cos(3*i)+3*Math.cos(i*Math.PI));
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
	public void workThunder(World world,EntityLivingBase target,int runtime) {
		for(int i=0;i<runtime;i++) {
			world.addWeatherEffect(new EntityLightningBolt(world,target.posX,target.posY,target.posZ,false));
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
	public void workSlashDimension(World world,EntityPlayer player,EntityLivingBase target,float damage) {
        EntitySlashDimension dim = new EntitySlashDimension(world, player, damage);
        if (dim != null) {
            dim.setPosition(target.posX, target.posY, target.posZ);
            dim.setLifeTime(10);
            dim.setIsSlashDimension(true);
            world.spawnEntity(dim);
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
	public void workExplode(World world,EntityPlayer player,EntityLivingBase target,float damage) {
		world.createExplosion(player, target.posX, target.posY, target.posZ, damage, false);
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
	@SubscribeEvent
	public void init(InitEvent event) {
		ItemSlashBlade.specialAttacks.put(this.SANum, new XCustomizedSpecialAttack(this,attackAll));
	}
}
