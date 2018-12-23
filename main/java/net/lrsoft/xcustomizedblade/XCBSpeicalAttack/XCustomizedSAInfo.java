package net.lrsoft.xcustomizedblade.XCBSpeicalAttack;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.entity.EntitySakuraEndManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class XCustomizedSAInfo {
	public String SAName;
	public String SAStep[];
	public int SACount[];
	public int StepCount,SAcost,SANum;
	public XCustomizedSAInfo(String name,int SANum,String info[],int count[],int step,int cost) {
		this.SANum=SANum;
		this.SAName=name;
		this.SAStep=info;
		this.SAcost=cost;
		this.SACount=count;
		this.StepCount=step;
	}
	public void workToEntity(World world,EntityPlayer player,EntityLivingBase target) {
		for(int i=0;i<this.StepCount;i++) {
			switch(this.SAStep[i]) {
				case "PS":
					workPhantomSword(world,player,target,5,this.SACount[i]);
					break;
				case "SE":
					workSakuraEnd(world,player,this.SACount[i]);
					break;
			}
		}
	}
	public void workPhantomSword(World world,EntityPlayer player,EntityLivingBase target,float damage,int runtime) {
        boolean isBurst = (runtime % 2 == 0);
        System.out.println("XCSA:PhantomSword shoot");
        for(int i=0;i<runtime;i++) {
        	EntityWitherSword entityDrive = new EntityWitherSword(world, player, damage,90);
            if (entityDrive != null) {
                entityDrive.setInterval((int) (1+runtime*0.1));
                entityDrive.setLifeTime(50);
                int color =isBurst ? -0xFFA07A : -0xFF6347;
                entityDrive.setColor(color);
                entityDrive.setBurst(isBurst);
                entityDrive.setTargetEntityId(target.getEntityId());
                world.spawnEntityInWorld(entityDrive);
            }
        }
	}
	public void workSakuraEnd(World world,EntityPlayer player,int runtime) {
		System.out.println("XCSA:Sakura End");
		for(int i=0;i<runtime;i++) {
	        EntitySakuraEndManager entityDA = new EntitySakuraEndManager(world, player);
	        entityDA.setInvisible(true);
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
