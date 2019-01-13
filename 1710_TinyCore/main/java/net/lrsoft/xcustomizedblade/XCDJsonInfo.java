package net.lrsoft.xcustomizedblade;

import com.google.gson.JsonArray;

public class XCDJsonInfo {
	public JsonArray Enchantment;
	public int sa,standby,bladeduration,color;
	public boolean iswitched;public float bladedamage;
	public String bladename,bladeModel,bladeTexture,showName;
	public XCDJsonInfo(int sa,int standby,int duration,int color,boolean iswithed,float bladedamage,String bladename,
			String showName,String bladeModel,String bladeTexture,JsonArray enchantlist) {
		this.sa=sa;this.standby=standby;this.bladeduration=duration;this.iswitched=iswithed;
		this.bladedamage=bladedamage;this.bladeModel=bladeModel;this.bladeTexture=bladeTexture;
		this.bladename=bladename;this.color=color;this.showName=showName;this.Enchantment=enchantlist;
	}
}
