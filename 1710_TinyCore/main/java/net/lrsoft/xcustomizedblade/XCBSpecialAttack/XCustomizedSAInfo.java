package net.lrsoft.xcustomizedblade.XCBSpecialAttack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.sun.javafx.geom.Vec3d;

import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.flammpfeil.slashblade.EntityDirectAttackDummy;
import mods.flammpfeil.slashblade.EntityDrive;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityMaximumBetManager;
import mods.flammpfeil.slashblade.entity.EntitySakuraEndManager;
import mods.flammpfeil.slashblade.entity.EntitySlashDimension;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.lrsoft.xcustomizedblade.InfoShow;
import net.lrsoft.xcustomizedblade.XCBUtil.XCBDelayCreator;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
					workDamage(player,target,this.StepDamage[i]);
					workSakuraEnd(world,player,this.SACount[i]);
					break;
				case "LN":
					workDamage(player,target,this.StepDamage[i]);
					workThunder(world,target,this.SACount[i]);
					break;
				case "MB":
					workDamage(player,target,this.StepDamage[i]);
					workMaximumBet(world,player,this.SACount[i]);
					break;
				case "SP":
					workDamage(player,target,this.StepDamage[i]);
					workSpear(world,player,this.SACount[i]);
					break;
				case "EP":
					workExplode(world,player,target,this.StepDamage[i]);
					break;
				case "SD":
					workSlashDimension(world,player,target,this.StepDamage[i]);
					break;
				case "PE":
					workPotionEffect(player,this.SACount[i],(float)this.StepDamage[i]);
					break;
				case "CS":
					workCircleSlash(world,player,this.StepDamage[i]);
					break;
				case "WE":
					workWaveEdge(world,player,this.StepDamage[i]);
					break;
				case "DL":
					try {
						workDelay(world,player,this.SACount[i]);
					}catch(NoSuchMethodError e) {}
					break;
				case "SETD":
					workSetDead(target);
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
	public void workSetDead(EntityLivingBase target) {
		target.setDead();
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
			if(target==null) break;
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
	public void workSlashDimension(World world,EntityPlayer player,EntityLivingBase target,float damage) {
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
	public void workExplode(World world,EntityPlayer player,EntityLivingBase target,float damage) {
		world.createExplosion(player, target.posX, target.posY, target.posZ, damage, false);
	}
	public void workPotionEffect(EntityPlayer player,int type,float time) {
		try {
			player.addPotionEffect(new PotionEffect(type,(int)time,2));
		}catch(Exception e) {
			System.out.println("XCustomizedSA:Invalid PotionEffect ID.");
		}
	}
	public void workCircleSlash(World world,EntityPlayer player,float magicDamage) {
			player.worldObj.playSoundAtEntity(player, "mob.blaze.hit", 0.2F, 0.6F);
            AxisAlignedBB bb = player.boundingBox.copy();
            bb = bb.expand(5.0f, 0.25f, 5.0f);
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, bb, ItemSlashBlade.AttackableSelector);
            for(Entity curEntity : list){
                 StylishRankManager.setNextAttackType(player, StylishRankManager.AttackTypes.CircleSlash);
                 try {
                	 if(player.getItemInUse().getItem() instanceof ItemSlashBlade)
                    	 ((ItemSlashBlade)player.getItemInUse().getItem()).attackTargetEntity(player.getItemInUse(), 
                    			 curEntity, player, true);
                 }catch(Exception error) {}
                    player.onCriticalHit(curEntity);
            }
            for(int i = 0; i < 6;i++){
                EntityDrive entityDrive = new EntityDrive(world, player, magicDamage,false,0);
                entityDrive.setLocationAndAngles(player.posX,
                        player.posY + (double)player.getEyeHeight()/2D,
                        player.posZ,
                        player.rotationYaw + 60 * i /*+ (entityDrive.getRand().nextFloat() - 0.5f) * 60*/,
                        0);//(entityDrive.getRand().nextFloat() - 0.5f) * 60);
                entityDrive.setDriveVector(0.5f);
                entityDrive.setLifeTime(10);
                entityDrive.setIsMultiHit(false);
                entityDrive.setRoll(90.0f /*+ 120 * (entityDrive.getRand().nextFloat() - 0.5f)*/);
                if (entityDrive != null) {
                    world.spawnEntityInWorld(entityDrive);
                }
            }
	}
	public void workWaveEdge(World world,EntityPlayer player,float magicDamage) {
            final float[] speeds = {0.25f,0.3f,0.35f};
            for(int i = 0; i < speeds.length;i++){
                EntityDrive entityDrive = new EntityDrive(world, player, magicDamage,false,0);
                entityDrive.setInitialSpeed(speeds[i]);
                if (entityDrive != null) {
                    world.spawnEntityInWorld(entityDrive);
                }
            }
            EntityDrive entityDrive = new EntityDrive(world, player, magicDamage,true,0);
            entityDrive.setInitialSpeed(0.225f);
            if (entityDrive != null) {
               world.spawnEntityInWorld(entityDrive);
            }
	}
	@SubscribeEvent
	public void init(InitEvent event) {
		ItemSlashBlade.specialAttacks.put(this.SANum, new XCustomizedSpecialAttack(this,attackAll));
	}
}
