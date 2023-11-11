package gui;

import omok.Board;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardButton extends JButton {
    private int draw;
    public int x;
    public int y;
    private Color stoneColor;

    private BoardButton(int x, int y) {
        super("");
        draw = 1;
        this.x = x;
        this.y = y;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public void setStoneColor(Color stoneColor) {
        this.stoneColor = stoneColor;
    }

    public int getDraw() {
        return draw;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        Dimension d = this.getSize();
        int strokeSize = d.width/10;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(strokeSize));
        g2d.setColor(new Color(100,100, 100, 255));

        switch (this.draw) {
            case 1 -> { // draw default cross section
                g2d.drawLine(0, d.height/2, d.width, d.height/2);
                g2d.drawLine(d.width/2, 0, d.width/2, d.height);
            }
            case 2 -> {
                g2d.setColor(stoneColor);
                int diameter = Math.min(d.width, d.height) - strokeSize*2;
                int x_offset = (d.width - diameter)/2;
                int y_offset = (d.height - diameter)/2;
                g2d.drawOval(x_offset, y_offset, diameter, diameter);
            }
            case 3 -> {
                g2d.drawLine(0, d.height/2, d.width, d.height/2);
                g2d.drawLine(d.width/2, 0, d.width/2, d.height);
                g2d.setColor(stoneColor);

                int diameter = Math.min(d.width,d.height) - strokeSize*2;
                int x_offset = (d.width - diameter)/2;
                int y_offset = (d.height - diameter)/2;
                g2d.fillOval(x_offset,y_offset,diameter, diameter);
            }
        }
    }

    public static BoardButton generateBoardButton(int x, int y) {
        BoardButton button = new BoardButton(x,y);
        button.setModel(new FixedStateButtonModel());
        button.setOpaque(false);
        button.setMargin(new Insets(0,0,0,0)); // these 3 make button transparent
        button.setBorder(new LineBorder(new Color(255, 0, 0,0)));
        button.setBackground(new Color(0,0,0,0));
        button.setFocusPainted(false);


        //button.addMouseListener( new ButtonsListener() );
        button.addActionListener(e -> button.setEnabled(false));
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
