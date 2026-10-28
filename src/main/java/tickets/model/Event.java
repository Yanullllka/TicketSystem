package tickets.model;

public class Event {

    private final int id;
    private final String name;
    private final String location;
    private final int totalSeats;
    private int availableSeats;

    public Event(int id, String name, String location, int totalSeats) {
        if (totalSeats <= 0) {
            throw new IllegalArgumentException(
                    "Количество мест должно быть больше 0"
            );
        }

        this.id = id;
        this.name = name;
        this.location = location;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void reserveSeat() {
        if (availableSeats <= 0) {
            throw new IllegalStateException(
                    "Свободных мест нет"
            );
        }

        availableSeats--;
    }

    public void releaseSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
        }
    }
}