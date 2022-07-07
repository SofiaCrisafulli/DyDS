package view;

import javax.swing.*;

public class CreatorOfFrame {
    protected static void createFrame(JSkiRentingPriceViewImpl JSkiRentingPriceViewImp) {
        JFrame frame = new JFrame("");
        frame.setContentPane(JSkiRentingPriceViewImp.getContent());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
