package org.example;

public class Book
{
    private String id;
    private String booktittle;
    private String bookauthor;
    private int totalCopies;
    private int availableCopies;

    public String getId()
    {
        return id;
    }

    public void setId(String id )
    {
        this.id = id;
    }

    public String getBooktittle()
    {
        return booktittle;
    }

    public void setBooktittle(String booktittle)
    {
        this.booktittle = booktittle;
    }

    public String getBookauthor()
    {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor)
    {
        this.bookauthor = bookauthor;
    }

    public int getTotalCopies()
    {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies)
    {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies()
    {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies)
    {
        this.availableCopies = availableCopies;
    }

    public Book(String id , String booktittle, String bookauthor , int totalCopies , int availableCopies)
    {
        this.id=id;
        this.booktittle=booktittle;
        this.bookauthor=bookauthor;
        this.totalCopies=totalCopies;
        this.availableCopies=availableCopies;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "id='" + id + '\'' +
                ", booktittle='" + booktittle + '\'' +
                ", bookauthor='" + bookauthor + '\'' +
                ", totalCopies='" + totalCopies + '\'' +
                ", availableCopies='" + availableCopies + '\'' +
                '}';
    }
}
