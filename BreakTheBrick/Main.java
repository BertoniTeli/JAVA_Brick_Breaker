package BreakTheBrick;
import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame GameWindow = new JFrame();
        Gameplay gameplay = new Gameplay();
        GameWindow.setBounds(10, 10, 720, 620);
        GameWindow.setTitle("BreakTheBrick!");
        GameWindow.setResizable(false);
        GameWindow.setVisible(true);
        GameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameWindow.add(gameplay);
    }
}
