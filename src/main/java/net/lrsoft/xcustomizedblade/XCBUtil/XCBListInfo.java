package net.lrsoft.xcustomizedblade.XCBUtil;

import javax.swing.AbstractListModel;

public class XCBListInfo extends AbstractListModel<String>{
	private String[] info=null;
	public XCBListInfo(String[] input) {
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