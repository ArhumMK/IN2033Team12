package Backend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarketingData {
    private List<Show> shows = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Screening> screenings = new ArrayList<>();
    private List<Film> films = new ArrayList<>();
    private List<Meeting> meetings = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();
    private List<GroupSale> groupSales = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<FoLMember> folMembers = new ArrayList<>();
    private List<HeldSeat> heldSeats = new ArrayList<>();
    private List<TicketSale> ticketSales = new ArrayList<>();
    private List<FilmOrder> filmOrders = new ArrayList<>();

    // Singleton instance
    private static MarketingData instance = new MarketingData();

    private MarketingData() {
        // Initialize with sample data
        initializeSampleData();
    }

    public static MarketingData getInstance() {
        return instance;
    }

    private void initializeSampleData() {
        // Sample Films
        films.add(new Film(1, "Action Movie", "Action"));
        films.add(new Film(2, "Drama Film", "Drama"));

        // Sample Screenings
        screenings.add(new Screening(1, 1, "2025-04-11", "19:00", "Theater 3"));
        screenings.add(new Screening(2, 2, "2025-04-13", "17:00", "Theater 1"));

        // Sample Shows
        shows.add(
                new Show(1, "2025-04-10", "18:00", "The Great Show", "Theater 1", "20.0/10%", "A great show", null, 1));
        shows.add(new Show(2, "2025-04-12", "20:00", "Movie Night", "Theater 2", "15.0/5%", "Movie night event", 1,
                null));

        // Sample Clients
        clients.add(new Client(1, "John Doe", "123 Main St", "john@example.com"));
        clients.add(new Client(2, "Jane Smith", "456 Oak Ave", "jane@example.com"));

        // Sample Meetings
        meetings.add(new Meeting(1, 1, "2025-04-10", "09:00", "Room 101"));
        meetings.add(new Meeting(2, 2, "2025-04-11", "11:00", "Room 102"));

        // Sample Invoices
        invoices.add(new Invoice(1, 1, 100.0));
        invoices.add(new Invoice(2, 2, 150.0));

        // Sample Groups
        groups.add(new Group(1, "School Group", "Alice Brown"));
        groups.add(new Group(2, "Corporate Team", "Bob Wilson"));

        // Sample Group Sales
        groupSales.add(new GroupSale(1, 1, 1, 20));
        groupSales.add(new GroupSale(2, 2, 2, 15));

        // Sample FoL Members
        folMembers.add(new FoLMember(1, true, 15.0));
        folMembers.add(new FoLMember(2, false, 0.0));

        // Sample Held Seats
        heldSeats.add(new HeldSeat(1, 1, "A1-A5"));
        heldSeats.add(new HeldSeat(2, 2, "B1-B3"));

        // Sample Ticket Sales
        ticketSales.add(new TicketSale(1, 1, 5, 100.0));
        ticketSales.add(new TicketSale(2, 2, 3, 45.0));

        // Sample Film Orders
        filmOrders.add(new FilmOrder(1, 1, "2025-04-01"));
        filmOrders.add(new FilmOrder(2, 2, "2025-04-02"));
    }

    // CRUD Methods for Show
    public List<Show> getShows() {
        return new ArrayList<>(shows);
    }

    public Show getShowById(int id) {
        return shows.stream().filter(show -> show.id == id).findFirst().orElse(null);
    }

    public void addShow(Show show) {
        validateShow(show);
        shows.add(show);
    }

    public void updateShow(int id, Show updatedShow) {
        validateShow(updatedShow);
        Show existingShow = getShowById(id);
        if (existingShow == null) {
            throw new IllegalArgumentException("Show with ID " + id + " not found.");
        }
        existingShow.date = updatedShow.date;
        existingShow.startTime = updatedShow.startTime;
        existingShow.name = updatedShow.name;
        existingShow.venue = updatedShow.venue;
        existingShow.priceDiscount = updatedShow.priceDiscount;
        existingShow.description = updatedShow.description;
        existingShow.screeningId = updatedShow.screeningId;
        existingShow.filmId = updatedShow.filmId;
        validateShow(existingShow); // Re-validate after update
    }

    public void deleteShow(int id) {
        Show show = getShowById(id);
        if (show == null) {
            throw new IllegalArgumentException("Show with ID " + id + " not found.");
        }
        // Check for dependencies
        if (groupSales.stream().anyMatch(gs -> gs.showId == id) ||
                ticketSales.stream().anyMatch(ts -> ts.showId == id) ||
                heldSeats.stream().anyMatch(hs -> hs.showId == id)) {
            throw new IllegalStateException("Cannot delete Show with ID " + id
                    + " because it has associated Group Sales, Ticket Sales, or Held Seats.");
        }
        shows.remove(show);
    }

    private void validateShow(Show show) {
        if (show.date == null || show.date.isEmpty())
            throw new IllegalArgumentException("Show date cannot be null or empty.");
        if (show.startTime == null || show.startTime.isEmpty())
            throw new IllegalArgumentException("Show start time cannot be null or empty.");
        if (show.name == null || show.name.isEmpty())
            throw new IllegalArgumentException("Show name cannot be null or empty.");
        if (show.venue == null || show.venue.isEmpty())
            throw new IllegalArgumentException("Show venue cannot be null or empty.");
        if (show.priceDiscount == null || show.priceDiscount.isEmpty())
            throw new IllegalArgumentException("Show price/discount cannot be null or empty.");
        if (show.description == null || show.description.isEmpty())
            throw new IllegalArgumentException("Show description cannot be null or empty.");
        // Enforce XOR relationship
        if (show.screeningId != null && show.filmId != null) {
            throw new IllegalArgumentException(
                    "A Show can be associated with either a Screening or a Film, but not both.");
        }
        if (show.screeningId != null && getScreeningById(show.screeningId) == null) {
            throw new IllegalArgumentException("Screening with ID " + show.screeningId + " does not exist.");
        }
        if (show.filmId != null && getFilmById(show.filmId) == null) {
            throw new IllegalArgumentException("Film with ID " + show.filmId + " does not exist.");
        }
    }

    // CRUD Methods for Client
    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    public Client getClientById(int id) {
        return clients.stream().filter(client -> client.id == id).findFirst().orElse(null);
    }

    public void addClient(Client client) {
        validateClient(client);
        clients.add(client);
    }

    public void updateClient(int id, Client updatedClient) {
        validateClient(updatedClient);
        Client existingClient = getClientById(id);
        if (existingClient == null) {
            throw new IllegalArgumentException("Client with ID " + id + " not found.");
        }
        existingClient.name = updatedClient.name;
        existingClient.address = updatedClient.address;
        existingClient.email = updatedClient.email;
    }

    public void deleteClient(int id) {
        Client client = getClientById(id);
        if (client == null) {
            throw new IllegalArgumentException("Client with ID " + id + " not found.");
        }
        // Check for dependencies
        if (meetings.stream().anyMatch(m -> m.clientId == id) ||
                invoices.stream().anyMatch(i -> i.clientId == id) ||
                folMembers.stream().anyMatch(f -> f.clientId == id)) {
            throw new IllegalStateException("Cannot delete Client with ID " + id
                    + " because it has associated Meetings, Invoices, or FoL membership.");
        }
        clients.remove(client);
    }

    private void validateClient(Client client) {
        if (client.name == null || client.name.isEmpty())
            throw new IllegalArgumentException("Client name cannot be null or empty.");
        if (client.address == null || client.address.isEmpty())
            throw new IllegalArgumentException("Client address cannot be null or empty.");
        if (client.email == null || client.email.isEmpty())
            throw new IllegalArgumentException("Client email cannot be null or empty.");
    }

    // CRUD Methods for Screening
    public List<Screening> getScreenings() {
        return new ArrayList<>(screenings);
    }

    public Screening getScreeningById(int id) {
        return screenings.stream().filter(screening -> screening.id == id).findFirst().orElse(null);
    }

    public void addScreening(Screening screening) {
        validateScreening(screening);
        screenings.add(screening);
    }

    public void updateScreening(int id, Screening updatedScreening) {
        validateScreening(updatedScreening);
        Screening existingScreening = getScreeningById(id);
        if (existingScreening == null) {
            throw new IllegalArgumentException("Screening with ID " + id + " not found.");
        }
        existingScreening.filmId = updatedScreening.filmId;
        existingScreening.date = updatedScreening.date;
        existingScreening.time = updatedScreening.time;
        existingScreening.venue = updatedScreening.venue;
        validateScreening(existingScreening);
    }

    public void deleteScreening(int id) {
        Screening screening = getScreeningById(id);
        if (screening == null) {
            throw new IllegalArgumentException("Screening with ID " + id + " not found.");
        }
        // Check for dependencies
        if (shows.stream().anyMatch(s -> s.screeningId != null && s.screeningId == id)) {
            throw new IllegalStateException(
                    "Cannot delete Screening with ID " + id + " because it is associated with a Show.");
        }
        screenings.remove(screening);
    }

    private void validateScreening(Screening screening) {
        if (getFilmById(screening.filmId) == null) {
            throw new IllegalArgumentException("Film with ID " + screening.filmId + " does not exist.");
        }
        if (screening.date == null || screening.date.isEmpty())
            throw new IllegalArgumentException("Screening date cannot be null or empty.");
        if (screening.time == null || screening.time.isEmpty())
            throw new IllegalArgumentException("Screening time cannot be null or empty.");
        if (screening.venue == null || screening.venue.isEmpty())
            throw new IllegalArgumentException("Screening venue cannot be null or empty.");
    }

    // CRUD Methods for Film
    public List<Film> getFilms() {
        return new ArrayList<>(films);
    }

    public Film getFilmById(int id) {
        return films.stream().filter(film -> film.id == id).findFirst().orElse(null);
    }

    public void addFilm(Film film) {
        validateFilm(film);
        films.add(film);
    }

    public void updateFilm(int id, Film updatedFilm) {
        validateFilm(updatedFilm);
        Film existingFilm = getFilmById(id);
        if (existingFilm == null) {
            throw new IllegalArgumentException("Film with ID " + id + " not found.");
        }
        existingFilm.title = updatedFilm.title;
        existingFilm.genre = updatedFilm.genre;
    }

    public void deleteFilm(int id) {
        Film film = getFilmById(id);
        if (film == null) {
            throw new IllegalArgumentException("Film with ID " + id + " not found.");
        }
        // Check for dependencies
        if (screenings.stream().anyMatch(s -> s.filmId == id) ||
                shows.stream().anyMatch(s -> s.filmId != null && s.filmId == id) ||
                filmOrders.stream().anyMatch(fo -> fo.filmId == id)) {
            throw new IllegalStateException("Cannot delete Film with ID " + id
                    + " because it is associated with Screenings, Shows, or Film Orders.");
        }
        films.remove(film);
    }

    private void validateFilm(Film film) {
        if (film.title == null || film.title.isEmpty())
            throw new IllegalArgumentException("Film title cannot be null or empty.");
        if (film.genre == null || film.genre.isEmpty())
            throw new IllegalArgumentException("Film genre cannot be null or empty.");
    }

    // CRUD Methods for Meeting
    public List<Meeting> getMeetings() {
        return new ArrayList<>(meetings);
    }

    public Meeting getMeetingById(int id) {
        return meetings.stream().filter(meeting -> meeting.id == id).findFirst().orElse(null);
    }

    public void addMeeting(Meeting meeting) {
        validateMeeting(meeting);
        meetings.add(meeting);
    }

    public void updateMeeting(int id, Meeting updatedMeeting) {
        validateMeeting(updatedMeeting);
        Meeting existingMeeting = getMeetingById(id);
        if (existingMeeting == null) {
            throw new IllegalArgumentException("Meeting with ID " + id + " not found.");
        }
        existingMeeting.clientId = updatedMeeting.clientId;
        existingMeeting.date = updatedMeeting.date;
        existingMeeting.time = updatedMeeting.time;
        existingMeeting.room = updatedMeeting.room;
        validateMeeting(existingMeeting);
    }

    public void deleteMeeting(int id) {
        Meeting meeting = getMeetingById(id);
        if (meeting == null) {
            throw new IllegalArgumentException("Meeting with ID " + id + " not found.");
        }
        meetings.remove(meeting);
    }

    private void validateMeeting(Meeting meeting) {
        if (getClientById(meeting.clientId) == null) {
            throw new IllegalArgumentException("Client with ID " + meeting.clientId + " does not exist.");
        }
        if (meeting.date == null || meeting.date.isEmpty())
            throw new IllegalArgumentException("Meeting date cannot be null or empty.");
        if (meeting.time == null || meeting.time.isEmpty())
            throw new IllegalArgumentException("Meeting time cannot be null or empty.");
        if (meeting.room == null || meeting.room.isEmpty())
            throw new IllegalArgumentException("Meeting room cannot be null or empty.");
    }

    // CRUD Methods for Invoice
    public List<Invoice> getInvoices() {
        return new ArrayList<>(invoices);
    }

    public Invoice getInvoiceById(int id) {
        return invoices.stream().filter(invoice -> invoice.id == id).findFirst().orElse(null);
    }

    public void addInvoice(Invoice invoice) {
        validateInvoice(invoice);
        invoices.add(invoice);
    }

    public void updateInvoice(int id, Invoice updatedInvoice) {
        validateInvoice(updatedInvoice);
        Invoice existingInvoice = getInvoiceById(id);
        if (existingInvoice == null) {
            throw new IllegalArgumentException("Invoice with ID " + id + " not found.");
        }
        existingInvoice.clientId = updatedInvoice.clientId;
        existingInvoice.amount = updatedInvoice.amount;
        validateInvoice(existingInvoice);
    }

    public void deleteInvoice(int id) {
        Invoice invoice = getInvoiceById(id);
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice with ID " + id + " not found.");
        }
        invoices.remove(invoice);
    }

    private void validateInvoice(Invoice invoice) {
        if (getClientById(invoice.clientId) == null) {
            throw new IllegalArgumentException("Client with ID " + invoice.clientId + " does not exist.");
        }
        if (invoice.amount <= 0)
            throw new IllegalArgumentException("Invoice amount must be greater than 0.");
    }

    // CRUD Methods for Group Sale
    public List<GroupSale> getGroupSales() {
        return new ArrayList<>(groupSales);
    }

    public GroupSale getGroupSaleById(int id) {
        return groupSales.stream().filter(gs -> gs.id == id).findFirst().orElse(null);
    }

    public void addGroupSale(GroupSale groupSale) {
        validateGroupSale(groupSale);
        groupSales.add(groupSale);
    }

    public void updateGroupSale(int id, GroupSale updatedGroupSale) {
        validateGroupSale(updatedGroupSale);
        GroupSale existingGroupSale = getGroupSaleById(id);
        if (existingGroupSale == null) {
            throw new IllegalArgumentException("Group Sale with ID " + id + " not found.");
        }
        existingGroupSale.groupId = updatedGroupSale.groupId;
        existingGroupSale.showId = updatedGroupSale.showId;
        existingGroupSale.numberOfTickets = updatedGroupSale.numberOfTickets;
        validateGroupSale(existingGroupSale);
    }

    public void deleteGroupSale(int id) {
        GroupSale groupSale = getGroupSaleById(id);
        if (groupSale == null) {
            throw new IllegalArgumentException("Group Sale with ID " + id + " not found.");
        }
        groupSales.remove(groupSale);
    }

    private void validateGroupSale(GroupSale groupSale) {
        if (getGroupById(groupSale.groupId) == null) {
            throw new IllegalArgumentException("Group with ID " + groupSale.groupId + " does not exist.");
        }
        if (getShowById(groupSale.showId) == null) {
            throw new IllegalArgumentException("Show with ID " + groupSale.showId + " does not exist.");
        }
        if (groupSale.numberOfTickets <= 0)
            throw new IllegalArgumentException("Number of tickets must be greater than 0.");
    }

    // CRUD Methods for Group
    public List<Group> getGroups() {
        return new ArrayList<>(groups);
    }

    public Group getGroupById(int id) {
        return groups.stream().filter(group -> group.id == id).findFirst().orElse(null);
    }

    public void addGroup(Group group) {
        validateGroup(group);
        groups.add(group);
    }

    public void updateGroup(int id, Group updatedGroup) {
        validateGroup(updatedGroup);
        Group existingGroup = getGroupById(id);
        if (existingGroup == null) {
            throw new IllegalArgumentException("Group with ID " + id + " not found.");
        }
        existingGroup.name = updatedGroup.name;
        existingGroup.contactPerson = updatedGroup.contactPerson;
    }

    public void deleteGroup(int id) {
        Group group = getGroupById(id);
        if (group == null) {
            throw new IllegalArgumentException("Group with ID " + id + " not found.");
        }
        // Check for dependencies
        if (groupSales.stream().anyMatch(gs -> gs.groupId == id)) {
            throw new IllegalStateException(
                    "Cannot delete Group with ID " + id + " because it has associated Group Sales.");
        }
        groups.remove(group);
    }

    private void validateGroup(Group group) {
        if (group.name == null || group.name.isEmpty())
            throw new IllegalArgumentException("Group name cannot be null or empty.");
        if (group.contactPerson == null || group.contactPerson.isEmpty())
            throw new IllegalArgumentException("Group contact person cannot be null or empty.");
    }

    // CRUD Methods for Ticket Sale
    public List<TicketSale> getTicketSales() {
        return new ArrayList<>(ticketSales);
    }

    public TicketSale getTicketSaleById(int id) {
        return ticketSales.stream().filter(ts -> ts.id == id).findFirst().orElse(null);
    }

    public void addTicketSale(TicketSale ticketSale) {
        validateTicketSale(ticketSale);
        ticketSales.add(ticketSale);
    }

    public void updateTicketSale(int id, TicketSale updatedTicketSale) {
        validateTicketSale(updatedTicketSale);
        TicketSale existingTicketSale = getTicketSaleById(id);
        if (existingTicketSale == null) {
            throw new IllegalArgumentException("Ticket Sale with ID " + id + " not found.");
        }
        existingTicketSale.showId = updatedTicketSale.showId;
        existingTicketSale.numberOfTickets = updatedTicketSale.numberOfTickets;
        existingTicketSale.totalAmount = updatedTicketSale.totalAmount;
        validateTicketSale(existingTicketSale);
    }

    public void deleteTicketSale(int id) {
        TicketSale ticketSale = getTicketSaleById(id);
        if (ticketSale == null) {
            throw new IllegalArgumentException("Ticket Sale with ID " + id + " not found.");
        }
        ticketSales.remove(ticketSale);
    }

    private void validateTicketSale(TicketSale ticketSale) {
        if (getShowById(ticketSale.showId) == null) {
            throw new IllegalArgumentException("Show with ID " + ticketSale.showId + " does not exist.");
        }
        if (ticketSale.numberOfTickets <= 0)
            throw new IllegalArgumentException("Number of tickets must be greater than 0.");
        if (ticketSale.totalAmount <= 0)
            throw new IllegalArgumentException("Total amount must be greater than 0.");
    }

    // CRUD Methods for Held Seat
    public List<HeldSeat> getHeldSeats() {
        return new ArrayList<>(heldSeats);
    }

    public HeldSeat getHeldSeatById(int id) {
        return heldSeats.stream().filter(hs -> hs.id == id).findFirst().orElse(null);
    }

    public void addHeldSeat(HeldSeat heldSeat) {
        validateHeldSeat(heldSeat);
        heldSeats.add(heldSeat);
    }

    public void updateHeldSeat(int id, HeldSeat updatedHeldSeat) {
        validateHeldSeat(updatedHeldSeat);
        HeldSeat existingHeldSeat = getHeldSeatById(id);
        if (existingHeldSeat == null) {
            throw new IllegalArgumentException("Held Seat with ID " + id + " not found.");
        }
        existingHeldSeat.showId = updatedHeldSeat.showId;
        existingHeldSeat.seats = updatedHeldSeat.seats;
        validateHeldSeat(existingHeldSeat);
    }

    public void deleteHeldSeat(int id) {
        HeldSeat heldSeat = getHeldSeatById(id);
        if (heldSeat == null) {
            throw new IllegalArgumentException("Held Seat with ID " + id + " not found.");
        }
        heldSeats.remove(heldSeat);
    }

    private void validateHeldSeat(HeldSeat heldSeat) {
        if (getShowById(heldSeat.showId) == null) {
            throw new IllegalArgumentException("Show with ID " + heldSeat.showId + " does not exist.");
        }
        if (heldSeat.seats == null || heldSeat.seats.isEmpty())
            throw new IllegalArgumentException("Held seats cannot be null or empty.");
    }

    // CRUD Methods for FoL Member
    public List<FoLMember> getFolMembers() {
        return new ArrayList<>(folMembers);
    }

    public FoLMember getFolMemberByClientId(int clientId) {
        return folMembers.stream().filter(f -> f.clientId == clientId).findFirst().orElse(null);
    }

    public void addFolMember(FoLMember folMember) {
        validateFolMember(folMember);
        folMembers.add(folMember);
    }

    public void updateFolMember(int clientId, FoLMember updatedFolMember) {
        validateFolMember(updatedFolMember);
        FoLMember existingFolMember = getFolMemberByClientId(clientId);
        if (existingFolMember == null) {
            throw new IllegalArgumentException("FoL Member with client ID " + clientId + " not found.");
        }
        existingFolMember.isMember = updatedFolMember.isMember;
        existingFolMember.discount = updatedFolMember.discount;
    }

    public void deleteFolMember(int clientId) {
        FoLMember folMember = getFolMemberByClientId(clientId);
        if (folMember == null) {
            throw new IllegalArgumentException("FoL Member with client ID " + clientId + " not found.");
        }
        folMembers.remove(folMember);
    }

    private void validateFolMember(FoLMember folMember) {
        if (getClientById(folMember.clientId) == null) {
            throw new IllegalArgumentException("Client with ID " + folMember.clientId + " does not exist.");
        }
        if (folMember.discount < 0)
            throw new IllegalArgumentException("Discount cannot be negative.");
    }

    // CRUD Methods for Film Order
    public List<FilmOrder> getFilmOrders() {
        return new ArrayList<>(filmOrders);
    }

    public FilmOrder getFilmOrderById(int id) {
        return filmOrders.stream().filter(fo -> fo.id == id).findFirst().orElse(null);
    }

    public void addFilmOrder(FilmOrder filmOrder) {
        validateFilmOrder(filmOrder);
        filmOrders.add(filmOrder);
    }

    public void updateFilmOrder(int id, FilmOrder updatedFilmOrder) {
        validateFilmOrder(updatedFilmOrder);
        FilmOrder existingFilmOrder = getFilmOrderById(id);
        if (existingFilmOrder == null) {
            throw new IllegalArgumentException("Film Order with ID " + id + " not found.");
        }
        existingFilmOrder.filmId = updatedFilmOrder.filmId;
        existingFilmOrder.orderDate = updatedFilmOrder.orderDate;
        validateFilmOrder(existingFilmOrder);
    }

    public void deleteFilmOrder(int id) {
        FilmOrder filmOrder = getFilmOrderById(id);
        if (filmOrder == null) {
            throw new IllegalArgumentException("Film Order with ID " + id + " not found.");
        }
        filmOrders.remove(filmOrder);
    }

    private void validateFilmOrder(FilmOrder filmOrder) {
        if (getFilmById(filmOrder.filmId) == null) {
            throw new IllegalArgumentException("Film with ID " + filmOrder.filmId + " does not exist.");
        }
        if (filmOrder.orderDate == null || filmOrder.orderDate.isEmpty())
            throw new IllegalArgumentException("Film order date cannot be null or empty.");
    }

    // Entity Classes
    public static class Show {
        int id;
        String date;
        String startTime;
        String name;
        String venue;
        String priceDiscount;
        String description;
        Integer screeningId; // Optional
        Integer filmId; // Optional

        public Show(int id, String date, String startTime, String name, String venue, String priceDiscount,
                String description, Integer screeningId, Integer filmId) {
            this.id = id;
            this.date = date;
            this.startTime = startTime;
            this.name = name;
            this.venue = venue;
            this.priceDiscount = priceDiscount;
            this.description = description;
            this.screeningId = screeningId;
            this.filmId = filmId;
        }
    }

    public static class Client {
        int id;
        String name;
        String address;
        String email;

        public Client(int id, String name, String address, String email) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.email = email;
        }
    }

    public static class Screening {
        int id;
        int filmId;
        String date;
        String time;
        String venue;

        public Screening(int id, int filmId, String date, String time, String venue) {
            this.id = id;
            this.filmId = filmId;
            this.date = date;
            this.time = time;
            this.venue = venue;
        }
    }

    public static class Film {
        int id;
        String title;
        String genre;

        public Film(int id, String title, String genre) {
            this.id = id;
            this.title = title;
            this.genre = genre;
        }
    }

    public static class Meeting {
        int id;
        int clientId;
        String date;
        String time;
        String room;

        public Meeting(int id, int clientId, String date, String time, String room) {
            this.id = id;
            this.clientId = clientId;
            this.date = date;
            this.time = time;
            this.room = room;
        }
    }

    public static class Invoice {
        int id;
        int clientId;
        double amount;

        public Invoice(int id, int clientId, double amount) {
            this.id = id;
            this.clientId = clientId;
            this.amount = amount;
        }
    }

    public static class GroupSale {
        int id;
        int groupId;
        int showId;
        int numberOfTickets;

        public GroupSale(int id, int groupId, int showId, int numberOfTickets) {
            this.id = id;
            this.groupId = groupId;
            this.showId = showId;
            this.numberOfTickets = numberOfTickets;
        }
    }

    public static class Group {
        int id;
        String name;
        String contactPerson;

        public Group(int id, String name, String contactPerson) {
            this.id = id;
            this.name = name;
            this.contactPerson = contactPerson;
        }
    }

    public static class FoLMember {
        int clientId;
        boolean isMember;
        double discount;

        public FoLMember(int clientId, boolean isMember, double discount) {
            this.clientId = clientId;
            this.isMember = isMember;
            this.discount = discount;
        }
    }

    public static class HeldSeat {
        int id;
        int showId;
        String seats;

        public HeldSeat(int id, int showId, String seats) {
            this.id = id;
            this.showId = showId;
            this.seats = seats;
        }
    }

    public static class TicketSale {
        int id;
        int showId;
        int numberOfTickets;
        double totalAmount;

        public TicketSale(int id, int showId, int numberOfTickets, double totalAmount) {
            this.id = id;
            this.showId = showId;
            this.numberOfTickets = numberOfTickets;
            this.totalAmount = totalAmount;
        }
    }

    public static class FilmOrder {
        int id;
        int filmId;
        String orderDate;

        public FilmOrder(int id, int filmId, String orderDate) {
            this.id = id;
            this.filmId = filmId;
            this.orderDate = orderDate;
        }
    }
}