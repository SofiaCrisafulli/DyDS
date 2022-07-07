package view;

import controller.JSkiRentingPriceControllerImpl;
import model.JSkiRentingModel;
import model.JSkiRentingModule;

import javax.swing.*;

public class JSkiRentingPriceViewImpl implements JSkiRentingPriceView {
    private JButton calculateBtn;
    private JLabel priceLbl;
    private JPanel content;
    private JTextArea feesTextArea;
    private JSpinner spinnerHs;
    private JSpinner spinnerMins;
    private JTabbedPane tabbedPane1;
    private JTextArea transactionsTextArea;

    private JSkiRentingPriceControllerImpl parkingPriceController;
    private JSkiRentingModel JSkiRentingModel = JSkiRentingModule.getInstance().getParkingModel();

    public JSkiRentingPriceViewImpl(JSkiRentingPriceControllerImpl parkingPriceController) {

        this.parkingPriceController = parkingPriceController;

        initListeners();
        updatePriceField();
    }

    private void initListeners() {
        calculateBtn.addActionListener(e -> requestPrice());

        tabbedPane1.addChangeListener(changeEvent -> {
            if (tabbedPane1.getSelectedIndex() == 1)
                transactionsTextArea.setText(JSkiRentingModel.getFormatedTickets());
        });
    }

    public String getShowedPrice() {
        return priceLbl.getText();
    }

    private void updatePriceField() {
        feesTextArea.setText(JSkiRentingModel.getFormatedFees());
    }

    public void updatePriceResult(float price) {
        priceLbl.setText("$" + price);
    }

    private void requestPrice() {

        int hs = Integer.parseInt(spinnerHs.getValue().toString());
        if (hs < 0) {
            spinnerHs.setValue(0);
            hs = 0;
        }

        int mins = Integer.parseInt(spinnerMins.getValue().toString());
        if (mins < 0) {
            spinnerMins.setValue(0);
            mins = 0;
        }

        if (mins >= 60) {
            spinnerMins.setValue(mins % 60);
            mins = mins % 60;
        }

        parkingPriceController.onEventCalculate(hs * 60 + mins);
    }

    public JPanel getContent() {
        return content;
    }
}
