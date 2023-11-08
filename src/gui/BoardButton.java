package gui;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;

public class BoardButton extends JButton {
    private int draw;
    private ImageIcon icon;

    /**
     * Private constructor.
     * @param icon
     */
    private BoardButton(ImageIcon icon) {
        super("");
        draw = 1;
        this.icon = icon;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getDraw() {
        return draw;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension d = this.getSize();
        int strokeSize = d.width/10;
        ((Graphics2D) g).setStroke(new BasicStroke(strokeSize));
        g.setColor(new Color(100,100,100));

        switch (this.draw) {
            case 1 -> { // draw default cross section
                g.drawLine(0, d.height/2, d.width, d.height/2);
                g.drawLine(d.width/2, 0, d.width/2, d.height);
            }
            case 2 -> {
                g.drawOval(strokeSize, strokeSize, d.width - strokeSize * 2, d.height - strokeSize * 2);
            }
            case 3 -> {
                g.drawLine(0, d.height/2, d.width, d.height/2);
                g.drawLine(d.width/2, 0, d.width/2, d.height);
                g.setColor(Color.pink);
                g.fillOval(strokeSize,strokeSize,d.width - strokeSize*2, d.height - strokeSize*2);
            }
            case 4 -> { // png stone
                g.drawLine(0, d.height/2, d.width, d.height/2);
                g.drawLine(d.width/2, 0, d.width/2, d.height);
                Image scaledStone = getScaledImage(icon.getImage(), this.getWidth(), this.getHeight());
                g.drawImage(scaledStone,0,0,null);
            }
        }
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public static JButton generateBoardButton() {
        return generateBoardButton(new ImageIcon("res/stone.png"));
    }

    public static JButton generateBoardButton(ImageIcon icon) {
        BoardButton button = new BoardButton(icon);
        button.setModel(new FixedStateButtonModel());
        button.setOpaque(false);
        button.setMargin(new Insets(0,0,0,0)); // these 3 make button transparent
        button.setBorder(new LineBorder(new Color(255, 0, 0,0)));
        button.setBackground(new Color(0,0,0,0));
        button.setFocusPainted(false);


        button.addMouseListener( new ButtonsListener());
        button.addActionListener(e -> button.draw = 4);
        return button;
    }

    /**
     * A button model that changes the default properties of the button.
     * It prevents highlighting when hovered, and when being pressed.
     */
    private static class FixedStateButtonModel extends DefaultButtonModel {

        @Override
        public boolean isPressed() {
            return false;
        }

        @Override
        public boolean isRollover() {
            return false;
        }

        @Override
        public void setRollover(boolean b) {
            //NOOP
        }

    }
}
