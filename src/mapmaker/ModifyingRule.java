package mapmaker; 

//Modifying Rule class
public class ModifyingRule {
  boolean exists; //if hex type has this rule
  String  name;   //name/description of rule
  int     val;    //any special value associated with this rule

  public ModifyingRule(String name) {
    this.exists = false;
    this.name   = name;
    this.val    = new Integer(0); //TODO: add some checking for unset values
  }

  public void set(boolean exists, int val) {
    this.exists = exists;
    this.val    = val;
  }

  public boolean getExists() {
    return exists;
  }

  public int getVal() {
    return val;
  }
}