package net.lrsoft.xcustomizedblade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EasyCreateBlade extends JFrame{
	public int sa,standby,duration,damage;public boolean iswitched;
	private int x,y,w,h,tw,th,bw,bh,bi;
	public ConfigJsonReader json;
	private EasyMoreDetail detailWindow;
	JLabel namel,shownl,pathl,texturel,colorl;
	JButton add,save,delete,detail;
	JTextField name,showname,path,texture,color;
	JList<String> list;
	LinkLabel decinfo;
	public EasyCreateBlade() {
		sa=standby=0;iswitched=false;duration=200;damage=18;
		x=y=60;w=120;h=15;tw=300;th=20;bw=120;bh=45;bi=30;
		json=new ConfigJsonReader(InfoShow.getNowPath()+"/XCustomizedBlade.json");
		if(json.readFromJson()==0) {
			JOptionPane.showMessageDialog(null,"XCustomizedBlade:Can\'t read from config!");
			return;
		}else {
			XCDJsonInfo[] info=json.GetInfo();
			String[] listname=new String[json.datalen];
			System.out.println("XCustomizedBlade:Read from config,config length "+json.datalen+".");
		/*	for(int i=0;i<json.datalen;i++) {
				listname[i]=info[i].bladename;
				System.out.println("XCustomizedBlade:Read blade "+listname[i]);
			}*/
			this.refreshData(listname, json, info);
			this.setSize(800, 550);
			this.setTitle("XCustomizedBlade Easy-SlashBlade");
			Container menu=this.getContentPane();
			this.setLayout(null);
			namel=new JLabel("拔刀剑识别名      ：");namel.setBounds(x, y, w, h);menu.add(namel);
			shownl=new JLabel("拔刀剑显示名称：");shownl.setBounds(x, y+4*h, w, h);menu.add(shownl);
			pathl=new JLabel("模型路径              ：");pathl.setBounds(x, y+8*h, w, h);menu.add(pathl);
			texturel=new JLabel("贴图路径              ：");texturel.setBounds(x, y+12*h, w, h);menu.add(texturel);
			colorl=new JLabel("十进制颜色代码：");colorl.setBounds(x, y+16*h, w, h);menu.add(colorl);
			
			name=new JTextField();name.setBounds(x+w, y, tw, th);menu.add(name);
			showname=new JTextField();showname.setBounds(x+w, y+4*h, tw, th);menu.add(showname);
			path=new JTextField();path.setBounds(x+w, y+8*h, tw, th);menu.add(path);
			texture=new JTextField();texture.setBounds(x+w, y+12*h, tw, th);menu.add(texture);
			color=new JTextField();color.setBounds(x+w, y+16*h, tw, th);menu.add(color);
			
			list=new JList<>(new showList(listname));
			list.setBounds(x+w+w/2+tw, y, 200, 280);
			list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					for(int j=0;j<json.datalen;j++) {
						try{
							if(list.getSelectedValue()==info[j].bladename) {
								name.setText(info[j].bladename);
								showname.setText(info[j].showName);
								path.setText(info[j].bladeModel);
								texture.setText(info[j].bladeTexture);
								color.setText(String.valueOf(info[j].color));
								duration=info[j].bladeduration;
								iswitched=info[j].iswitched;
								damage=(int)info[j].bladedamage;
							}	
						}catch(NullPointerException e) {
							continue;
						}
					}
				}
				
			});
			menu.add(list);
			JLabel mInfo=new JLabel("注意！重进游戏后设置生效！");mInfo.setBounds(x, y+20*h+2*bh-25, 450, 20);menu.add(mInfo);
			decinfo=new LinkLabel("点击这里，更多十进制颜色代码","http://www.360doc.com/content/14/0216/21/6954561_353057367.shtml");
			decinfo.setBounds(x, y+20*h+2*bh, 450, 20);menu.add(decinfo);
			add=new JButton("添加新拔刀");add.setBounds(x, y+20*h, bw, bh);
			add.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int ret=JOptionPane.showConfirmDialog(null, "你确定放弃现有数据么","XCustomizedBlade Info",JOptionPane.YES_NO_CANCEL_OPTION);
					if(ret==JOptionPane.YES_OPTION) {
						name.setText(null);
						showname.setText(null);
						path.setText(null);
						texture.setText(null);
						color.setText(null);
					}
				}
			});
			menu.add(add);
			save=new JButton("保存当前拔刀");save.setBounds(x+bw+bi, y+20*h, bw, bh);
			save.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					XCDJsonInfo info=new  XCDJsonInfo(sa,standby,duration,Integer.parseInt(color.getText()),
					 iswitched,damage,name.getText(),showname.getText(),path.getText(),texture.getText(),null,false,null);
					int ret=json.addToJson(info);
					switch(ret) {
						case 1:
							JOptionPane.showMessageDialog(null, "配置已经保存.");break;
						case 0:
							JOptionPane.showMessageDialog(null, "输入不完整！.");break;
						case -1:
							JOptionPane.showMessageDialog(null, "配置文件写入失败！！.");break;
						case -2:
							JOptionPane.showMessageDialog(null, "拔刀重写完成！");break;
							
					}
				}
			});
			menu.add(save);
			delete=new JButton("删除选中拔刀");delete.setBounds(x+2*bw+2*bi, y+20*h, bw, bh);
			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(list.getSelectedValue()!=null) {
						int ret=JOptionPane.showConfirmDialog(null, "你确定删除选中拔刀么","XCustomizedBlade Info",JOptionPane.YES_NO_CANCEL_OPTION);
						if(ret==JOptionPane.YES_OPTION) {
							int r=json.deleteToJson(list.getSelectedValue());
							if(r!=1) {
								JOptionPane.showMessageDialog(null, "拔刀已删除！");
							}else {
								JOptionPane.showMessageDialog(null, "删除失败！");
							}
						}						
					}else {
						JOptionPane.showMessageDialog(null, "未选中拔刀！");
					}

				}
			});
			menu.add(delete);
			detail=new JButton("编辑详细信息");detail.setBounds(x+w+w/2+tw, y+300, 200, bh);
			EasyCreateBlade tranTemp=this;
			detail.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					detailWindow=new EasyMoreDetail(tranTemp);
					detailWindow.sa=sa;detailWindow.iswitched=iswitched;
					detailWindow.iswitchB.setSelected(iswitched);
					detailWindow.setVisible(true);
				}
			});
			menu.add(detail);
		}
	}
	public void refreshData(String[] listname,ConfigJsonReader json,XCDJsonInfo[] info) {
		for(int i=0;i<json.datalen;i++) {
			try {
				listname[i]=info[i].bladename;
				System.out.println("XCustomizedBlade:Read blade "+listname[i]);
			}catch(NullPointerException e) {
				continue;
			}

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
class LinkLabel extends JLabel {
	/*Code from CSDN,author _cha1R
	 https://blog.csdn.net/tanjiayqq/article/details/17062275
	 */
    private static final long serialVersionUID = 1L;
    private String text;
    private URL link = null;
    private Color preColor = null;
    public LinkLabel(String vText, String vLink) {
        super("<html>" + vText + "</html>");
        this.text = vText;
        try {
            if (!vLink.startsWith("http://"))
                vLink = "http://" + vLink;
            this.link = new URL(vLink);
        } catch (MalformedURLException err) {
            err.printStackTrace();
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor
                        .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                if (preColor != null)
                    LinkLabel.this.setForeground(preColor);
                LinkLabel.this.setText("<html>" + text + "</html>");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor
                        .getPredefinedCursor(Cursor.HAND_CURSOR));
                preColor = LinkLabel.this.getForeground();
                LinkLabel.this.setForeground(Color.BLUE);
                LinkLabel.this.setText("<html><u>" + text + "</u></html>");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(link.toURI());
                } catch (IOException err) {
                    err.printStackTrace();
                } catch (URISyntaxException err) {
                    err.printStackTrace();
                }
            }
        });
    }
}
