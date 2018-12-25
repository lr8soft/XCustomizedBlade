package net.lrsoft.xcustomizedblade.XCBSpeicalAttack;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mods.flammpfeil.slashblade.EntityDirectAttackDummy;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.entity.EntityMaximumBetManager;
import mods.flammpfeil.slashblade.entity.EntitySakuraEndManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class XCustomizedSAInfo {
	public String SAName;
	public String SAStep[];
	public int SACount[];
	public float StepDamage[];
	public int StepCount,SAcost,SANum;
	public XCustomizedSAInfo(String name,int SANum,String info[],int count[],float StepDamage[],int step,int cost) {
		this.SANum=SANum;
		this.SAName=name;
		this.SAStep=info;
		this.SAcost=cost;
		this.SACount=count;
		this.StepCount=step;
		this.StepDamage=StepDamage;
	}
	public void workToEntity(World world,EntityPlayer player,EntityLivingBase target) {
		for(int i=0;i<this.StepCount;i++) {
			switch(this.SAStep[i]) {
				case "PS":
					workPhantomSword(world,player,target,this.StepDamage[i],this.SACount[i]);
					break;
				case "SE":
					target.setHealth(target.getHealth()-this.StepDamage[i]);
					workSakuraEnd(world,player,this.SACount[i]);
					break;
				case "LN":
					target.setHealth(target.getHealth()-this.StepDamage[i]);
					workThunder(world,target,this.SACount[i]);
					break;
				case "MB":
					target.setHealth(target.getHealth()-this.StepDamage[i]);
					workMaximumBet(world,player,this.SACount[i]);
					break;
				case "SP":
					target.setHealth(target.getHealth()-this.StepDamage[i]);
					workSpear(world,player,this.SACount[i]);
					break;
			}
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
                world.spawnEntityInWorld(entityDrive);
            }
        }
	}
	public void workSakuraEnd(World world,EntityPlayer player,int runtime) {
		for(int i=0;i<runtime;i++) {
	        EntitySakuraEndManager entityDA = new EntitySakuraEndManager(world, player);
	        entityDA.setInvisible(true);
	        if (entityDA != null) {
	            world.spawnEntityInWorld(entityDA);
	        }
		}
	}
	public void workThunder(World world,EntityLivingBase target,int runtime) {
		for(int i=0;i<runtime;i++) {
			world.addWeatherEffect(new EntityLightningBolt(world,target.posX,target.posY,target.posZ));
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
	@SubscribeEvent
	public void init(InitEvent event) {
		ItemSlashBlade.specialAttacks.put(this.SANum, new XCustomizedSpecialAttack(this));
	}
}
