package controller;

public interface JSkiRentingPriceController {

    public void onEventCalculate(int minutes);

    public void didUpdateParkingPrice(float price);
}
