package mapmaker;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/*
 * Handles drawing the actual image using the Graphics2D class
 * 
 * Unfortunately, I haven't figured out how to make the hexes look good unless:
 *  lineWidth = 1
 *  radius    = 50
 * But if you want to mess with those values they're hardcoded in makeMapImage::HexMap
*/
public class MapDrawer {

  private Hex[][] map;
  private HashMap<String, Integer> colors;
  private int radius, lineWidth, width, height;

  public MapDrawer(Hex[][] arrMap, int radius, int lineWidth, HashMap<String, Integer> colors) {
    this.colors = colors;
    
    this.map = new Hex[arrMap.length][arrMap[0].length];
    for (int i = 0; i < arrMap.length; i++) {
      for (int j = 0; j < arrMap[i].length; j++) {
	this.map[i][j] = arrMap[i][j];
      }
    }

    this.width = (int) Math.rint(radius * 1.5 * arrMap[0].length) + radius;
    this.height = (int) Math.rint(radius * 1.7 * arrMap.length) + 2*radius;

    this.radius = radius;
    this.lineWidth = lineWidth;
  }

  //creates hexmap.png
  public void mapHexes() {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();

    g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
    mapHexlper(g2d);

    try {
      String path = Paths.get(".").toAbsolutePath().normalize().toString();
      ImageIO.write(image, "png", new File(path + "/hexmap.png"));
    } catch (IOException e) {
      System.out.println("Program could not write to directory.");
      System.out.println("IOException: " + e);
    }
  }

  //paints each hexagon one by one
  private void mapHexlper(Graphics g) {
    //this exists so the first row/column are actually on the screen
    int offset = (int) Math.ceil(radius / 2) + radius;

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
	int x = -10;
	int y = -10;
	
	//These constants were plucked from the beard of a mountain sage.
	//Do not touch them.
	if (j % 2 == 0) {
	  x = (int) Math.rint(offset + j * radius * 1.5);
	  y = (int) Math.rint(offset + i * radius * 1.7);
	} else {
	  x = (int) Math.rint(offset + j * radius * 1.5);
	  y = (int) Math.rint(offset + i * radius * 1.7 + radius - 7);
	}

	drawHex(g, x, y, colors.get(map[i][j].getType()));
	
	//TODO: clean this up
	Graphics2D g2d = (Graphics2D) g;
	Hexagon hex = new Hexagon(x, y, radius);
	hex.draw(g2d, x, y, 0, colors.get(map[i][j].getType()), true); //fill
	hex.draw(g2d, x, y, lineWidth, 0x000000, false); //border

	g.setColor(new Color(0xFFFFFF));
      }
    }
  }

  //calls draw::Hexagon, and figures out what colors to use
  //DEPRECIATED
  private void drawHex(Graphics g, int x, int y, int type) {
    Graphics2D g2d = (Graphics2D) g;
    Hexagon hex = new Hexagon(x, y, radius);
    int color_val = 0xFF0000; //red

    switch (type) {
      case (0):
	color_val = 0x0000FF; //blue
	break;
      case (1):
	color_val = 0x008000; //green
	break;
      case (2):
	color_val = 0x808080; //gray
	break;
      case (3):
	color_val = 0x800080; //purple
	break;
    }

    hex.draw(g2d, x, y, 0, color_val, true); //fill
    hex.draw(g2d, x, y, lineWidth, 0x000000, false); //border

    g.setColor(new Color(0xFFFFFF));
  }

}
