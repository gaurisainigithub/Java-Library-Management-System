package org.example;

public class Transaction
{
    String bookId;
    String studentId;
    String studentName;

    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    @Override
    public String toString()
    {
        return "Transaction{" +
                "bookId='" + bookId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }

    public Transaction(String bookId , String studentId , String studentName)
    {
       this.bookId = bookId;
       this.studentId = studentId;
       this.studentName = studentName;
    }



}
