package model.repository;

import model.Ticket;

import java.util.ArrayList;

public interface TicketRepository {

    public void add(Ticket ticket);

    public ArrayList<Ticket> getListTicket();

    public boolean storeTicket(Ticket ticket);
}
