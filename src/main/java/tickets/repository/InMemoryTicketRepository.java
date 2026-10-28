package tickets.repository;

import tickets.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTicketRepository implements TicketRepository {

    private final List<Ticket> tickets = new ArrayList<>();

    @Override
    public void save(Ticket ticket) {
        tickets.add(ticket);
    }

    @Override
    public Ticket findById(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }

        return null;
    }

    @Override
    public void delete(int id) {
        tickets.removeIf(ticket -> ticket.getId() == id);
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(tickets);
    }
}