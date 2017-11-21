package mapmaker; 

import java.util.HashMap;
import java.util.function.Function;

//A single hex rule
//TODO: create "there's a new other hex type" method
public class Rule {
  private String name;
  private double base;
  
  private HashMap<String, Double> mods;
  
  //Special rules pertaining to other hex types
  // key   = hexname
  // value = rule
  private HashMap<String, ModRule[]>  specialRules;
  
  //General rules for this hex type
  private ModRule[] generalRules;
  
  //indice where general rules start
  private final String[] specialDescs = {"Cannot Spawn Next To",
					 "Only Spawns Next To"};
  
  private final String[] generalDescs = {"Cannot Spawn Beneath:",
					 "Cannot Spawn Above:"};
  
  private final double otherMod = -0.2;
  private final double thisMod  = 0.2;
  
  public Rule(String name) {
    this.name    = name;
    this.mods    = new HashMap<>();
    this.mods.put(name, thisMod);
    
    this.specialRules = new HashMap<>();
    this.generalRules = initModRules(generalDescs);
    
    //initializing special rules for this hex type
    this.specialRules.put(name, initModRules(specialDescs));

  }
  
  //updates the rules for this type
  //specific set methods don't exist to reduce logic in the AdvancedInput class
  public void updateRule(String name, double base, String other, double mod, 
			 boolean[] sRules, int[] sVals, boolean gRules[], int[] gVals) {
    this.name = name;
    this.base = base;
    
    mods.put(other, mod);
    
    for (int i = 0; i<sRules.length; i++)
      specialRules.get(other)[i].set(sRules[i], sVals[i]);
    
    for (int i = 0; i<gRules.length; i++)
      generalRules[i].set(gRules[i], gVals[i]);
  }
  
  private ModRule[] initModRules(String[] rules) {
    ModRule[] modRules = new ModRule[rules.length];
    for (int i = 0; i<rules.length; i++)
      modRules[i] = new ModRule(rules[i]);
    return modRules;
  }
  
  public String getName() {
    return name;
  }
  
  public double getMod(String other) {
    return mods.get(other);
  }
  
  public Boolean[] getSpecials(String other) {
    return (Boolean[]) getModVar(specialRules.get(other), ModRule::getExists);
  }
  
  public Integer[] getSpecVals(String other) {
    return (Integer[]) getModVar(specialRules.get(other), ModRule::getVal);
  }
  
  public Boolean[] getGenerals() {
    return (Boolean[]) getModVar(generalRules, ModRule::getExists);
  }
  
  public Integer[] getGenVals() {
    return (Integer[]) getModVar(generalRules, ModRule::getVal);
  }
  
  //This is completely unnecessary, but I figured I'd get practice with lambdas
  //Creates an array out of ModRule variables using f as the get method
  private Object[] getModVar(ModRule[] a, Function<ModRule, Object> f) {
    Object[] arr = new Object[a.length];
    for (int i = 0; i<a.length; i++)
      arr[i] = f.apply(a[i]);
    
    return arr;
  }
  
  public void setEmptyOther(String name) {
    specialRules.put(name, initModRules(specialDescs));
  }
  
  public void deleteOther(String name) {
    specialRules.remove(name);
  }
  
  public String[] getSpecialDescs() {
    return specialDescs;
  }
  
  public String[] getGeneralDescs() {
    return generalDescs;
  }
}
