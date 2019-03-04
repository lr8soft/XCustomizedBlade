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
				"  \"XCustomizedBladeVER\": 1.61,\r\n" + 
				"  \"CustomizedRecipe\": true,\r\n" + 
				"  \"ToolRecipe\": false,\r\n" + 
				"  \"XCBTinyCore\": true,\r\n" + 
				"  \"ServerInfo\": {\r\n" + 
				"    \"ServerPort\": 2333,\r\n" + 
				"    \"ServerHostName\": \"127.0.0.1\",\r\n" + 
				"    \"SyncConfig\": false\r\n" + 
				"  },\r\n" + 
				"  \"XCustomizedBladeConfig\": [\r\n" + 
				"    {\r\n" + 
				"      \"BladeName\": \"TestBlade[Technology Revolution]\",\r\n" + 
				"      \"Enchantment\": [ \"power\", 12, \"sharpness\", 12 ],\r\n" + 
				"      \"BladeShowName\": \"TestBlade【TechREV】\",\r\n" + 
				"      \"BladeModel\": \"model/2\",\r\n" + 
				"      \"BladeTexture\": \"texture/2\",\r\n" + 
				"      \"BladeDamge\": 1024.0,\r\n" + 
				"      \"BladeDuration\": 1200,\r\n" + 
				"      \"BladeWitched\": false,\r\n" + 
				"      \"BladeStandBy\": 1,\r\n" + 
				"      \"BladeSA\": 123,\r\n" + 
				"      \"BladeSE\": \"TestSE\",\r\n" + 
				"      \"SELevel\": 15,\r\n" + 
				"      \"SwordColor\": 16744192\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"XCustomizedSA\": [\r\n" + 
				"    {\r\n" + 
				"      \"SAName\": \"TestSA\",\r\n" + 
				"      \"SANumber\": 123,\r\n" + 
				"      \"SACost\": 10,\r\n" + 
				"      \"SAInfo\": [ \"EP\", \"SD\", \"PS\", \"MB\", \"LN\" ],\r\n" + 
				"      \"StepDamage\": [ 10, 10, 3, 3, 10 ],\r\n" + 
				"      \"SAStep\": [ 5, 1, 100, 2, 5 ],\r\n" + 
				"      \"SACount\": 5,\r\n" + 
				"      \"PeaceSelector\": true\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"XCustomizedSE\": [\r\n" + 
				"    {\r\n" + 
				"      \"SEName\": \"TestSE\",\r\n" + 
				"      \"SECost\": 10,\r\n" + 
				"      \"SELevel\": 15,\r\n" + 
				"      \"SEStep\": [ \"PE\", \"SD\", \"PS\" ],\r\n" + 
				"      \"SERuntime\": [ 12, 10, 3 ],\r\n" + 
				"      \"SEDamage\": [ 100, 1, 100 ]\r\n" + 
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
				System.out.println("XCustomizedBlade TinyCore:Now setting the first time running environment.");
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
