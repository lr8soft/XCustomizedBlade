package net.lrsoft.xcustomizedblade.XCBNetwork;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.lrsoft.xcustomizedblade.ConfigJsonReader;
import net.lrsoft.xcustomizedblade.InfoShow;

public class XCBConfigSync extends Thread{
	private boolean isServer;
	private JsonArray jsonarray;
	private int port;
	InetAddress iaddress=null;
	MulticastSocket socket=null;
	public XCBConfigSync(JsonArray input,String hostname,int port,boolean isserver){
		this.jsonarray=input;
		this.isServer=isserver;
		this.port=port;
		try {
			iaddress=InetAddress.getByName(hostname);
			socket=new MulticastSocket(port);
			if(isServer) {
				socket.setTimeToLive(255);
			} 
			socket.joinGroup(iaddress);
		}catch(Exception e) {}
	}
	public void run() {
		if(isServer) {
			System.out.println("XCustomizedBlade SERVER:Start sending data packet to client.");
			while(true) {
				DatagramPacket packet=null;
				byte data[]=new byte[8192];
				data=jsonarray.toString().getBytes();
				packet=new DatagramPacket(data,data.length,iaddress,port);
				try {
					socket.send(packet);
					Thread.sleep(1000);
					System.out.println("XCB SERVER:Config "+jsonarray.toString());
				}catch(Exception e) {}
			}
		}else {
			while(true) {
				System.out.println("XCustomizedBlade Client:Start download config from SERVER.");
				String[] temp=new String[3];
				for(int i=0;i<3;i++) {
					byte[] data=new byte[8192];
					DatagramPacket packet=new DatagramPacket(data,data.length,iaddress,port);
					try {
						socket.receive(packet);
						temp[i]=data.toString();
						JOptionPane.showMessageDialog(null,"Config file:"+data);
					}catch(Exception e) {}
				}
				if(temp[0].equals(temp[1])&&temp[1].equals(temp[2])) {
					System.out.println("XCustomizedBlade Client:Finished download config from server.");
					String input=jsonarray.toString();
					if(!temp[0].equals(input)) {
						updataToJson(temp[0]);
					}
					break;
				}
			}
		}
	}
	public void updataToJson(String newinfo) {
		String writeInfo="{\r\n" + 
				"  \"temp\":";
		String tailInfo="\r\n}";
	/*	JsonArray newarray=new JsonArray();
		ConfigJsonReader jsondata=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json",false);
		jsondata.readFromJson();
		jsondata.json.remove("XCustomizedBladeConfig");
		jsondata.json.addProperty("XCustomizedBladeConfig", newinfo);*/
		Gson out=new Gson();
		try {
			FileOutputStream output=new FileOutputStream(InfoShow.getNowPath()+"/XCustomizedBlade_updata.json");
			output.write(writeInfo.getBytes());
			output.write(newinfo.getBytes());
			output.write(tailInfo.getBytes());
			output.close();
		} catch (FileNotFoundException e) {} catch (IOException e) {}
	}
}
