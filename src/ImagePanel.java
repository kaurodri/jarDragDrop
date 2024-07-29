import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private class ImageElement {
        ImageIcon image;
        Point position;

        ImageElement(String path, Point position) {
            this.image = new ImageIcon(path);
            this.position = position;
        }

        int getWidth() {
            return image.getIconWidth();
        }

        int getHeight() {
            return image.getIconHeight();
        }
    }

    private List<ImageElement> images;
    private Point previousPoint;
    private ImageElement selectedImage;
    private final String[] imagePaths = {
        "./images/card.png",
        "./images/card2.png",
        "./images/card3.png"
    };

    ImagePanel() {
        images = new ArrayList<>();
        images.add(new ImageElement(imagePaths[0], new Point(0, 0)));
        images.add(new ImageElement(imagePaths[1], new Point(100, 100)));

        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);

        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ImageElement imageElement : images) {
            imageElement.image.paintIcon(this, g, imageElement.position.x, imageElement.position.y);
        }
    }

    private class ClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent evt) {
            previousPoint = evt.getPoint();
            selectedImage = getImageAtPoint(previousPoint);
        }
    
        @Override
        public void mouseClicked(MouseEvent evt) {
            if (selectedImage != null && evt.getClickCount() == 2) { //verifica se é um clique duplo.
                //trocar a imagem apenas no clique duplo.
                int currentIndex = -1;
                for (int i = 0; i < imagePaths.length; i++) {
                    if (selectedImage.image.getDescription().equals(imagePaths[i])) {
                        currentIndex = i;
                        break;
                    }
                }
                if (currentIndex != -1) {
                    // Atualizar a imagem para a próxima no array (ou voltar para a primeira)
                    int nextIndex = (currentIndex + 1) % imagePaths.length;
                    selectedImage.image = new ImageIcon(imagePaths[nextIndex]);
                }
                repaint();
            }
        }
    }
    
    private class DragListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent evt) {
            if (selectedImage != null) {
                Point currentPoint = evt.getPoint();
                selectedImage.position.translate(
                    (int) (currentPoint.getX() - previousPoint.getX()),
                    (int) (currentPoint.getY() - previousPoint.getY())
                );
                previousPoint = currentPoint;
    
                //mover a imagem selecionada para o final da lista.
                images.remove(selectedImage);
                images.add(selectedImage);
    
                repaint();
            }
        }
    }

    private ImageElement getImageAtPoint(Point point) {
        for (int i = images.size() - 1; i >= 0; i--) {
            ImageElement imageElement = images.get(i);
            if (point.x >= imageElement.position.x && point.x <= imageElement.position.x + imageElement.getWidth()
                    && point.y >= imageElement.position.y && point.y <= imageElement.position.y + imageElement.getHeight()) {
                return imageElement;
            }
        }
        return null;
    }
}