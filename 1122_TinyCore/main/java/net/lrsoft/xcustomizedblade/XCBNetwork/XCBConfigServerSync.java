package net.lrsoft.xcustomizedblade.XCBNetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.lrsoft.xcustomizedblade.ConfigJsonReader;
import net.lrsoft.xcustomizedblade.InfoShow;

public class XCBConfigServerSync extends Thread{
	private JsonArray jsonarray,saarray,searray;
	private OutputStreamWriter output;
	private ServerSocket server;
	private Socket socket;
	private int port;
	public XCBConfigServerSync(JsonArray inputblade,JsonArray inputsa,JsonArray inputse,int port){
		this.jsonarray=inputblade;
		this.saarray=inputsa;
		this.searray=inputse;
		this.port=port;
		try {
			server=new ServerSocket(port);
			System.out.println("XCB SERVER:Socket has been created.");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("XCB SERVER:Fail to create TCP connection.");
			this.stop();
		}
	}
	public void run() {
		System.out.println("XCB SERVER:Waiting for client.");
		while(true) {
			String jsonInfo=jsonarray.toString();
			String saInfo=saarray.toString();
			String seInfo=searray.toString();
			try {
				socket=server.accept();
				PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
				out.print(jsonInfo);
				out.print(">>>>>");
				out.print(saInfo);
				out.print(">>>>>");
				out.print(seInfo);
				System.out.println("XCB SERVER:Config has been send to"+socket.getRemoteSocketAddress().toString());
			} catch (Exception e) {}		
			try {
				if(server!=null) {
					server.close();
				}
				if(socket!=null) {
					socket.close();
				}
			}catch(IOException e) {}
			startNewThread();
		}
	}
	public void startNewThread() {
		XCBConfigServerSync newThread=new XCBConfigServerSync(this.jsonarray,this.saarray,this.searray,this.port);
		newThread.start();
		this.stop();
	}
}
