package tickets.service;

import tickets.model.Customer;
import tickets.model.Event;
import tickets.model.Ticket;
import tickets.repository.TicketRepository;

public class TicketService {

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public Ticket buyTicket(
            int ticketId,
            Event event,
            Customer customer
    ) {

        if (event.getAvailableSeats() <= 0) {
            throw new IllegalStateException(
                    "Невозможно купить билет: свободных мест нет"
            );
        }

        event.reserveSeat();

        Ticket ticket = new Ticket(
                ticketId,
                event,
                customer
        );

        repository.save(ticket);

        return ticket;
    }

    public void cancelTicket(int ticketId) {

        Ticket ticket = repository.findById(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException(
                    "Билет не найден"
            );
        }

        Event event = ticket.getEvent();

        repository.delete(ticketId);

        event.releaseSeat();
    }

    public Ticket findTicket(int ticketId) {
        return repository.findById(ticketId);
    }
}