package me.kammoun.core.JComponentsPlus;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {

    public RoundButton(String label) {
        super(label);
        setOpaque(false); 
        setContentAreaFilled(false); 
        setFocusPainted(false); 
        setBorderPainted(false);
        setForeground(Color.WHITE); 
        setFont(new Font("Arial", Font.BOLD, 14));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setForeground(Color.black);

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
        		RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.RED); 
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30)); 
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }

    @Override
    public boolean contains(int x, int y) {
        return new RoundRectangle2D.Float(0, 0, 
        		getWidth(), getHeight(), 30, 30).contains(x, y);
    }
}