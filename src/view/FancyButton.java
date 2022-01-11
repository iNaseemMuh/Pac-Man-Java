package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
/**
 * This page is a special design for button
 */
public class FancyButton extends JLabel implements MouseListener {

    ActionListener myAL;

    public FancyButton(String str, float size){
        super(str);
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/crackman.ttf")).deriveFont(size);
            this.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
//        this.setFont(new Font("DIALOG", Font.BOLD, 25));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setForeground(Color.yellow);
        this.setOpaque(false);
        this.addMouseListener(this);
    }

    public void addActionListener(ActionListener al){
        myAL = al;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        myAL.actionPerformed(new ActionEvent(this,501,""));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setForeground(new Color(253, 3, 3));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setForeground(Color.yellow);
    }
}
