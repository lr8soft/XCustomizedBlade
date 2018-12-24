package net.lrsoft.xcustomizedblade.XCBUtil;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class XCBSelectBox extends AbstractListModel<String> implements ComboBoxModel<String>{
	String selecteditem=null;
	String[] info=null;
	public XCBSelectBox(String[] in) {
		this.info=in;
	}
	@Override
	public String getElementAt(int arg0) {
		return info[arg0];
	}

	@Override
	public int getSize() {
		return info.length;
	}

	@Override
	public Object getSelectedItem() {
		// TODO 自动生成的方法存根
		return selecteditem;
	}

	@Override
	public void setSelectedItem(Object arg0) {
		selecteditem=(String)arg0;
		
	}
}