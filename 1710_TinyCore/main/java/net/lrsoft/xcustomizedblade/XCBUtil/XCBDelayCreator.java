package net.lrsoft.xcustomizedblade.XCBUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.lrsoft.xcustomizedblade.InfoShow;

public class XCBDelayCreator extends Thread{
	private int runtime;
	public XCBDelayCreator(int in) {
		this.runtime=in;
	}
	public void run() {
		try {
			FileOutputStream out=new FileOutputStream(InfoShow.getNowPath()+"/XCB_DELAY_TEMP.dat");
			for(int i=0;i<runtime;i++) {
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
         this.stop();
	}
}
