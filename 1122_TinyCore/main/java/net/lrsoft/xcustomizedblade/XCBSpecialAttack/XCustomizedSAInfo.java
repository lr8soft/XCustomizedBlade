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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
	@SubscribeEvent
	public void init(InitEvent event) {
		ItemSlashBlade.specialAttacks.put(this.SANum, new XCustomizedSpecialAttack(this,attackAll));
	}
}