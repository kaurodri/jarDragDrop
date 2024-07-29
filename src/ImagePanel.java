import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    
    ImageIcon image = new ImageIcon("./images/card.png");

    final int IMG_WIDTH = image.getIconWidth();
    final int IMG_HEIGHT = image.getIconHeight();

    Point image_corner;
    Point previousPoint;

    ImagePanel() {

        image_corner = new Point(0,0);

        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);

        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        image.paintIcon(this, g, (int)image_corner.getX(), (int)image_corner.getY());

    }

    private class ClickListener extends MouseAdapter {

        public void mousePressed(MouseEvent evt) {
            previousPoint = evt.getPoint();
        }

    }

    private class DragListener extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent evt) {

            Point currentPoint = evt.getPoint();

            image_corner.translate((int)(currentPoint.getX() - previousPoint.getX()),
            (int)(currentPoint.getY() - previousPoint.getY()));

            previousPoint = currentPoint;
            repaint();
        }
    }
}