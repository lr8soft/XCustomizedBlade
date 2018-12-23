package net.lrsoft.xcustomizedblade.XCBSpeicalAttack;

import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityPhantomSwordBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class XCBPhantomSword extends EntityPhantomSwordBase {
    public XCBPhantomSword(World par1World) {
        super(par1World);
    }

    public XCBPhantomSword(World par1World, EntityLivingBase entityLiving, float AttackLevel) {
        super(par1World, entityLiving, AttackLevel);
    }

    public XCBPhantomSword(World par1World, EntityLivingBase entityLiving, float AttackLevel, float roll) {
        super(par1World, entityLiving, AttackLevel, roll);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(8, (byte)0);
    }

    public boolean getBurst(){
        return this.getDataWatcher().getWatchableObjectByte(8) != 0;
    }
    public void setBurst(boolean value){
        this.getDataWatcher().updateObject(8,value ? (byte)1 : (byte)0);
    }

    @Override
    protected void attackEntity(Entity target) {
        if(getBurst())
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, false);

        if(!this.worldObj.isRemote){
            float magicDamage = Math.max(10.0f, AttackLevel);
            target.hurtResistantTime = 0;
            DamageSource ds = new EntityDamageSource("directMagic",this.getThrower()).setDamageBypassesArmor().setMagicDamage();
            target.attackEntityFrom(ds, magicDamage);

            if(blade != null && target instanceof EntityLivingBase && thrower != null && thrower instanceof EntityLivingBase){
                StylishRankManager.setNextAttackType(this.thrower, StylishRankManager.AttackTypes.PhantomSword);
                ((ItemSlashBlade)blade.getItem()).hitEntity(blade,(EntityLivingBase)target,(EntityLivingBase)thrower);

                if (!target.isEntityAlive())
                    ((EntityLivingBase)thrower).heal(1.0F);

                target.motionX = 0;
                target.motionY = 0;
                target.motionZ = 0;
                target.addVelocity(0.0, 0.1D, 0.0);

                ((EntityLivingBase) target).hurtTime = 1;

                ((ItemSlashBlade)blade.getItem()).setDaunting(((EntityLivingBase) target));
            }
        }

        this.setDead();
    }
}

