package monitoring;

import java.util.Scanner;

import tickets.model.Customer;
import tickets.model.Event;
import tickets.repository.InMemoryTicketRepository;
import tickets.repository.TicketRepository;
import tickets.service.TicketService;

public class MetricsDemo {

    public static void main(String[] args) throws Exception {

        // Запускаем HTTP-сервер метрик
        MetricsServer.startServer();

        // Создаём сервис продажи билетов
        TicketRepository repository = new InMemoryTicketRepository();
        TicketService ticketService = new TicketService(repository);

        // Мероприятие со свободными местами
        Event availableEvent = new Event(
                1,
                "Концерт",
                "Минск",
                100
        );

        // Мероприятие, на котором изначально одно место
        Event soldOutEvent = new Event(
                2,
                "Фестиваль",
                "Минск",
                1
        );

        // Создаём покупателя
        Customer customer = new Customer(
                1,
                "Иван Иванов",
                "ivan@example.com"
        );

        int ticketId = 1001;

        // Заранее продаём единственное место на фестиваль
        // Теперь свободных мест на нём не осталось
        ticketService.buyTicket(
                ticketId++,
                soldOutEvent,
                customer
        );

        // Создаём Scanner для ввода команд
        Scanner scanner = new Scanner(System.in);

        System.out.println("      МОНИТОРИНГ TICKETSYSTEM");

        while (true) {

            System.out.println();
            System.out.println("Выберите действие:");
            System.out.println("1 — Успешная покупка билета");
            System.out.println("2 — Неудачная покупка билета");
            System.out.println("3 — Показать текущие метрики");
            System.out.println("0 — Завершить работу");

            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    try {
                        ticketService.buyTicket(
                                ticketId++,
                                availableEvent,
                                customer
                        );

                        System.out.println(
                                "Покупка выполнена успешно!"
                        );

                    } catch (IllegalStateException exception) {
                        System.out.println(
                                "Покупка отклонена: "
                                        + exception.getMessage()
                        );
                    }
                    break;

                case "2":
                    try {
                        ticketService.buyTicket(
                                ticketId++,
                                soldOutEvent,
                                customer
                        );

                        System.out.println(
                                "Покупка выполнена успешно!"
                        );

                    } catch (IllegalStateException exception) {
                        System.out.println(
                                "Покупка отклонена: "
                                        + exception.getMessage()
                        );
                    }
                    break;

                case "3":
                    System.out.println(
                            MetricsConfig.getRegistry().scrape()
                    );
                    break;

                case "0":
                    System.out.println(
                            "Работа программы завершена."
                    );
                    return;

                default:
                    System.out.println(
                            "Неверный выбор. Попробуйте ещё раз."
                    );
            }
        }
    }
}