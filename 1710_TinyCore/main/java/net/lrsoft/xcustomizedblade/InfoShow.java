package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

public class InfoShow {
	private int type;
	public InfoShow(int t) {
		this.type=t;
	}
	public void showInfo() {
		if(type==0) {
			System.out.println("XCustomizedBlade Error:\nConfig file can\'t be read!");
			System.exit(-1);
		}else if(type==-1){
			System.out.println("XCustomizedBlade Error:\nConfig file format error!");
			System.exit(-1);
		}else {
			
		}
	}
	 public static String getNowPath() {
			File directory = new File("");
			String courseFile=null;
			try {
				courseFile = directory.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return courseFile;
	 }
}
