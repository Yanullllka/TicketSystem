package tickets;

import tickets.model.Customer;
import tickets.model.Event;
import tickets.repository.InMemoryTicketRepository;
import tickets.repository.TicketRepository;
import tickets.service.TicketService;

public class TicketApplication {

    public static void main(String[] args) {

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

        System.out.println("Мероприятие: " + event.getName());

        System.out.println(
                "Свободных мест: " +
                        event.getAvailableSeats()
        );

        service.buyTicket(
                1,
                event,
                customer
        );

        System.out.println(
                "После покупки: " +
                        event.getAvailableSeats()
        );

        service.cancelTicket(1);

        System.out.println(
                "После отмены: " +
                        event.getAvailableSeats()
        );
    }
}