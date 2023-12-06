import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 * This class represents the highlighter functionality of the maps, which is triggered when a user searches for a POI. The POI
 * will be highlighted for 10 seconds upon submitting the search.
 * @author stephenkinsey
 */
public class Highlighter extends JComponent {
  /** rectangle which is used to highlight the POI */
  private Rectangle rect;
  
  /**
   * Class constructor which creates a highlighter object with the specified attributes.
   * @param x x coordinate on the top-left of the POI
   * @param y y coordinate of the top-left coordinate of the POI
   * @param width width of the rectangle
   * @param height height of the rectangle
   */
  public Highlighter(int x, int y, int width, int height) {
    this.rect = new Rectangle(x, y, width, height);
    setBounds(rect);
  }
  
  @Override
  /**
   * This class class overrides the default paintComponent() from Java and paints the highlighter.
   */
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(new Color(255, 255, 0, 128));
    g2d.fillRect(0, 0, getWidth(), getHeight());
    g2d.setColor(Color.YELLOW);
    g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    g2d.dispose();
  }
  
}