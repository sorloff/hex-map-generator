package mapmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * Handles the logic behind creating the hex map.
 */
public class HexMap {
  private final int[]     dim;
  private final Hex[][]   arrMap;
  private final TypeRules rules;
  private final Random    randomGen;
  private final String    standard;
  
  public HexMap(int[] dimensions, TypeRules rules, String standard) {
    this.dim = new int[2];
    this.dim[0] = dimensions[0];
    this.dim[1] = dimensions[1];
    this.rules  = rules;

    //size increase is to allow for "border hexes" which simplifies later logic
    this.arrMap = new Hex[dim[0] + 2][dim[1] + 2];
    
    this.randomGen = new Random();
    
    this.standard = standard;
    
    System.out.println("Here's what my ruleset looks like:");
    for (String s : rules.getNames()) {
      System.out.println("----------------------");
      System.out.println(s + ":");
      for (String other : rules.getNames())
	System.out.println(other + ": " + rules.getRule(s).getMod(other));
    }
  }

  public void makeMap() {
    for (int i = 0; i < arrMap.length; i++) {
      for (int j = 0; j < arrMap[i].length; j++) {
	arrMap[i][j] = new Hex();
      }
    }

    //setting border hexes to have border hex type
    for (Hex h : arrMap[0]) {
      h.setType(TypeRules.BORDER);
    }
    for (Hex h : arrMap[arrMap.length - 1]) {
      h.setType(TypeRules.BORDER);
    }
    for (Hex[] hA : arrMap) {
      hA[0].setType(TypeRules.BORDER);
      hA[hA.length - 1].setType(TypeRules.BORDER);
    }

    linkMap();
    populateMap();
  }

  //this links each hex object with its neighbors, so we can easily calculate
  //the probability equation later
  private void linkMap() {
    for (int i = 1; i <= dim[0]; i++) {
      for (int j = 1; j <= dim[1]; j++) {
	boolean shift = (j % 2 != 0); //note Hex class for neighbor positions
	Hex[] neighbors = {arrMap[i - 1][j],
			   arrMap[i + 1][j],
			   arrMap[shift ? i : i - 1][j + 1],
			   arrMap[shift ? i : i - 1][j - 1],
			   arrMap[shift ? i + 1 : i][j + 1],
			   arrMap[shift ? i + 1 : i][j - 1]};
	System.out.println("--------------");
	System.out.println("HEX: [" + i + ", " + j + "]");
	System.out.println("N: [" + (i-1) + ", " + j + "]");
	System.out.println("S: [" + (i+1) + ", " + j + "]");
	System.out.println("NE: [" + (shift ? i : i - 1) + ", " + (j+1) + "]");
	System.out.println("NW: [" + (shift ? i : i - 1) + ", " + (j-1) + "]");
	System.out.println("SE: [" + (shift ? i + 1 : i) + ", " + (j+1) + "]");
	System.out.println("SW: [" + (shift ? i + 1 : i) + ", " + (j-1) + "]");
	this.arrMap[i][j].setAllAdj(neighbors);
      }
    }
  }

  //calculate the type for each hex, in random order
  private void populateMap() {
    List<Integer> yRange = IntStream.range(1, dim[0] + 1).boxed().collect(Collectors.toList());
    List<Integer> xRange = IntStream.range(1, dim[1] + 1).boxed().collect(Collectors.toList());
    
    Collections.shuffle(yRange);
    Collections.shuffle(xRange);
    
    for (int i : yRange) {
      for (int j : xRange) {
	Hex h = arrMap[i][j];
	if (!h.getType().equals(TypeRules.BORDER)) {
	  int[] coords  = {i, j};
	  String type   = probHelper(h, coords);
	  h.setType(type);
	}
      }
    }
  }

  //return value to compare r.nextDouble to
  private String probHelper(Hex currentHex, int[] coords) {
   System.out.println("----------------------------");

    Hex[] neighbors               = currentHex.getAllAdj();
    HashMap<String, Double> bases = rules.getRuleBases();
    String type                   = this.standard;
    
    //change the base values in accordance with neighbors' mod values
    for (Hex h : neighbors) {
      String name = h.getType();
      Rule nRule  = rules.getRule(name);
      
      for (String s : rules.getNames()) { 
	double base = bases.get(s);
	double mod  = nRule.getMod(s);
	bases.put(s, base + mod);
      }
      
      System.out.println("n: " + name);
    }
    
    for (String name : rules.getNames())
      System.out.println(name + ": " + Double.toString(bases.get(name)));
    
    //remove options that aren't allowed
    ArrayList<String> useable = new ArrayList<>();
    
    for (String k : bases.keySet()) {
      if (rules.allowed(k, arrMap, coords))
	useable.add(k);
    }
    
    /////////BEGIN CALCULATION///////////
    boolean foundMin = false;
    double       min = Double.MAX_VALUE;
    
    for (String s : useable)
      if (bases.get(s) < 0)
	if (bases.get(s) < min) {
	  foundMin = true;
	  min      = bases.get(s);
	}
    
    double addVal = -1 * min;
    
    if (foundMin)
      for (String s : useable)
	bases.put(s, bases.get(s) + addVal);
    
    for (String name : useable)
      System.out.println(name + ": " + Double.toString(bases.get(name)));
    
    double max = 0.0;
    for (String s : useable)
      max += bases.get(s);
    
    double target  = randomGen.nextDouble()*max;
    double val     = 0.0;
    
    System.out.println("Random: " + Double.toString(target));
    
    System.out.println("Set was:");
    System.out.println(useable);

    
//    System.out.println("------------Vals I rolled are:");
    for (String k : useable) {
      double next = val + bases.get(k);
      //the second <= might screw with probabilities in a literally
      //infinitesimal degree of scenarios, but if there's only one possible
      //choice then the program will otherwise return blank
      if (val <= target && target <= next) {
	System.out.println("Chose: " + k);
	return k;
      }
      val = next;
    }
    
    System.out.println("program returned default tile!");
    
    return type;
  }

  //print the hex map into the console
  //TODO: make this compatible with new hex names
  public void printMap() {
    for (int i = 0; i < arrMap.length; i++) {
      String s = "";
      for (int j = 0; j < arrMap[i].length; j++) {
	s += arrMap[i][j].getType();
      }
      System.out.println(s);
    }
  }

  public void makeMapImage() {
    MapDrawer m = new MapDrawer(arrMap, 50, 1, rules.getColors());
    m.mapHexes();
    System.out.println("badda boom");

  }
}
