package net.lrsoft.xcustomizedblade.XCBNetwork;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.lrsoft.xcustomizedblade.ConfigJsonReader;
import net.lrsoft.xcustomizedblade.InfoShow;

public class XCBConfigServerSync extends Thread{
	private JsonArray jsonarray;
	private OutputStream output;
	private ServerSocket server;
	private Socket socket;
	private int port;
	public XCBConfigServerSync(JsonArray input,int port){
		this.jsonarray=input;
		this.port=port;
		try {
			server=new ServerSocket(port);
			System.out.println("XCB SERVER:Socket has been created.");
		}catch(Exception e) {
			System.out.println("XCB SERVER:Fail to create TCP connection.");
		}
	}
	public void run() {
		System.out.println("XCB SERVER:Waiting for client.");
		while(true) {
			byte[] sendinfo=jsonarray.toString().getBytes();
			try {
				socket=server.accept();
				output=socket.getOutputStream();
				output.write(sendinfo);
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
		}
	}
}
