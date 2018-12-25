package net.lrsoft.xcustomizedblade.EasyEditor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.lrsoft.xcustomizedblade.InfoShow;
import net.lrsoft.xcustomizedblade.XCBSpeicalAttack.SAJsonReader;
import net.lrsoft.xcustomizedblade.XCBUtil.XCBListInfo;
import net.lrsoft.xcustomizedblade.XCBUtil.XCBSelectBox;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class EasySAEditor extends JFrame {
	SAJsonReader SAInfo;
	JList<String> steplist;
	private JTextField count;
	private JTextField damage;
	public Vector actioncontents=new Vector();
	public Vector actioncount=new Vector();
	public Vector actiondamage=new Vector();
	private EasyCreateSpecialAttack inputclass;
	private JTextField name;
	private JTextField cost;
	private JTextField idField;
	public EasySAEditor(EasyCreateSpecialAttack tempclass) {
		String[] sainfo= {"幻影剑(一根)","终焉樱","闪电","MaximumBet(快速两斩)","平突"};
		//                   0       1    2       3              4
		inputclass=tempclass;
		setResizable(false);
		SAInfo=new SAJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		Container menu=this.getContentPane();
		this.setSize(585, 590);
		this.setVisible(true);
		getContentPane().setLayout(null);
		this.setTitle("XCustomizedSA Editor");
		
		DefaultListModel dlm=new DefaultListModel();
		JList actionList = new JList();//<>(new XCBListInfo(steplistN))
		actionList.setBounds(32, 27, 360, 239);
		getContentPane().add(actionList);
		
		JComboBox<String> selectAction = new JComboBox<>(new XCBSelectBox(sainfo));
		selectAction.setBounds(92, 418, 102, 27);
		getContentPane().add(selectAction);
		EasySAEditor classoutside=this;
	
		JButton addAction = new JButton("添加动作");
		JList iactionList=actionList;
		addAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(count.getText()!=null&&damage.getText()!=null) {
					switch(selectAction.getSelectedIndex()) {
						case 0:
							actioncontents.add("PS");
							dlm.addElement("幻影剑 "+count.getText()+"次，单次威力"+damage.getText());
							break;
						case 1:
							actioncontents.add("SE");
							dlm.addElement("终焉樱 "+count.getText()+"次，总威力"+damage.getText());
							break;
						case 2:
							actioncontents.add("LN");
							dlm.addElement("雷电"+count.getText()+"次，总威力"+damage.getText());
							break;
						case 3:
							actioncontents.add("MB");
							dlm.addElement("快速两连斩"+count.getText()+"次，总威力"+damage.getText());
							break;
						case 4:
							actioncontents.add("SP");
							dlm.addElement("平突"+count.getText()+"次，总威力"+damage.getText());
							break;
					}
					actioncount.add(Integer.valueOf(count.getText()));
					actiondamage.add(Integer.valueOf(damage.getText()));
					actionList.setModel(dlm);
				}
			}
		});
		addAction.setBounds(33, 475, 132, 47);
		getContentPane().add(addAction);
		
		JButton delAction = new JButton("删除动作");
		delAction.setEnabled(false);
		delAction.setBounds(193, 475, 132, 47);
		getContentPane().add(delAction);
		
		JLabel label = new JLabel("动作");
		label.setBounds(32, 421, 81, 21);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("次数");
		label_1.setBounds(219, 359, 81, 21);
		getContentPane().add(label_1);
		
		count = new JTextField();
		count.setBounds(285, 356, 107, 27);
		getContentPane().add(count);
		count.setColumns(10);
		
		JLabel label_2 = new JLabel("威力");
		label_2.setBounds(32, 359, 81, 21);
		getContentPane().add(label_2);
		
		damage = new JTextField();
		damage.setBounds(92, 356, 102, 27);
		getContentPane().add(damage);
		damage.setColumns(10);
		
		JButton submit = new JButton("提交");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] scontext=new String[actioncount.toArray().length];
				int[] scount=new int[actioncount.toArray().length];
				int[] sdamage=new int[actioncount.toArray().length];
				for(int a=0;a<actioncount.toArray().length;a++) {
					scount[a]=(int)actioncount.get(a);
					sdamage[a]=(int)actiondamage.get(a);
					scontext[a]=String.valueOf(actioncontents.get(a));
				}
				int ret=SAInfo.addToJson(name.getText(),Integer.valueOf(idField.getText()),
						Integer.valueOf(cost.getText()), scontext,scount, sdamage);
				if(ret==1)
					JOptionPane.showMessageDialog(null, "已添加到配置中！");
				else
					JOptionPane.showMessageDialog(null, "添加失败！");
			}
		});
		submit.setBounds(407, 27, 148, 47);
		getContentPane().add(submit);
		EasySAEditor ctemp=this;
		JButton cancel = new JButton("取消");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ctemp.setVisible(false);
				} catch (Exception e2) {}
				try {
					ctemp.finalize();
				} catch (Throwable e1) {}
			}
		});
		cancel.setBounds(407, 89, 148, 47);
		getContentPane().add(cancel);
		
		JLabel label_3 = new JLabel("名称");
		label_3.setBounds(32, 302, 81, 21);
		getContentPane().add(label_3);
		
		name = new JTextField();
		name.setBounds(92, 299, 102, 27);
		getContentPane().add(name);
		name.setColumns(10);
		
		JLabel label_4 = new JLabel("消耗");
		label_4.setBounds(219, 421, 81, 21);
		getContentPane().add(label_4);
		
		cost = new JTextField();
		cost.setBounds(285, 418, 107, 27);
		getContentPane().add(cost);
		cost.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(219, 302, 81, 21);
		getContentPane().add(lblId);
		
		idField = new JTextField();
		idField.setBounds(285, 299, 107, 27);
		getContentPane().add(idField);
		idField.setColumns(10);
	}

}
