package tickets.model;

public class Ticket {

    private final int id;
    private final Event event;
    private final Customer customer;

    public Ticket(int id, Event event, Customer customer) {
        this.id = id;
        this.event = event;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public Customer getCustomer() {
        return customer;
    }
}