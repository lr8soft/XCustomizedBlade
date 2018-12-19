package net.lrsoft.xcustomizedblade.EasyEditor;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.minecraft.item.ItemStack;

public class EasyNBTEditor extends JFrame{
	public ItemStack blade;
	public EasyNBTEditor(ItemStack input) {
		int x=60,y=60,w=120,h=15,tw=300,th=20;
		this.blade=input;
		this.setSize(620, 650);
		this.setTitle("XCustomizedBlade SlashBlade Editor");
		Container menu=this.getContentPane();
		this.setLayout(null);
		
		JLabel namel=new JLabel("拔刀剑识别名      ：");namel.setBounds(x, y, w, h);menu.add(namel);
		JLabel shownl=new JLabel("拔刀剑显示名称：");shownl.setBounds(x, y+4*h, w, h);menu.add(shownl);
		JLabel pathl=new JLabel("模型路径              ：");pathl.setBounds(x, y+8*h, w, h);menu.add(pathl);
		JLabel texturel=new JLabel("贴图路径              ：");texturel.setBounds(x, y+12*h, w, h);menu.add(texturel);
		JLabel colorl=new JLabel("十进制颜色代码：");colorl.setBounds(x, y+16*h, w, h);menu.add(colorl);
		
		JTextField	name=new JTextField();name.setBounds(x+w, y, tw, th);menu.add(name);
		JTextField  showname=new JTextField();showname.setBounds(x+w, y+4*h, tw, th);menu.add(showname);
		JTextField  path=new JTextField();path.setBounds(x+w, y+8*h, tw, th);menu.add(path);
		JTextField  texture=new JTextField();texture.setBounds(x+w, y+12*h, tw, th);menu.add(texture);
		JTextField  color=new JTextField();color.setBounds(x+w, y+16*h, tw, th);menu.add(color);
		
		JLabel damal=new JLabel("威力：");damal.setBounds(x, y+20*h, w, h);menu.add(damal);
		JLabel dural=new JLabel("耐久：");dural.setBounds(x+10*h, y+20*h, w, h);menu.add(dural);
		JLabel sal=new JLabel("SA：");sal.setBounds(x, y+24*h, w, h);menu.add(sal);
		JLabel killl=new JLabel("杀敌：");killl.setBounds(x+10*h, y+24*h, w, h);menu.add(killl);
		JLabel psl=new JLabel("耀魂：");psl.setBounds(x, y+28*h, w, h);menu.add(psl);
		JLabel rpl=new JLabel("锻造：");rpl.setBounds(x+10*h, y+28*h, w, h);menu.add(rpl);
		
		JTextField dama=new JTextField();dama.setBounds(x+60, y+20*h, 80, th);menu.add(dama);
		JTextField dura=new JTextField();dura.setBounds(x+10*h+60, y+20*h, 80, th);menu.add(dura);
		JTextField sa=new JTextField();sa.setBounds(x+60,  y+24*h, 80, th);menu.add(sa);
		JTextField kill=new JTextField();kill.setBounds(x+10*h+60,  y+24*h, 80, th);menu.add(kill);
		JTextField ps=new JTextField();ps.setBounds(x+60, y+28*h, 80, th);menu.add(ps);
		JTextField rp=new JTextField();rp.setBounds(x+10*h+60, y+28*h, 80, th);menu.add(rp);
		
		JCheckBox iswitchB=new JCheckBox("妖刀");iswitchB.setBounds(x+10*h, y+32*h,60, 20);menu.add(iswitchB);
		JButton submit =new JButton("提交");submit.setBounds(x+10*h+60+100, y+20*h, 120, 150);
		try {
			name.setText(getString("CurrentItemName"));
			showname.setText(blade.getDisplayName());
			path.setText(getString("ModelName"));
			texture.setText(getString("TextureName"));
			dama.setText(String.valueOf(getInt("baseAttackModifier")));
			dura.setText(String.valueOf(getInt("CustomMaxDamage")));
			kill.setText(String.valueOf(getInt("killCount")));
			ps.setText(String.valueOf(getInt("ProudSoul")));
			rp.setText(String.valueOf(getInt("RepairCounter")));
			iswitchB.setSelected(getBoolean("isDefaultBewitched"));
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"拔刀信息加载失败！");
			try {
				this.finalize();
			} catch (Throwable e1) {
				this.setVisible(false);
			}
		}
		try {
			color.setText(String.valueOf(getInt("SummonedSwordColor")));
		}catch(Exception e) {
			color.setText("255");
		}
		try {
			sa.setText(String.valueOf(getInt("SpecialAttackType")));
		}catch(Exception e) {
			sa.setText("0");
		}
		EasyNBTEditor classtemp=this;
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(name.getText().equals("")==false) setString("CurrentItemName",name.getText());
				if(showname.getText().equals("")==false) blade.setStackDisplayName(showname.getText());
				if(path.getText().equals("")==false) setString("ModelName",path.getText());
				if(texture.getText().equals("")==false) setString("TextureName",texture.getText());
				if(dama.getText().equals("")==false) setInt("baseAttackModifier",Integer.parseInt(dama.getText()));
				if(dura.getText().equals("")==false) setInt("CustomMaxDamage",Integer.parseInt(dura.getText()));
				if(rp.getText().equals("")==false) setInt("RepairCounter",Integer.parseInt(rp.getText()));
				setInt("killCount",Integer.parseInt(kill.getText()));
				setInt("ProudSoul",Integer.parseInt(ps.getText()));
				setBoolean("isDefaultBewitched",iswitchB.isSelected());
				if(color.getText().equals("0")==false) setInt("SummonedSwordColor",Integer.parseInt(color.getText()));
				setInt("SpecialAttackType",Integer.parseInt(sa.getText()));
				JOptionPane.showMessageDialog(null,"保存成功！");
				classtemp.setVisible(false);
				try {
					classtemp.finalize();
				} catch (Throwable e) {e.printStackTrace();}
			}
		});
		menu.add(submit);
		
	}
	public boolean getBoolean(String tag) {
		return blade.stackTagCompound.getBoolean(tag);
	}
	public String getString(String tag) {
		return blade.stackTagCompound.getString(tag);
	}
	public int getInt(String tag) {
		return blade.stackTagCompound.getInteger(tag);
	}
	public void setBoolean(String tag,boolean info) {
		blade.stackTagCompound.setBoolean(tag, info);
	}
	public void setString(String tag,String info) {
		blade.stackTagCompound.setString(tag, info);
	}
	public void setInt(String tag,int info) {
		blade.stackTagCompound.setInteger(tag, info);
	}
}
