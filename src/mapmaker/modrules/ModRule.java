package mapmaker.modrules;

import mapmaker.Hex;

//to make a new ModRule, extend this class and then add the rule in ModList in:
//-getRulesForList under "BEGIN ADDING RULES"
//-addRule         under switch/case statement
public abstract class ModRule{  
  public static final String    DESC   = "Description Was Not Set!";
  public static final String    NAME   = "AbstractModRule";
  
  //TODO: figure out a better hack to get around this issue
  public String className;
  
  //first val is whether to show "val" textbox, second is for "other" box
  public static final boolean[] SCHEMA = {false, false};
  
  //some mod rules need integer values or the names of other hexes
  protected int     val    = 0;
  protected String  other  = ""; 
  
  public ModRule(String name) {
    this.className = name;
  }
  
  //returns true if type is allowed in current hex
  public abstract boolean allowed(Hex[][] map, int[] coords);
  
  public void setVal(int val) {
    this.val = val;
  }
  
  public int getVal() {
    return this.val;
  }
  
  public void setOther(String other) {
    this.other = other;
  }
  
  public String getOther() {
    return this.other;
  }
  
  public String getDesc() {
    return this.DESC;
  }
  
  public String getName() {
    return this.className;
  }
  
  public void set(String other, int val) {
    this.setVal(val);
    this.setOther(other);
  }
}
