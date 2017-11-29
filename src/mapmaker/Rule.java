package mapmaker; 

import java.util.ArrayList;
import java.util.HashMap;
import mapmaker.modrules.ModRule;

//A single hex rule for a single hex type
//TODO: create "there's a new other hex type" method
public class Rule {
  private String name;
  private double base;
  private int    color;
  
  private HashMap<String, Double> mods;
  
  private ModList modList;
  
  private double thisMod  = 0.2;
  
  //holds modifiers, colors, and modifying rules for each hex
  public Rule(String name) {
    this.name    = name;
    this.color   = 0xFFFFFF; //TODO: add checking if this isn't set
    this.modList = new ModList();
    this.mods    = new HashMap<>();
    this.mods.put(name, thisMod);
  }
  
  //updates the rules for this type
  //specific set methods don't exist to reduce logic in the AdvancedInput class
  public void updateRule(String name, double base, String other, double mod, int color) {
    this.name  = name;
    this.base  = base;
    this.color = color;
    
    mods.put(other, mod);
  }
  
  public String createNewMod(String modName) {
    return this.modList.addRule(modName);
  }
  
  public void deleteMod(String ID) {
    this.modList.deleteRule(ID);
  }
  
  public void updateMod(String ID, int val, String other) {
    this.modList.updateRule(ID, val, other);
  }
  
  public String getName() {
    return name;
  }
  
  public int getColor() {
    return color;
  }

  
  public double getMod(String other) {
//    System.out.println(this.name + " checked for " + other);
    return mods.get(other);
  }
  
  public ModList getModRules() {
    return this.modList;
  }
  
  public void setEmptyOther(String name) {
    mods.put(name, -1*thisMod);
  }
  
  public void deleteOther(String name) {
    HashMap<String, ModRule> tempModRules = modList.getMods();
    
    for (String ID : tempModRules.keySet())
      if (tempModRules.get(ID).getOther().equals(name))
	modList.deleteRule(ID);
    
    mods.remove(name);
  }
  
  public double getBase() {
    return base;
  }
  
  //only necessary for blank/border hexes
  public void setDefaultMod(double mod) {
    this.thisMod = mod;
  }
}
