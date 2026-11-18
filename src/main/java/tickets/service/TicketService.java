package tickets.service;

import io.micrometer.core.instrument.Timer;
import monitoring.MetricsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tickets.model.Customer;
import tickets.model.Event;
import tickets.model.Ticket;
import tickets.repository.TicketRepository;

public class TicketService {

    private static final Logger LOGGER =
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

        LOGGER.info("Запрошена покупка билета. ID билета: {}", ticketId);

        // Увеличиваем общее количество попыток покупки
        MetricsConfig.getTotalOperations().increment();

        // Начинаем измерять время выполнения операции
        Timer.Sample sample = Timer.start(MetricsConfig.getRegistry());

        try {

            if (event.getAvailableSeats() <= 0) {
                LOGGER.warn(
                        "Покупка билета отклонена: нет свободных мест. ID билета: {}",
                        ticketId
                );

                // Увеличиваем количество неудачных покупок
                MetricsConfig.getErrorPurchases().increment();

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

                LOGGER.info(
                        "Билет успешно куплен. ID билета: {}",
                        ticketId
                );

                // Увеличиваем количество успешных покупок
                MetricsConfig.getSuccessfulPurchases().increment();

                return ticket;

            } catch (RuntimeException exception) {

                // Учитываем ошибку при выполнении покупки
                MetricsConfig.getErrorPurchases().increment();

                LOGGER.error(
                        "Ошибка при покупке билета. ID билета: {}",
                        ticketId,
                        exception
                );

                throw exception;
            }

        } finally {

            // Сохраняем время выполнения операции
            sample.stop(MetricsConfig.getPurchaseDuration());
        }
    }

    public void cancelTicket(int ticketId) {

        Ticket ticket = repository.findById(ticketId);

        if (ticket == null) {
            LOGGER.warn(
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

        LOGGER.info(
                "Билет успешно отменён. ID билета: {}",
                ticketId
        );
    }

    public Ticket findTicket(int ticketId) {
        return repository.findById(ticketId);
    }
}