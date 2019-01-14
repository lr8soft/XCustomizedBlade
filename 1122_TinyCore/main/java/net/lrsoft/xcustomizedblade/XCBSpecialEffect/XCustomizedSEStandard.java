package net.lrsoft.xcustomizedblade.XCBSpecialEffect;

public class XCustomizedSEStandard {
	public String SEName;public int SECost,SELevel;public String[] SEStep;
	public int[] SERuntime;public double[] SEDamage;
	public XCustomizedSEStandard(String name,int cost,int level,String[] step,int[] runtime,double[] damage) {
		this.SEName=name;this.SECost=cost;this.SELevel=level;this.SEStep=step;
		this.SERuntime=runtime;this.SEDamage=damage;
	}
	
}
