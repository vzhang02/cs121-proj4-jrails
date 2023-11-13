package jrails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import books.Book;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

public class ModelTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model(){};
    }

    @Test
    public void id() {
        assertThat(model.id(), notNullValue());
        assert(model.id() == 0);
        assert((new Book()).id() == 0);
    }

    // @Test
    // public void bookTest_saveAndId() {
    //     Book b = new Book();
    //     b.title = "Programming Languages: Build, Prove, and Compare";
    //     b.author = "Norman Ramsey";
    //     b.num_copies = 999;
    //     // The book b exists in memory but isn't saved to the db
    //     assert(b.id() == 0);
    //     b.save(); // now the book is in the db
    //     b.num_copies = 42; // the book in the db still has 999 copies
    //     b.save(); // now the book in the db has 42 copies
    //     Book b2 = new Book();
    //     b2.title = "Programming Languages: Build, Prove, and Compare";
    //     b2.author = "Norman Ramsey";
    //     b2.num_copies = 999; // hm, same as other book
    //     assert(b2.id() == 0);
    //     b2.save(); // a second record is added to the database
    //     assert(b.id() != b2.id()); // every row has a globally unique id (int) column, so we can tell them apart
    //     Model.reset();
    // }

    // @Test
    // public void bookTest_destroy() {
    //     Book b = new Book();
    //     b.title = "Programming Languages: Build, Prove, and Compare";
    //     b.author = "Norman Ramsey";
    //     b.num_copies = 999;
    //     // The book b exists in memory but isn't saved to the db
    //     b.save(); // now the book is in the db
    //     b.num_copies = 42; // the book in the db still has 999 copies
    //     b.save(); // now the book in the db has 42 copies
    //     Book b2 = new Book();
    //     b2.title = "Programming Languages: Build, Prove, and Compare";
    //     b2.author = "Norman Ramsey";
    //     b2.num_copies = 999; // hm, same as other book
    //     try {
    //         b2.destroy();
    //     } catch (Exception ex) {
    //     }
    //     b2.save(); // a second record is added to the database
    //     b2.destroy();
    //     Model.reset();
    // }

    // @Test
    // public void bookTest_find() {
    //     Book b = Model.find(Book.class, 1);
    //     assert (b == null);
    //     b = new Book();
    //     b.title = "woah";
    //     b.author = "yuh";
    //     b.num_copies = 100;
    //     b.save();
    //     Book b2 = Model.find(Book.class, b.id());
    //     assert(b.title == b2.title);
    //     assert(b.author == b2.author);
    //     assert(b.num_copies == b2.num_copies);
    //     Model.reset();
    // }

    @Test 
    public void bookTest_all() {
        System.out.println("bookTest_all");
        Book b1 = new Book();
        b1.title = "book 1";
        Book b2 =  new Book();
        b2.title = "book 2";
        Book b3 =  new Book();
        b3.title = "book 3";
        List<Book> bs = Model.all(Book.class);
        assert (bs.isEmpty());
        b1.save();
        b2.save();
        b3.save();
        bs = Model.all(Book.class);
        assert(bs.size() == 3);
        Model.reset();
    }

    @After
    public void tearDown() throws Exception {
    }
}