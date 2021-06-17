package library;
import java.io.Serializable;
import java.util.*;

import library.ReaderBase.*;

public class Bookset implements Serializable {
  public class Book implements Serializable{
    private int id;
    private String title;
    private String author;
    private String publisher;
    private Rent actRent;
    private int publicationYear;
    private int value;
    private boolean isInBookset;

    /**Constructor of a Book class instance
    Allows to add book to bookset.
    @see addToBookset
    */
    public Book(int id, String title, String author, String publisher, int publicationYear, int value){
      this.id = id;
      this.title = title;
      this.author = author;
      this.publisher = publisher;
      this.actRent = null;
      this.publicationYear = publicationYear;
      this.value = value;
      this.isInBookset = true; ///General presence, doesn't change when book is borrowed
    }

    public int getID(){
      return this.id;
    }

    public String getTitle(){
      return this.title;
    }

    public String getAuthor(){
      return this.author;
    }

    public String getPublisher(){
      return this.publisher;
    }

    public int getPublicationYear(){
      return this.publicationYear;
    }

    public int getValue(){
      return this.value;
    }

    public Rent getRent(){
      return this.actRent;
    }

    /**Allows to edit basic information about book
    @attention Doesn't impact the current Rent nor its presence in Bookset*/
    public void editBookentry(String title, String author, String publisher, int publicationYear, byte value){
      this.title = title;
      this.author = author;
      this.publisher = publisher;
      this.publicationYear = publicationYear;
      this.value = value;
    }

    public void setRent(Rent actRent){
      this.actRent = actRent;
    }

    ///Gives information whether the book is available to be rent \equiv no one has it borrowed
    public boolean isAvailableToRent(){
      return actRent == null && isInBookset;
    }
    
    /**Assisting function in book removal
    @see removeBookFromBookset(int id)*/
    public void removeBook()
    {
      this.isInBookset = false;
    }
  }

  private ArrayList<Book> set;
  private int booksNumber;

  public Bookset(){
    set = new ArrayList<Book>();
  }

  ///Adds book to the bookset
  ///@return Unique book's ID in the bookset
  public int addToBookset(String title, String author, String publisher, int publicationYear, int value){
    set.add(new Book(set.size() + 1, title, author, publisher, publicationYear, value));
    booksNumber++;
    return set.size();
  }

  ///Disables book's ability to be rented
  ///@warning Doesn't remove book's data from database
  public void removeFromBookset(int id) throws CurrentlyRentedException{
    Book tmp = set.get(id - 1);
    if(tmp.actRent != null)
      throw new CurrentlyRentedException();
    tmp.removeBook();
    booksNumber--;
  }

  public int getNumberOfActiveBooks(){
    return booksNumber;
  }

  public Book getBook(int id){
    return set.get(id - 1);
  }

  /** Searches for books with titles starting with given prefix
   * @return ArrayList<Book> of matching books
   */
  public ArrayList<Book> bookLookupByTitle(String titlePrefix){
    ArrayList<Book> res = new ArrayList<Book>();
    for(Book b: set)
      if(b.title.startsWith(titlePrefix) && b.isInBookset)
        res.add(b);
    return res;
  }

  public int getSize(){
    return set.size();
  }
}
