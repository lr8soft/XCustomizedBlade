package net.lrsoft.xcustomizedblade;

import java.awt.*;
import javax.swing.*;

public class EasyCreateBlade extends JFrame{
	private int x,y,w,h,tw,th;
	private ConfigJsonReader json;
	JLabel namel,shownl,pathl,texturel;
	JButton submit,cancel,add,edit;
	JTextField name,showname,path,texture;
	JList<String> list;
	public EasyCreateBlade() {
		x=y=60;w=120;h=15;tw=300;th=20;
		json=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		if(json.readFromJson()==0) {
			JOptionPane.showMessageDialog(null,"XCustomizedBlade:Can\'t read from config!");
			return;
		}else {
			XCDJsonInfo[] info=json.GetInfo();
			String[] listname=new String[json.datalen];
			System.out.println("XCustomizedBlade:Read from config,config len "+json.datalen+".");
			for(int i=0;i<json.datalen;i++) {
				listname[i]=info[i].bladename;
				System.out.println("XCustomizedBlade:Read blade "+listname[i]);
			}
			this.setSize(800, 600);
			this.setTitle("XCustomizedBlade Easy-SlashBlade");
			Container menu=this.getContentPane();
			this.setLayout(null);
			namel=new JLabel("拔刀剑识别名      ：");namel.setBounds(x, y, w, h);menu.add(namel);
			shownl=new JLabel("拔刀剑显示名称：");shownl.setBounds(x, y+4*h, w, h);menu.add(shownl);
			pathl=new JLabel("模型路径              ：");pathl.setBounds(x, y+8*h, w, h);menu.add(pathl);
			texturel=new JLabel("贴图路径              ：");texturel.setBounds(x, y+12*h, w, h);menu.add(texturel);
			
			name=new JTextField();name.setBounds(x+w, y, tw, th);menu.add(name);
			showname=new JTextField();showname.setBounds(x+w, y+4*h, tw, th);menu.add(showname);
			path=new JTextField();path.setBounds(x+w, y+8*h, tw, th);menu.add(path);
			texture=new JTextField();texture.setBounds(x+w, y+12*h, tw, th);menu.add(texture);
			
			list=new JList<>(new showList(listname));list.setBounds(x+w+w/2+tw, y, 200, 400);menu.add(list);
		}
	}
}
class showList extends AbstractListModel<String>{
	private String[] info=null;
	public showList(String[] input) {
		this.info=input;
	}
	public String getElementAt(int arg0) {
		if(arg0<info.length)
			return info[arg0++];
		else
			return null;
	}
	public int getSize() {
		return info.length;
	}
	
}
