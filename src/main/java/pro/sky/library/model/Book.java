package pro.sky.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "library")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "shelfNumber")
    private int shelfNumber;
    @Column(name = "book")
    private String book;
    @Column(name = "author")
    private String author;
    @Column(name = "publicationDate")
    private LocalDate publicationDate;

    public Book() {
        // Пустой конструктор
    }

    public Book(Long id, int shelfNumber, String title, String author, LocalDate publicationDate) {
        this.id = id;
        this.shelfNumber = shelfNumber;
        this.book = title;
        this.author = author;
        this.publicationDate = publicationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book1 = (Book) o;
        return shelfNumber == book1.shelfNumber && Objects.equals(id, book1.id) && Objects.equals(book, book1.book) && Objects.equals(author, book1.author) && Objects.equals(publicationDate, book1.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shelfNumber, book, author, publicationDate);
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", shelfNumber=" + shelfNumber +
               ", book='" + book + '\'' +
               ", author='" + author + '\'' +
               ", publicationDate=" + publicationDate +
               '}';
    }
}
