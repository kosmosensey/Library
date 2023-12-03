package pro.sky.library.service.impl;

import pro.sky.library.constants.Shelf;
import pro.sky.library.model.Book;
import pro.sky.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.library.service.BookService;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private static final int NUMBER_OF_SHELVES = 5;
    private final BookRepository bookRepository;
    private final List<List<Book>> shelves;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.shelves = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SHELVES; i++) {
            shelves.add(new ArrayList<>());
        }
        distributeBooksOnShelves(bookRepository.findAll());
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        Collections.sort(allBooks, Comparator.comparing(Book::getBook));
        return allBooks;
    }

    @Override
    public Map<Shelf, List<Book>> getBooksOnShelves() {
        Map<Shelf, List<Book>> booksOnShelves = new HashMap<>();

        for (Shelf shelf : Shelf.values()) {
            booksOnShelves.put(shelf, shelves.get(shelf.ordinal()));
        }

        return booksOnShelves;
    }

    @Override
    public void distributeBooksOnShelves(List<Book> newBooks) {
        Collections.sort(newBooks, Comparator.comparing(Book::getBook));

        Map<Shelf, Integer> shelfCounts = new EnumMap<>(Shelf.class);
        for (Shelf shelf : Shelf.values()) {
            shelfCounts.put(shelf, 0);
        }

        for (List<Book> shelf : shelves) {
            shelfCounts.put(Shelf.values()[shelves.indexOf(shelf)], shelf.size());
        }

        for (Book book : newBooks) {
            Shelf targetShelf = Shelf.SHELF_1;
            int minCount = shelfCounts.get(Shelf.SHELF_1);

            for (int i = 0; i < NUMBER_OF_SHELVES - 1; i++) {
                Shelf currentShelf = Shelf.values()[i];
                Shelf nextShelf = Shelf.values()[i + 1];

                if (shelfCounts.get(currentShelf) >= shelfCounts.get(nextShelf)) {
                    if (shelfCounts.get(nextShelf) < minCount) {
                        targetShelf = nextShelf;
                        minCount = shelfCounts.get(nextShelf);
                    }
                }
            }

            int shelfIndex = targetShelf.ordinal();
            shelves.get(shelfIndex).add(book);
            book.setShelfNumber(shelfIndex + 1); // Устанавливаем номер полки
            shelfCounts.put(targetShelf, shelfCounts.get(targetShelf) + 1);
        }

        bookRepository.saveAll(newBooks); // Сохраняем все книги после установки номера полки
    }



    @Override
    public void addBookToShelf(Book book) {
        bookRepository.save(book);
        List<Book> books = bookRepository.findAll();
        distributeBooksOnShelves(books);
    }
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
        List<Book> books = bookRepository.findAll();
        distributeBooksOnShelves(books);
    }


}

