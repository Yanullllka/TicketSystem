package tickets.repository;

import tickets.model.Ticket;

import java.util.List;

public interface TicketRepository {

    void save(Ticket ticket);

    Ticket findById(int id);

    void delete(int id);

    List<Ticket> findAll();
}