package org.example;

import java.sql.Connection;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        Connection c = DBConnection.getConnection();
        if(c!=null)
        {
            System.out.println("Connected to Database Successfully");
        }
        else
        {
            System.out.println("Connection Failed");
        }
        /*
        LibraryDao libraryDao = new LibraryDao();
        //libraryDao.addBook("109" , "Python Developer" , "Robin Roberts" , 9);
        libraryDao.displayBooks();

        System.out.println("Searching of Books");
        libraryDao.searchByTittle("My Developer");

        //libraryDao.issueBook("103" , "18" , "tanvi Saini");
        //libraryDao.issueBook("108" , "11" , "Yashika Saini");

        //libraryDao.returnBook("104" , "16");
        //libraryDao.returnBook("103" , "20");
        libraryDao.displayTransaction();
        */
          LibraryDao libraryDao = new LibraryDao();
          Scanner scanner = new Scanner(System.in);
          int choice;

          System.out.println("========================Welcome to the Java Library System===========================");

          do
          {
              System.out.println("\n--------- MAIN MENU-----------");
              System.out.println("1. View All Books");
              System.out.println("2. Add a New Book Package");
              System.out.println("3. Search Book By Tittle");
              System.out.println("4. Issue Book (Log Student Details)");
              System.out.println("5. Return Book (Clear Student Debt)");
              System.out.println("6. View Student Borrow Record History");
              System.out.println("7. Exit");
              System.out.println("Enter Choice (1-7)");

              while (!scanner.hasNextInt())
              {
                  System.out.println("Enter a choice number (1-7)");
                  scanner.next();
              }

              choice = scanner.nextInt();
              scanner.nextLine();

              switch (choice)
              {
                  case 1:
                      libraryDao.displayBooks();
                      break;

                  case 2:
                      System.out.println("Enter Book Id : ");
                      String id = scanner.nextLine();
                      System.out.println("Enter Book Tittle : ");
                      String booktittle = scanner.nextLine();
                      System.out.println("Enter Book Author : ");
                      String bookauthor = scanner.nextLine();
                      System.out.println("Enter Total Stock Count : ");
                      int totalCopies = scanner.nextInt();
                      libraryDao.addBook(id, booktittle, bookauthor, totalCopies);
                      break;

                  case 3:
                      System.out.println("Enter the BookTittle to Search : ");
                      String bookTittle = scanner.nextLine();
                      libraryDao.searchByTittle(bookTittle);
                      break;

                  case 4:
                      System.out.println("Enter the Book Id to Issue : ");
                      String bookId = scanner.nextLine();
                      System.out.println("Enter the Borrowing Student's ID : ");
                      String studentId = scanner.nextLine();
                      System.out.println("Enter the Borrowing Student's Name : ");
                      String studentName = scanner.nextLine();
                      libraryDao.issueBook(bookId, studentId, studentName);
                      break;

                  case 5:
                      System.out.println("Enter the Book Id to Return : ");
                      String bookId1 = scanner.nextLine();
                      System.out.println("Enter the Student Id Returning It : ");
                      String studentId1 = scanner.nextLine();
                      libraryDao.returnBook(bookId1, studentId1);
                      break;

                  case 6:
                      libraryDao.displayTransaction();
                      break;

                  case 7:
                      System.out.println("Goodbye........");
                      break;

                  default:
                      System.out.println("Invalid Option Choice");
              }
          }
          while (choice != 7);

            scanner.close();

    }
}