package mapmaker;

import java.util.ArrayList;
import java.util.Optional;

/*
 * Holds rules for an arbitrary number of hex types
 * Exists just so that AdvancedInput doesn't have any real logic in it
 */
public class TypeRules {
  private ArrayList<Rule> ruleSet;
  
  public TypeRules() {
    this.ruleSet = new ArrayList<>();
  }
  
  public String[] getNames() {
    return (String[]) ruleSet.stream().map(r -> r.getName()).toArray();
  }
  
  public Rule getRule(String name) {
    Optional o = ruleSet.stream().filter(r -> r.getName().equals(name)).findAny();
    
    if (o.isPresent())
      return (Rule) o.get();
    
    System.out.println("Warning: TypeRules::getRule didn't find hex type \"" + name + "\"");
    return new Rule("ERROR");
  }
  
  public void addRule(String name) {
    ruleSet.forEach(r -> r.setEmptyOther(name));
    ruleSet.add(new Rule(name));
  }
  
  public void updateRule(String name, String base, String mod, String other,
			 boolean[] sRules, boolean[] gRules, String[] sVals, String[] gVals) {
    Double dBase  = Double.parseDouble(base);
    Double dMod   = Double.parseDouble(mod);
    int[]  iSVals = new int[sVals.length];
    int[]  iGVals = new int[gVals.length];
    
    for (int i = 0; i<sVals.length; i++) 
      iSVals[i] = sVals.equals("") ? new Integer(null) : Integer.parseInt(sVals[i]);
    
    for (int i = 0; i<gVals.length; i++) 
      iGVals[i] = gVals.equals("") ? new Integer(null) : Integer.parseInt(gVals[i]);
    
    Rule r = ruleSet.stream().filter(s -> s.getName().equals(name)).findAny().get();
    r.updateRule(name, dBase, other, dMod, sRules, iSVals, gRules, iGVals);
  }
  
  public boolean ruleExists(String name) {
    return ruleSet.stream().anyMatch(r -> r.getName().equals(name));
  }
  
  public void deleteRule(String name) {
    boolean found = ruleSet.removeIf(r -> r.getName().equals(name));
    
    ruleSet.forEach(r -> r.deleteOther(name));
    
    if (!found)
      System.out.println("Warning: TypeRules::deleteRule didn't find \"" + name + "\"");
  }
}
