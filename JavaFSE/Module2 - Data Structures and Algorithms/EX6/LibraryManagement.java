import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Book {
    private int bookId;
    private String title;
    private String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

public class LibraryManagementSystem {

    public static Book linearSearchByTitle(List<Book> books, String targetTitle) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(targetTitle)) {
                return book;
            }
        }
        return null;
    }

    public static Book binarySearchByTitle(List<Book> sortedBooks, String targetTitle) {
        int low = 0;
        int high = sortedBooks.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Book midBook = sortedBooks.get(mid);
            int comparison = midBook.getTitle().compareToIgnoreCase(targetTitle);

            if (comparison == 0) {
                return midBook;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        List<Book> library = new ArrayList<>();
        library.add(new Book(101, "The Hobbit", "J.R.R. Tolkien"));
        library.add(new Book(102, "1984", "George Orwell"));
        library.add(new Book(103, "To Kill a Mockingbird", "Harper Lee"));

        Book foundLinear = linearSearchByTitle(library, "1984");

        List<Book> sortedLibrary = new ArrayList<>(library);
        Collections.sort(sortedLibrary, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));

        Book foundBinary = binarySearchByTitle(sortedLibrary, "1984");
    }
}