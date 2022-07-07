package model;


public interface JSkiRentingModel {

    String getFormatedFees();

    String getFormatedTickets();

    void calculatePrice(int minutes);

    void addListener(JSkListenerModel listener);

    float getFinalPrice();
}
