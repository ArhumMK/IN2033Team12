public class TestinAPI {
    public static void main(String[] args) {
        BoxOfficeImpl boxOffice = new BoxOfficeImpl();

        // Example: Get film details
        FilmDetails details = boxOffice.getFilmDetails("Inception");

        System.out.println("\n🎬 Film Info:");
        System.out.println("Title: " + details.getTitle());
        System.out.println("Director: " + details.getDirector());
        System.out.println("Genre: " + details.getGenre());
        System.out.println("Venue: " + details.getVenue());
        System.out.println("Scheduled: " + details.getSchedulingStatus());
    }
}
