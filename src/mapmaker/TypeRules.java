package mapmaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/*
 * Holds rules for an arbitrary number of hex types
 * Exists just so that AdvancedInput doesn't have any real logic in it
 * Also gives a number of functions useful for dealing with a ruleSet
 */
public class TypeRules {
  private ArrayList<Rule> ruleSet;
  
  public final static String BORDER = "map_border";
  public final static String BLANK  = "blank_tile";
  
  public TypeRules() {
    this.ruleSet = new ArrayList<>();
    
    
    //creating blank and border rules    
    this.addRule(BLANK);
    this.addRule(BORDER);
    
    this.updateRule(BLANK, Double.toString(0), Double.toString(0), BLANK, "FFFFFF");
    this.updateRule(BORDER, Double.toString(0), Double.toString(0), BORDER, "808080");
    
    this.getRule(BLANK).setDefaultMod(0);
    this.getRule(BORDER).setDefaultMod(0);
    
    this.getRule(BLANK).setEmptyOther(BORDER);
    this.getRule(BORDER).setEmptyOther(BLANK);
    
    this.getRule(BLANK).createNewMod("CantSpawn");
    this.getRule(BORDER).createNewMod("CantSpawn");

  }
  
  public String[] getNames() {
    return Arrays.copyOf(ruleSet.stream().map(r -> r.getName()).toArray(),
			 ruleSet.size(), String[].class);
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
    
    Rule newRule = new Rule(name);
    ruleSet.forEach(r -> newRule.setEmptyOther(r.getName()));
    
    ruleSet.add(newRule);
  }
  
  //This is mostly just cleaning up input and parsing strings to numbers
  public void updateRule(String name, String base, String mod, String other, String color) {
    Double dBase  = Double.parseDouble(base);
    Double dMod   = Double.parseDouble(mod);
    int    iCol   = Integer.parseInt(color, 16); //TODO: check what color picker throws

    Rule r = ruleSet.stream().filter(s -> s.getName().equals(name)).findAny().get();
    
    r.updateRule(name, dBase, other, dMod, iCol);
  }
  
  public String createNewMod(String type, String modName) {
    return this.getRule(type).createNewMod(modName);
  }
  
  public void deleteMod(String type, String ID) {
    this.getRule(type).deleteMod(ID);
  }
  
  public void updateMod(String type, String ID, int val, String other) {
    this.getRule(type).updateMod(ID, val, other);
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
  
  //returns the bases in easy hashmap form for HexMap to use
  public HashMap<String, Double> getRuleBases() {
    HashMap<String, Double> bases = new HashMap<>();
    
    ruleSet.stream().forEach((r) -> bases.put(r.getName(), r.getBase()));
    
    return bases;
  }
  
  //returns the bases in easy hashmap form for MapDrawer to use
  public HashMap<String, Integer> getColors() {
    HashMap<String, Integer> colors = new HashMap<>();
    
    ruleSet.stream().forEach((r) -> colors.put(r.getName(), r.getColor()));
    
    return colors;
  }
  
  //returns true if the hex type is allowed given neighbors and coordinates
  //if you want to add special rules, make them in the Rule class and then add
  //  the logic here
  //TODO: move this logic into a new class
  public boolean allowed(String type, Hex[][] map, int[]coords) {
    return this.getRule(type).getModRules().checkAllowed(map, coords);
  }
}
