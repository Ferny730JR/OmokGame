package gui;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardButton extends JButton {
    private int draw;

    private BoardButton() {
        this("");
    }

    private BoardButton(String text) {
        super(text);
        draw = 1;
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
        int strokeWidth = d.width/10;
        ((Graphics2D) g).setStroke(new BasicStroke(strokeWidth));
        g.setColor(new Color(100,100,100));

        switch (this.draw) {
            case 1 -> { // draw default cross section
                g.drawLine(0, d.height/2, d.width, d.height/2);
                g.drawLine(d.width/2, 0, d.width/2, d.height);
            }
            case 2 -> g.drawOval(strokeWidth,strokeWidth,d.width-strokeWidth*2,d.height-strokeWidth*2);
            case 3 -> g.fillOval(strokeWidth,strokeWidth,d.width-strokeWidth*2,d.height-strokeWidth*2);
//            case 3 -> {
//                ImageIcon stone = new ImageIcon("stone.png");
//                g.drawImage(stone.getImage(),0,0,null);
//                this.setOpaque(true);
//                this.setIcon(stone);
//            }
        }
    }


    public static JButton generateBoardButton() {
        BoardButton button = new BoardButton();
        button.setOpaque(false);
        button.setMargin(new Insets(0,0,0,0));
        button.setBorder(new LineBorder(new Color(255, 0, 0,0)));
        ImageIcon icon = new ImageIcon(
                new BufferedImage(1024,1024, BufferedImage.TYPE_INT_ARGB));
        button.setIcon(icon);

        button.addMouseListener( new ButtonsListener(new Color(100,100,100)));
        button.addActionListener(e -> button.draw = 3);
        return button;
    }
}
