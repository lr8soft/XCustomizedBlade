package net.lrsoft.xcustomizedblade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.lrsoft.xcustomizedblade.InfoShow;
public class ModInit {
	private String path;
	private String STDJsonConfig;
	public ModInit() {
		this.path=InfoShow.getNowPath()+"/XCustomizedBlade.json";
		this.STDJsonConfig="{\r\n" + 
				"  \"XCustomizedBladeVER\": 1.40,\r\n" + 
				"  \"CustomizedRecipe\": true,\r\n" + 
				"  \"ToolRecipe\": true,\r\n" + 
				"  \"ServerInfo\": {\r\n" + 
				"    \"ServerPort\": 2333,\r\n" + 
				"    \"ServerHostName\": \"127.0.0.1\",\r\n" + 
				"    \"SyncConfig\": true\r\n" + 
				"  },\r\n" + 
				"  \"XCustomizedBladeConfig\": [\r\n" + 
				"    {\r\n" + 
				"      \"BladeName\": \"testblade\",\r\n" + 
				"      \"BladeRecipe\": [ \"phtc2\", \"SlashBlade.bladeWood\", \"normalingot\", \"normalingot\", \"normalingot\", \"energydust\", \"energydust\", \"energydust\", \"ph_hyperdiamond\", \"ph_hyperdiamond\", \"ph_hyperdiamond\" ],\r\n" + 
				"      \"BladeShowName\": \"TestBlade[Origin]\",\r\n" + 
				"      \"BladeModel\": \"model/1\",\r\n" + 
				"      \"BladeTexture\": \"texture/1\",\r\n" + 
				"      \"BladeDamge\": 12.0,\r\n" + 
				"      \"BladeDuration\": 200,\r\n" + 
				"      \"BladeWitched\": false,\r\n" + 
				"      \"BladeStandBy\": 1,\r\n" + 
				"      \"BladeSA\": 1,\r\n" + 
				"      \"SwordColor\": 16711935\r\n" + 
				"    },\r\n" + 
				"    {},\r\n" + 
				"    {\r\n" + 
				"      \"BladeName\": \"TestBlade[Technology Revolution]\",\r\n" + 
				"      \"Enchantment\": [ \"power\", 12, \"sharpness\", 12 ],\r\n" + 
				"      \"BladeShowName\": \"测之刃【技术革新】\",\r\n" + 
				"      \"BladeModel\": \"model/2\",\r\n" + 
				"      \"BladeTexture\": \"texture/2\",\r\n" + 
				"      \"BladeDamge\": 1024.0,\r\n" + 
				"      \"BladeDuration\": 1200,\r\n" + 
				"      \"BladeWitched\": false,\r\n" + 
				"      \"BladeStandBy\": 1,\r\n" + 
				"      \"BladeSA\": 8,\r\n" + 
				"      \"SwordColor\": 16744192\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"BladeName\": \"testblade2\",\r\n" + 
				"      \"BladeShowName\": \"TestBlade[Second]\",\r\n" + 
				"      \"BladeModel\": \"model/1\",\r\n" + 
				"      \"BladeTexture\": \"texture/1\",\r\n" + 
				"      \"BladeDamge\": 18.0,\r\n" + 
				"      \"BladeDuration\": 200,\r\n" + 
				"      \"BladeWitched\": true,\r\n" + 
				"      \"BladeStandBy\": 2,\r\n" + 
				"      \"BladeSA\": 7,\r\n" + 
				"      \"SwordColor\": 16744192\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"XCustomizedSA\": [\r\n" + 
				"    {\r\n" + 
				"      \"SAName\": \"SpecialAttack_Test0\",\r\n" + 
				"      \"SANumber\": 2333,\r\n" + 
				"      \"SACost\": 20,\r\n" + 
				"      \"SAInfo\": [\"PS\",\"SE\",\"SP\"],\r\n" + 
				"      \"SAStep\": [ 5, 3, 1 ],\r\n" + 
				"      \"SACount\": 2\r\n" + 
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
		/*		 OutputStreamWriter outwriter=new OutputStreamWriter(out,"UTF-8");
				 outwriter.write(this.STDJsonConfig);
				 outwriter.flush();
				 outwriter.close();*/
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
