package net.lrsoft.xcustomizedblade.XCBItem;

import java.io.File;
import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.named.NamedBladeManager;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import mods.flammpfeil.slashblade.tileentity.DummyTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.enchantment.Enchantment;
public class ItemCustomBlade {
	private String[] recipeItems;
	private JsonArray Enchantment;
	private int sa,standby,bladeduration,color;
	private boolean iswitched,useCustomRecipe;private float bladedamage;
	private String bladename,bladeModel,bladeTexture,showName;
	public ItemCustomBlade(int sa,int standby,int duration,int color,boolean iswithed,float bladedamage,String bladename,
			String showName,String bladeModel,String bladeTexture,JsonArray list,boolean useCustomRecipe,String[] recipeItems) {
		this.sa=sa;this.standby=standby;this.bladeduration=duration;this.iswitched=iswithed;
		this.bladedamage=bladedamage;this.bladeModel=bladeModel;this.bladeTexture=bladeTexture;
		this.bladename=bladename;this.color=color;this.showName=showName;this.Enchantment=list;
		this.recipeItems=recipeItems;this.useCustomRecipe=useCustomRecipe;
	}
	 @SubscribeEvent
	public void Init(InitEvent event) {
	      ItemStack customblade = new ItemStack(SlashBlade.bladeNamed, 1, 0);  
	      NBTTagCompound tag = new NBTTagCompound();
	      customblade.setTagCompound(tag);
	      customblade.setStackDisplayName(showName);
	      ItemSlashBladeNamed.CurrentItemName.set(tag, bladename);
	      ItemSlashBladeNamed.CustomMaxDamage.set(tag, bladeduration);
	      ItemSlashBladeNamed.setBaseAttackModifier(tag, bladedamage);
	      ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(iswitched));
	      ItemSlashBladeNamed.TextureName.set(tag, bladeTexture);
	      ItemSlashBladeNamed.ModelName.set(tag, bladeModel);
	      ItemSlashBladeNamed.SpecialAttackType.set(tag, sa);
	      ItemSlashBladeNamed.StandbyRenderType.set(tag, standby);
	      ItemSlashBladeNamed.SummonedSwordColor.set(tag,color);
	      if(this.Enchantment!=null&&this.Enchantment.size()%2==0) {
	    	  int i,strengthen=0;String temp;
	    	  for(i=0;i<this.Enchantment.size();i+=2) {
	    		try {
	    			 temp=this.Enchantment.get(i).getAsString();
		    		 strengthen=this.Enchantment.get(i+1).getAsInt();
		    		 switch(temp) {
		    		 	case "power":
		    		 		customblade.addEnchantment(Enchantments.POWER,strengthen); break;
		    		 	case "unbreaking":
		    		 		customblade.addEnchantment(Enchantments.UNBREAKING,strengthen); break;
		    		 	case "looting":
		    		 		customblade.addEnchantment(Enchantments.LOOTING,strengthen); break;
		    		 	case "sharpness":
		    		 		customblade.addEnchantment(Enchantments.SHARPNESS,strengthen); break;
		    		 	case "infinity":
		    		 		customblade.addEnchantment(Enchantments.INFINITY,strengthen); break;
		    		 	case "thorns":
		    		 		customblade.addEnchantment(Enchantments.THORNS,strengthen); break;
		    		 	case "knockback":
		    		 		customblade.addEnchantment(Enchantments.KNOCKBACK,strengthen); break;
		    		 	case "baneOfArthropods":
		    		 		customblade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS,strengthen); break;
		    		 	case "blastProtection":
		    		 		customblade.addEnchantment(Enchantments.BLAST_PROTECTION,strengthen); break;
		    		 	case "featherFalling":
		    		 		customblade.addEnchantment(Enchantments.FEATHER_FALLING,strengthen); break;
		    		 	case "fireAspect":
		    		 		customblade.addEnchantment(Enchantments.FIRE_ASPECT,strengthen); break;
		    			case "fireProtection":
		    		 		customblade.addEnchantment(Enchantments.FIRE_PROTECTION,strengthen); break;
		    			case "flame":
		    		 		customblade.addEnchantment(Enchantments.FLAME,strengthen); break;
		    			case "fortune":
		    		 		customblade.addEnchantment(Enchantments.FORTUNE,strengthen); break;
		    			case "projectileProtection":
		    		 		customblade.addEnchantment(Enchantments.PROJECTILE_PROTECTION,strengthen); break;
		    			case "protection":
		    		 		customblade.addEnchantment(Enchantments.PROTECTION,strengthen); break;
		    			case "punch":
		    		 		customblade.addEnchantment(Enchantments.PUNCH,strengthen); break;
		    			case "respiration":
		    		 		customblade.addEnchantment(Enchantments.RESPIRATION,strengthen); break;
		    			case "silkTouch":
		    		 		customblade.addEnchantment(Enchantments.SILK_TOUCH,strengthen); break;
		    			case "smite":
		    		 		customblade.addEnchantment(Enchantments.SMITE,strengthen); break;
		    			case "aquaAffinity":
		    		 		customblade.addEnchantment(Enchantments.AQUA_AFFINITY,strengthen); break;
		    			case "luckOfTheSea":
		    		 		customblade.addEnchantment(Enchantments.LUCK_OF_THE_SEA,strengthen); break;
		    			case "lure":
		    		 		customblade.addEnchantment(Enchantments.LURE,strengthen); break;
		    			case "mending":
		    		 		customblade.addEnchantment(Enchantments.MENDING,strengthen); break;
		    			case "sweeping":
		    		 		customblade.addEnchantment(Enchantments.SWEEPING,strengthen); break;
		    			case "vanishingCurse":
		    		 		customblade.addEnchantment(Enchantments.VANISHING_CURSE,strengthen); break;
		    		 	default:
		    		 		System.out.println("XCustomizedBlade Error:Unknown Enchantment name "+temp);
		    		 }
	    		}catch(NumberFormatException e) {
	    			System.out.println("XCustomizedBlade Error:Unknown strength type!");
	    		}
	    	  }
	      }
    	  ItemStack[] recipeNeed = new ItemStack[10];
    	  if(this.useCustomRecipe==true) {	  
       /* 	  int j;String source = null,name;
        	  try {
        		  recipeNeed[0]=SlashBlade.findItemStack("flammpfeil.slashblade", recipeItems[0],1);
        	  }catch(Exception e) {
        		  recipeNeed[0]=null;  
        	  }  
        	  for(j=1;j<19;j++) {
        		  if(j%2==1) {
        				  try {
        					  source=recipeItems[j];
        				  }catch(Exception e) {
        					  recipeNeed[j]=null;;
        			      }
        		  }else {
        			  name=recipeItems[j];
        			  try {
        				  recipeNeed[j]=SlashBlade.findItemStack(source, recipeItems[j],1);
        			  }catch(NullPointerException e) {
        				  recipeNeed[j]=null;
        			  }
        		  }
        	  }
        	  BladeRecipeInit(this,customblade,recipeNeed);*/
          }
          SlashBlade.registerCustomItemStack(bladename, customblade);
          ItemSlashBladeNamed.NamedBlades.add(bladename);
          NamedBladeManager.registerBladeSoul(tag, bladename);
     }
	 public static void BladeRecipeInit(ItemCustomBlade bladeObject,ItemStack blade,ItemStack recipe[]) {
		 int recipeElement=0,trecipe=0;
		 char[] recipeTemp=new char[3];
		 String[] truerecipe=new String[3];
		 for(int i=1;i<10;i++) {
			 for(int j=0;j<3;j++) {
				 if(recipe[i].equals(null)) {
					 recipeTemp[j]=' ';
				 }else {
					 switch(recipeElement) {
					 	case 0:
					 		recipeTemp[j]='A';break;
					 	case 1:
					 		recipeTemp[j]='B';break;
					 	case 2:
					 		recipeTemp[j]='C';break;
					 	case 3:
					 		recipeTemp[j]='D';break;
					 	case 4:
					 		recipeTemp[j]='E';break;
					 	case 5:
					 		recipeTemp[j]='F';break;
					 	case 6:
					 		recipeTemp[j]='G';break;
					 	case 7:
					 		recipeTemp[j]='H';break;
					 	case 8:
					 		recipeTemp[j]='I';break;
					 }
				 }
				 recipeElement++;
			 }
			 truerecipe[trecipe++]=recipeTemp.toString();
		 }
		 SlashBlade.addRecipe(bladeObject.bladename, new RecipeAwakeBlade(new ResourceLocation(SlashBlade.modid,bladeObject.bladename),blade, recipe[0],
	    		  new Object[]{truerecipe[0],truerecipe[1],truerecipe[2],
	              Character.valueOf('A'),recipe[1],
	              Character.valueOf('B'), recipe[2],
	              Character.valueOf('C'), recipe[3],
	              Character.valueOf('D'), recipe[4],
	              Character.valueOf('E'), recipe[5],
	              Character.valueOf('F'), recipe[6],
	              Character.valueOf('G'), recipe[7],
	              Character.valueOf('H'), recipe[8],
	              Character.valueOf('I'), recipe[9],
	    		  }));
	 }
}
