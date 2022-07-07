package controller;

import view.CreatorOfFrame;
import view.JSkiRentingPriceViewImpl;


public class Main extends CreatorOfFrame {

  public static void main(String[] args) {

    JSkiRentingPriceControllerImpl parkingPriceController =
        new JSkiRentingPriceControllerImpl();

    JSkiRentingPriceViewImpl JSkiRentingPriceView =
        new JSkiRentingPriceViewImpl(parkingPriceController);

    parkingPriceController.setParkingPriceView(JSkiRentingPriceView);

    createFrame(JSkiRentingPriceView);
  }
}
