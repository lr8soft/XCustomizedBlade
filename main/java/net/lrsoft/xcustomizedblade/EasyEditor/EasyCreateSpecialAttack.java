package net.lrsoft.xcustomizedblade.EasyEditor;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import com.google.gson.JsonObject;

import net.lrsoft.xcustomizedblade.InfoShow;
import net.lrsoft.xcustomizedblade.XCBSpeicalAttack.SAJsonReader;
import net.lrsoft.xcustomizedblade.XCBUtil.XCBListInfo;

public class EasyCreateSpecialAttack extends JFrame{
	SAJsonReader SAInfo;
	JList<String> salist;
	String[] saName=null;
	JButton add,del,edit;
	public EasyCreateSpecialAttack() {
		SAInfo=new SAJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		saName=new String[SAInfo.jsondata.size()];
		for(int i=0;i<SAInfo.jsondata.size();i++) {
			JsonObject tempobj=SAInfo.jsondata.get(i).getAsJsonObject();
			saName[i]=tempobj.get("SAName").getAsString();
		}
		Container menu=this.getContentPane();
		this.setTitle("XCustomoizedBlade EasyCustomizedSA");
		this.setSize(600, 400);
		this.setLayout(null);
		//////////////////////GUI Init
		salist=new JList<>(new XCBListInfo(saName));
		salist.setBounds(10, 10, 400, 300);
		menu.add(salist);
		//SAList
		EasyCreateSpecialAttack temp=this;
		add=new JButton("添加");
		add.setBounds(420, 10, 120, 25);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EasySAEditor saedit=new EasySAEditor(temp);
			}
		});
		menu.add(add);
		//ADD buton
		edit=new JButton("修改");
		edit.setBounds(420, 10+25+10, 120, 25);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			//	EasySAEditor saedit=new EasySAEditor(null);
			}
		});
		edit.setEnabled(false);
		menu.add(edit);
		del=new JButton("删除");
		del.setBounds(420, 10+25*2+10*2, 120, 25);
		del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!salist.isSelectionEmpty()) {
					SAInfo.deleteToJson(salist.getSelectedValue());
				}
			}
		});
		menu.add(del);
	}
}