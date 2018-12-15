package net.lrsoft.xcustomizedblade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.lrsoft.xcustomizedblade.InfoShow;
public class ModInit {
	private String path;
	private String STDJsonConfig;
	public ModInit() {
		this.path=InfoShow.getNowPath()+"/XCustomizedBlade.json";
		this.STDJsonConfig="{\r\n" + 
				"  \"XCustomizedBladeVER\": 0.8,\r\n" + 
				"  \"CustomizedRecipe\": true,\r\n" + 
				"  \"XCustomizedBladeConfig\": [\r\n" + 
				"    {\r\n" + 
				"      \"BladeName\": \"testblade\",\r\n" + 
				"      \"BladeShowName\": \"TestBlade[Origin]\",\r\n" + 
				"      \"BladeModel\": \"model/1\",\r\n" + 
				"      \"BladeTexture\": \"texture/1\",\r\n" + 
				"      \"BladeDamge\": 10.0,\r\n" + 
				"      \"BladeDuration\": 200,\r\n" + 
				"      \"BladeWitched\": false,\r\n" + 
				"      \"BladeStandBy\": 1,\r\n" + 
				"      \"BladeSA\": 1,\r\n" + 
				"      \"SwordColor\": 16711935, //Decimal Color Code\r\n" + 
				"      \"BladeRecipe\": [\r\n" + 
				"        \"phtc2\", //Enter modid here.\r\n" + 
				"        \"SlashBlade.bladeWood\",\r\n" + 
				"        \"normalingot\",\r\n" + 
				"        \"normalingot\",\r\n" + 
				"        \"normalingot\",\r\n" + 
				"        \"energydust\",\r\n" + 
				"        \"energydust\",\r\n" + 
				"        \"energydust\",\r\n" + 
				"        \"ph_hyperdiamond\",\r\n" + 
				"        \"ph_hyperdiamond\",\r\n" + 
				"        \"ph_hyperdiamond\"\r\n" + 
				"      ]\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"BladeName\": \"testblade2\",\r\n" + 
				"      \"BladeShowName\": \"TestBlade[Nano Tech]\",\r\n" + 
				"      \"BladeModel\": \"model/2\",\r\n" + 
				"      \"BladeTexture\": \"texture/2\",\r\n" + 
				"      \"BladeDamge\": 1024,\r\n" + 
				"      \"BladeDuration\": 2000,\r\n" + 
				"      \"BladeWitched\": true,\r\n" + 
				"      \"BladeStandBy\": 1,\r\n" + 
				"      \"BladeSA\": 1,\r\n" + 
				"      \"SwordColor\": 16744192,\r\n" + 
				"      \"Enchantment\": [ \"power\", 10, \"unbreaking\", 10 ]\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";
	}
	private boolean checkConfig() {
		File json=new File(this.path);
		return json.exists();
	}
	public void checkInit() {
		if(this.checkConfig()==false) {
			try {
				System.out.println("XCustomizedBlade:Now setting the first time running environment.");
				FileOutputStream out=new FileOutputStream(this.path);
				out.write(this.STDJsonConfig.getBytes());
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			
			
		}
	}
}
