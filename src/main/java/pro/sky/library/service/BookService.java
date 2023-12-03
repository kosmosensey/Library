package pro.sky.library.service;

import pro.sky.library.constants.Shelf;
import pro.sky.library.model.Book;

import java.util.*;


public interface BookService {
    List<Book> getAllBooks();


    Map<Shelf, List<Book>> getBooksOnShelves();

    void distributeBooksOnShelves(List<Book> newBooks);

    void addBookToShelf(Book book);

    void deleteBookById(Long id);
}
