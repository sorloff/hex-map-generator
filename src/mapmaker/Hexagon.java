package mapmaker;

import java.awt.*;

/*
 * This class was modified slightly from the class provided here:
 * https://stackoverflow.com/questions/20734438/algorithm-to-generate-a-hexagonal-grid-with-coordinate-system
 * 
 * Some rounding errors were fixed to provide hexes that could be adjacent
 * 
 * Eventually I'll have to write my own Hexagon class to provide accurate,
 *  resizeable hexes, but I'm too lazy to do it right now.
 */
public class Hexagon extends Polygon {

  private static final long serialVersionUID = 1L;

  public static final int SIDES = 6;

  private Point[] points = new Point[SIDES];
  private Point center = new Point(0, 0);
  private int radius;
  private int rotation = 0;

  public Hexagon(Point center, int radius) {
    npoints = SIDES;
    xpoints = new int[SIDES];
    ypoints = new int[SIDES];

    this.center = center;
    this.radius = radius;

    updatePoints();
  }

  public Hexagon(int x, int y, int radius) {
    this(new Point(x, y), radius);
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;

    updatePoints();
  }

  public int getRotation() {
    return rotation;
  }

  public void setRotation(int rotation) {
    this.rotation = rotation;

    updatePoints();
  }

  public void setCenter(Point center) {
    this.center = center;

    updatePoints();
  }

  public void setCenter(int x, int y) {
    setCenter(new Point(x, y));
  }

  private double findAngle(double fraction) {
    return fraction * Math.PI * 2 + Math.toRadians((rotation + 180) % 360);
  }

  private Point findPoint(double angle) {
    int x = (int) Math.rint(center.x + Math.cos(angle) * radius);
    int y = (int) Math.rint(center.y + Math.sin(angle) * radius);

    return new Point(x, y);
  }

  protected void updatePoints() {
    for (int p = 0; p < SIDES; p++) {
      double angle = findAngle((double) p / SIDES);
      Point point = findPoint(angle);
      xpoints[p] = point.x;
      ypoints[p] = point.y;
      points[p] = point;
    }
  }

  public void draw(Graphics2D g, int x, int y, int lineThickness, int colorValue, boolean filled) {
    // Store before changing.
    Stroke tmpS = g.getStroke();
    Color tmpC = g.getColor();

    g.setColor(new Color(colorValue));
    g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

    if (filled) {
      g.fillPolygon(xpoints, ypoints, npoints);
    } else {
      g.drawPolygon(xpoints, ypoints, npoints);
    }

    // Set values to previous when done.
    g.setColor(tmpC);
    g.setStroke(tmpS);
  }
}
