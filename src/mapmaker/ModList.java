package mapmaker; 

//handles logic having to do with "modification rules" - cannot spawn next to, etc

import java.util.HashMap;
import java.util.UUID;

import mapmaker.modrules.*;

//holds list of modifying rules and useful methods for interacting with rules
public class ModList {  
  //(unique ID, mod)
  private final HashMap<String, ModRule> rules;
  
  public ModList() {
    this.rules = new HashMap<>();
  }
  
  //TODO: make rules not accessible to the public like this
  public HashMap<String, ModRule> getMods() {
    return rules;
  }
  
  //returns a set of rules for the user to pick new rules from
  public static HashMap<String, String> getFullRules() {
    HashMap<String, String> r = new HashMap<>();
    
    //BEGIN ADDING RULES
    r.put(CantSpawn.DESC,        CantSpawn.NAME);
    r.put(CantSpawnAbove.DESC,   CantSpawnAbove.NAME);
    r.put(CantSpawnBeneath.DESC, CantSpawnBeneath.NAME);
    r.put(CantSpawnNextTo.DESC,  CantSpawnNextTo.NAME);
    r.put(OnlySpawnNextTo.DESC,  OnlySpawnNextTo.NAME);
    //END ADDING RULES
    
    return r;
  }
  
  public static String[] getRuleDescs() {
    return getFullRules().keySet().stream().toArray(String[]::new);
  }
  
  public static String[] getRuleNames() {
    return getFullRules().values().stream().toArray(String[]::new);
  }
  
  public String addRule(String ruleName) {
    String ID = UUID.randomUUID().toString();
    ModRule m;
    
    switch(ruleName) {
      case CantSpawn.NAME:
	m = new CantSpawn();
	break;
      case CantSpawnAbove.NAME:
	m = new CantSpawnAbove();
	break;
      case CantSpawnBeneath.NAME:
	m = new CantSpawnBeneath();
	break;
      case CantSpawnNextTo.NAME:
	m = new CantSpawnNextTo();
	break;
      case OnlySpawnNextTo.NAME:
	m = new OnlySpawnNextTo();
	break;
      default:
	System.out.println("ModList::addRule didn't find rule: " + ruleName);
	m = new BlankRule();
    }
    
    rules.put(ID, m);
    return ID;
  }
  
  public void updateRule(String ID, int val, String other) {
    rules.get(ID).set(other, val);
  }
  
  public void deleteRule(String ID) {
    rules.remove(ID);
  }
  
  public boolean checkAllowed(Hex[][] map, int[] coords) {
    return rules.values().stream().allMatch(r -> r.allowed(map, coords));
  }
}
