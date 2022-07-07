package model.repository;

import model.Ticket;

import java.util.ArrayList;

public class NonPersistentTicketsRepository implements TicketRepository{

    private ArrayList<Ticket> listTicket = new ArrayList<>();

    @Override
    public void add(Ticket ticket) {
        listTicket.add(ticket);
    }

    @Override
    public ArrayList<Ticket> getListTicket() {
        return listTicket;
    }

    @Override
    public boolean storeTicket(Ticket ticket) {
        listTicket.removeIf(ticket :: areTheSame);
        listTicket.add(ticket);
        return true;
    }
}
