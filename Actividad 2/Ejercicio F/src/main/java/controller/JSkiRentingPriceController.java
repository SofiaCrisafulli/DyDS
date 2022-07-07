package controller;

import fees.ComputeFee;
import model.JSkiRentingModel;
import model.JSkiRentingModule;
import view.JSkiRentingPriceView;


public class JSkiRentingPriceController implements JSkiRentingPriceUpdateListener {

    private JSkiRentingModel JSkiRentingModel = JSkiRentingModule.getInstance().getParkingModel();
    private JSkiRentingPriceView JSkiRentingPriceView;

    public JSkiRentingPriceController() {
        initFees();
    }

    private void initFees() {
        ComputeFee.getInstance().addFees(15, 300);
        ComputeFee.getInstance().addFees(60, 1000);
        ComputeFee.getInstance().addFees(5 * 60, 4000);
        ComputeFee.getInstance().addFees(3 * 60, 2500);
    }

    public void onEventCalculate(int minutes) {
        JSkiRentingModel.calculatePrice(this, minutes);
    }

    public void setParkingPriceView(JSkiRentingPriceView JSkiRentingPriceView) {
        this.JSkiRentingPriceView = JSkiRentingPriceView;
    }

    @Override
    public void didUpdateParkingPrice(float price) {
        JSkiRentingPriceView.updatePriceResult(price);
    }
}
