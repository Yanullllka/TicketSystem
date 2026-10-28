package tickets;

import org.junit.jupiter.api.Test;
import tickets.model.Customer;
import tickets.model.Event;
import tickets.repository.InMemoryTicketRepository;
import tickets.repository.TicketRepository;
import tickets.service.TicketService;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceTest {

    @Test
    void shouldBuyTicketSuccessfully() {

        TicketRepository repository =
                new InMemoryTicketRepository();

        TicketService service =
                new TicketService(repository);

        Event event = new Event(
                1,
                "Rock Festival",
                "Minsk Arena",
                100
        );

        Customer customer = new Customer(
                1,
                "Anna",
                "anna@example.com"
        );

        service.buyTicket(
                1,
                event,
                customer
        );

        assertEquals(
                99,
                event.getAvailableSeats()
        );

        assertNotNull(
                service.findTicket(1)
        );
    }

    @Test
    void shouldNotBuyTicketWhenNoSeatsAvailable() {

        TicketRepository repository =
                new InMemoryTicketRepository();

        TicketService service =
                new TicketService(repository);

        Event event = new Event(
                1,
                "Small Concert",
                "Club",
                1
        );

        Customer customer = new Customer(
                1,
                "Anna",
                "anna@example.com"
        );

        service.buyTicket(
                1,
                event,
                customer
        );

        assertThrows(
                IllegalStateException.class,
                () -> service.buyTicket(
                        2,
                        event,
                        customer
                )
        );
    }

    @Test
    void shouldReturnSeatAfterTicketCancellation() {

        TicketRepository repository =
                new InMemoryTicketRepository();

        TicketService service =
                new TicketService(repository);

        Event event = new Event(
                1,
                "Rock Festival",
                "Minsk Arena",
                100
        );

        Customer customer = new Customer(
                1,
                "Anna",
                "anna@example.com"
        );

        service.buyTicket(
                1,
                event,
                customer
        );

        assertEquals(
                99,
                event.getAvailableSeats()
        );

        service.cancelTicket(1);

        assertEquals(
                100,
                event.getAvailableSeats()
        );

        assertNull(
                service.findTicket(1)
        );
    }
}