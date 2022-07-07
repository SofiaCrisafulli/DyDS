package view;

import controller.JSkiRentingPriceController;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JSkiRentingPriceController parkingPriceController =
                new JSkiRentingPriceController();

        JSkiRentingPriceView JSkiRentingPriceView =
                new JSkiRentingPriceView(parkingPriceController);

        parkingPriceController.setParkingPriceView(JSkiRentingPriceView);

        JFrame frame = new JFrame("");
        frame.setContentPane(JSkiRentingPriceView.content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
