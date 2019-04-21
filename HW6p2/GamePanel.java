import java.awt.Color;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class GamePanel extends JPanel {
    private World world = null;

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        int height = this.getHeight();
        int width = this.getWidth()- 50;

        int drawHeight = height/this.getHeight();
        int drawWidth = width/this.getWidth();

        int min = Math.min(drawWidth,drawHeight);

        g.setColor(Color.BLACK);
        for (int i = 0; i < world.getHeight(); i++) {
            for (int j = 0; j < world.getWidth(); j++) {
                if (world.getCell(j, i)) {
                    g.fillRect(j*min, i*min, min, min);
                }
            }
        }
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < world.getHeight(); i++) {
            for (int j = 0; j < world.getWidth(); j++) {
                if (world.getCell(j, i)) {
                    g.fillRect(min*j, min*i, min, min);
                }
            }
        }
        g.drawString("Generation " + world.getGenerationCount(), 135, this.getHeight() - 25);
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch, title);
        component.setBorder(tb);
    }

    public void display(World w) {
        world = w;
        repaint();
    }
}
