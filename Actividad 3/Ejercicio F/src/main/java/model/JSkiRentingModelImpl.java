package model;

import Utils.WaitSimulator;
import controller.JSkiRentingPriceControllerImpl;
import fees.ComputeFee;

import java.util.ArrayList;

class JSkiRentingModelImpl implements JSkiRentingModel {

    private ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
    private JSkiRentingPriceControllerImpl jSkiRentingPriceControllerImpl;
    private ArrayList<JSkListenerModel> listeners = new ArrayList<>();
    private float finalPrice;
    private ComputeFee computeFee = new ComputeFee().getInstance();


    JSkiRentingModelImpl() {
        jSkiRentingPriceControllerImpl = new JSkiRentingPriceControllerImpl();
    }


    @Override
    public String getFormatedFees() {
        return jSkiRentingPriceControllerImpl.getComputeFee().callFeeString();
    }

    @Override
    public String getFormatedTickets() {
        String formatedTickets = "";
        for (Ticket ticket : ticketList)
            formatedTickets += ticket.id + ": " + ticket.totalPrice + "$, " + ticket.minutesUsed + "mins\n";
        return formatedTickets;
    }

    @Override
    public void calculatePrice(int minutes) {
        float price = computeFee.finalPrice(minutes);
        simulatedStoreinRepo(new Ticket(price, minutes));
        finalPrice = price;
        notifyChangesToListener();
    }

    private void simulatedStoreinRepo(Ticket ticket) {
        new Thread(() -> {
            WaitSimulator.simulateLongWait();
            ticketList.add(ticket);
        }).start();
    }

    private void notifyChangesToListener() {
        for (JSkListenerModel listener : listeners) {
            listener.itWasCalculatedFee();
        }
    }

    @Override
    public void addListener(JSkListenerModel listener) {
        listeners.add(listener);
    }

    @Override
    public float getFinalPrice() {
        return finalPrice;
    }
}
