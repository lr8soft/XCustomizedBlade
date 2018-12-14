package net.lrsoft.xcustomizedblade;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class InfoShow {
	private int type;
	public InfoShow(int t) {
		this.type=t;
	}
	public void showInfo() {
		if(type==0) {
			JOptionPane.showMessageDialog(null,"XCustomizedBlade Error:\nConfig file can\'t be read!");
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
