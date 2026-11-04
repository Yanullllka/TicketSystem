package tickets.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tickets.model.Customer;
import tickets.model.Event;
import tickets.model.Ticket;
import tickets.repository.TicketRepository;

public class TicketService {

    private static final Logger logger =
            LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public Ticket buyTicket(
            int ticketId,
            Event event,
            Customer customer
    ) {

        logger.info("Запрошена покупка билета. ID билета: {}", ticketId);

        if (event.getAvailableSeats() <= 0) {
            logger.warn(
                    "Покупка билета отклонена: нет свободных мест. ID билета: {}",
                    ticketId
            );

            throw new IllegalStateException(
                    "Невозможно купить билет: свободных мест нет"
            );
        }

        try {
            event.reserveSeat();

            Ticket ticket = new Ticket(
                    ticketId,
                    event,
                    customer
            );

            repository.save(ticket);

            logger.info(
                    "Билет успешно куплен. ID билета: {}",
                    ticketId
            );

            return ticket;

        } catch (RuntimeException exception) {
            logger.error(
                    "Ошибка при покупке билета. ID билета: {}",
                    ticketId,
                    exception
            );

            throw exception;
        }
    }

    public void cancelTicket(int ticketId) {

        Ticket ticket = repository.findById(ticketId);

        if (ticket == null) {
            logger.warn(
                    "Не удалось отменить билет: билет не найден. ID: {}",
                    ticketId
            );

            throw new IllegalArgumentException(
                    "Билет не найден"
            );
        }

        Event event = ticket.getEvent();

        repository.delete(ticketId);

        event.releaseSeat();

        logger.info(
                "Билет успешно отменён. ID билета: {}",
                ticketId
        );
    }

    public Ticket findTicket(int ticketId) {
        return repository.findById(ticketId);
    }
}