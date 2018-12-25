package net.lrsoft.xcustomizedblade.EasyEditor;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.lrsoft.xcustomizedblade.XCBUtil.XCBSelectBox;

public class EasyMoreDetail extends JFrame{
	public int standby,sa;
	int x,y,z,delta;
	public JCheckBox iswitchB;
	public boolean iswitched;
	public JComboBox<String> standbyList,saList;
	private JLabel sal,sbl;
	String[] SBinfo= {"右手","左手","后背"};
	String[] SAinfo= {"次元斩","波刀龙胆","幻影刃","平突","円刃","急袭幻影剑-衰破","终焉樱","MaximumBet"};
					 // 0     1          2     3   4    5       6       7       8
	public EasyMoreDetail(EasyCreateBlade callback) {
		x=y=50;delta=100;
		this.setSize(400, 300);
		this.setTitle("XCustomizedBlade Easy-SlashBlade Detail");
		Container menu=this.getContentPane();
		this.setLayout(null);
		iswitchB=new JCheckBox("妖刀");iswitchB.setBounds(x, y+90, 100, 20);
		iswitchB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				iswitched=iswitchB.isSelected();
			}
		});
		iswitchB.setSelected(callback.iswitched);
		menu.add(iswitchB);
		JLabel dura=new JLabel("耐久");dura.setBounds(10, y, 50, 20);menu.add(dura);
		JTextField duraT=new JTextField();duraT.setBounds(x, y, 110, 20);
		duraT.setText(Integer.toString(callback.duration));;menu.add(duraT);
		JLabel dama=new JLabel("伤害");dama.setBounds(10, y+30, 50, 20);menu.add(dama);
		JTextField damaT=new JTextField();damaT.setBounds(x, y+30, 110, 20);
		damaT.setText(Integer.toString(callback.damage));menu.add(damaT);
		JButton submit=new JButton("提交");submit.setBounds(x,y+123, 100, 20);
		EasyMoreDetail callClass=this;
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callback.damage=Integer.valueOf(damaT.getText());
				callback.duration=Integer.valueOf(duraT.getText());
				callback.iswitched=iswitched;
				callback.sa=sa;
				callback.standby=standby;
				callClass.setVisible(false);
			}
		});
		menu.add(submit);
		sbl=new JLabel("选择持刀姿势:");sbl.setBounds(x+150, y-30, 100, 20);menu.add(sbl);
		standbyList=new JComboBox<>(new XCBSelectBox(SBinfo));standbyList.setBounds(x+150, y, 100, 40);
		standbyList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				standby=standbyList.getSelectedIndex();
			}
		});
		standbyList.setEnabled(false);
		menu.add(standbyList);
		sal=new JLabel("选择特殊攻击SA:");sal.setBounds(x+150, y+70, 100, 20);menu.add(sal);
		saList=new JComboBox<>(new XCBSelectBox(SAinfo));saList.setBounds(x+150, y+100, 100, 40);
		saList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sa=saList.getSelectedIndex();
			}
		});
		menu.add(saList);
	}
}