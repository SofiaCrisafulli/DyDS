package controller;

import Utils.WaitSimulator;
import fees.ComputeFee;
import model.Ticket;
import view.JSkiRentingPriceViewImpl;

import java.util.ArrayList;


public class JSkiRentingPriceControllerImpl implements JSkiRentingPriceController {

    private JSkiRentingPriceViewImpl JSkiRentingPriceView;
    private ComputeFee computeFee = ComputeFee.getInstance();
    private ArrayList<Ticket> repo = new ArrayList<>();


    public JSkiRentingPriceControllerImpl() {
        initFees();
    }

    private void initFees() {
        ComputeFee.getInstance().addFees(15, 300);
        ComputeFee.getInstance().addFees(60, 1000);
        ComputeFee.getInstance().addFees(5 * 60, 4000);
        ComputeFee.getInstance().addFees(3 * 60, 2500);
    }

    public void onEventCalculate(int minutes) {
        calculatePrice(this, minutes);
    }

    public void setParkingPriceView(JSkiRentingPriceViewImpl JSkiRentingPriceView) {
        this.JSkiRentingPriceView = JSkiRentingPriceView;
    }

    public void calculatePrice(JSkiRentingPriceController listener, int minutes) {
        float price = computeFee.finalPrice(minutes);
        simulatedStoreinRepo(new Ticket(price, minutes));
        listener.didUpdateParkingPrice(price);
    }

    private void simulatedStoreinRepo(Ticket ticket) {
        new Thread(() -> {
            WaitSimulator.simulateLongWait();
            repo.add(ticket);
        }).start();
    }

    public void start() {
        JSkiRentingPriceView = new JSkiRentingPriceViewImpl(this);
    }

    public void didUpdateParkingPrice(float price) {
        JSkiRentingPriceView.updatePriceResult(price);
    }

    public ComputeFee getComputeFee() {
        return computeFee;
    }
}
