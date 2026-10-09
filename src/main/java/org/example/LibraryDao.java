package org.example;

import java.sql.*;

public class LibraryDao
{

    //1. Add a new book
    public void addBook(String  id , String booktittle , String bookauthor , int totalCopies)
    {
        String sql = "insert into books (id , booktittle , bookauthor ,  totalCopies, availableCopies) values  (?,?,?,?,?)";
        try
        {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps  = connection.prepareStatement(sql);
            ps.setString(1 , id);
            ps.setString(2 , booktittle);
             ps.setString(3 , bookauthor);
            ps.setInt(4 , totalCopies);
            ps.setInt(5 , totalCopies);
            ps.executeUpdate();

            System.out.println("New Book added Successfully");
        }

        catch (Exception se)
        {
            System.out.println("Error Saving book" + se.getMessage());
        }
    }


    //2. Display all books
    public void displayBooks()
    {
        String sql = "select * from books";
        try
        {
          Connection connection = DBConnection.getConnection();
          Statement statement =  connection.createStatement();
          ResultSet resultSet = statement.executeQuery(sql);
          System.out.println("================LIBRARY INVENTORY=======================");
          System.out.println("================MY TABLE=======================");

            // a. Print the table header with column widths
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s | %-30s | %-25s | %-12s | %-16s%n", "ID", "Book Title", "Book Author", "Total Copies", "Available Copies");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");

           // b. Loop through the result set and print rows
            while (resultSet.next())
            {
                String id = resultSet.getString("id");
                String booktittle = resultSet.getString("booktittle");
                String bookauthor = resultSet.getString("bookauthor");
                int totalCopies = resultSet.getInt("totalCopies");
                int availableCopies = resultSet.getInt("availableCopies");

                // Print the formatted row data
                System.out.printf("%-10s | %-30s | %-25s | %-12d | %-16d%n", id, booktittle, bookauthor, totalCopies, availableCopies);
            }

            // c. Print the table bottom border
             System.out.println("----------------------------------------------------------------------------------------------------------------------");

        }
        catch (SQLException e)
        {
         System.out.print("Error fetching Books = " + e.getMessage());
        }
    }


              //3. Search book by tittle

                    public void searchByTittle(String bookTittle)
                    {
                        String sql ="select * from books where booktittle LIKE ?";

                        try
                        {
                            Connection con = DBConnection.getConnection();
                            PreparedStatement pst = con.prepareStatement(sql);
                            pst.setString(1 , bookTittle);
                            ResultSet rs =pst.executeQuery();

                                boolean found = false;
                                while (rs.next())
                                {
                                    found=true;
                                    System.out.println("Found :" + new Book(rs.getString("id"), rs.getString("booktittle"), rs.getString("bookauthor"), rs.getInt("totalCopies"), rs.getInt("availableCopies")));
                                    System.out.println("Matching book found with the tittle = " + bookTittle);
                                    System.out.println("====================================================");
                                }
                                if(!found)
                                    {
                                        System.out.println("going in not found loop");

                                        System.out.println("No Book found with Matching Tittle =" + bookTittle);
                                        System.out.println("====================================================");

                                    }

                        }

                        catch (SQLException sqlException)
                        {
                            System.out.print("Error Searching book = " + sqlException.getMessage());
                        }
                    }

                    //4. Issue a Book and entering student details

                  public void issueBook (String bookId , String studentId , String studentName)
                  {
                      String checkSql = "select availableCopies from books where id =?";
                      String update = "update books set availableCopies = availableCopies - 1 where availableCopies !=0 and id = ?";
                      String insert = "INSERT INTO transactions (book_id, student_id, student_name, status)  SELECT ?, ?, ?, 'ISSUED' FROM books WHERE id = ? AND availableCopies > 0";



                      //a. Checking availability of book
                      try
                      {
                          System.out.println("====================================================================");
                          System.out.println("Checking whether book is available or not with bookId = "+ bookId);
                          Connection c = DBConnection.getConnection();
                          PreparedStatement pstmt = c.prepareStatement(checkSql);
                          pstmt.setString(1 , bookId);
                          ResultSet set = pstmt.executeQuery();
                          if(set.next())
                           {
                           int availableCopies  = set.getInt("availableCopies");
                           if (availableCopies >0)
                           {
                               System.out.println("Book is available with id = " + bookId);
                           }
                           else
                           {
                               System.out.println("Out of stock! No available Copies left");

                           }
                          }
                        else
                        {
                            System.out.println("Error : Book with ID "+ bookId + "does not exists.");
                        }
                      }
                      catch (Exception e)
                      {
                        System.out.println("error executing = " + e.getMessage());
                      }

                      //b. Updating available books record in table

                      try
                      {
                        Connection conn = DBConnection.getConnection();
                        PreparedStatement ps = conn.prepareStatement(update);
                        ps.setString(1 , bookId);
                        int rs = ps.executeUpdate();
                        if (rs > 0)
                        {
                            System.out.println("Available Copies record in table updated");
                        }
                        else
                        {
                            System.out.println("Out of stock ! Cant be updated...");
                        }
                      }
                      catch (Exception e)
                      {
                          e.getMessage();
                      }

                      //c. Inserting issued book record in transaction table

                      try
                      {
                          Connection c1 = DBConnection.getConnection();
                          PreparedStatement p1 = c1.prepareStatement(insert);
                          p1.setString(1, bookId);
                          p1.setString(2, studentId);
                          p1.setString(3, studentName);
                          p1.setString(4, bookId);
                          int result = p1.executeUpdate();
                          //c1.commit();
                          if (result > 0)
                          {
                              System.out.println("Success ! Book issued to = " + studentName + " Id = " + studentId);
                          }
                          else
                          {
                              System.out.println("Failed ! Cannot be issued , Book is out of stock.");
                          }
                      }

                      catch (Exception e)
                      {
                          System.out.println("Error during issuing workflow = " + e.getMessage());
                      }
                  }

                  //5. Return a book & update ledger status

                       public void returnBook(String bookId , String studentId)
                       {
                           int txId = -1;
                           String checkSql = "select trans_id from transactions where book_id = ? and student_id = ? and status = 'ISSUED'";
                           String updateTrans = "update transactions set status = 'RETURNED' where trans_id = ?";
                           String updateBook = "update books set availableCopies = availableCopies + 1 where availableCopies < totalCopies and id = ?";
                           Connection con = DBConnection.getConnection();

                           //a. Retreiving trans id for that book id
                           try
                           {
                               System.out.println("====================================================================");

                               PreparedStatement ps=con.prepareStatement(checkSql);
                               ps.setString(1, bookId);
                               ps.setString(2, studentId);
                               ResultSet rs = ps.executeQuery();
                               if(rs.next())
                               {
                                  txId = rs.getInt("trans_id");
                                  System.out.println("The transaction Id is = " +txId);
                               }
                               else
                               {
                                   System.out.println("No matching active borrow record found for this student ID and book ID");
                               }
                           }
                           catch (Exception e)
                           {
                               e.getMessage();
                           }

                           //b, updating transaction table with status as returned.

                           try
                           {
                               PreparedStatement ps = con.prepareStatement(updateTrans);
                               ps.setInt(1, txId);
                               int result = ps.executeUpdate();
                               if (result > 0)
                               {
                                   System.out.println("Status updated");
                               }
                               else
                               {
                                   System.out.println("not updated");
                               }
                           }
                           catch (Exception e)
                           {
                               System.out.println(e.getMessage());
                           }

                           //c. updating Book table for available copies

                           try
                           {
                             PreparedStatement pt =con.prepareStatement(updateBook);
                             pt.setString(1 , bookId);
                             int result =pt.executeUpdate();
                             if (result > 0)
                             {
                                 System.out.println("Availability of copies updated");
                             }
                             else
                             {
                                 System.out.println("Not updated");
                             }
                           }
                           catch (Exception e)
                           {
                               e.getMessage();
                           }
                       }

                       //6.View all Issued Borrow records

                        public void displayTransaction()
                        {
                            String sql = "select t.trans_id, t.student_id, t.student_name, t.book_id, b.booktittle, t.issue_date, t.status " +  "from transactions t left join books b on t.book_id = b.id order by t.issue_date DESC";
                            //System.out.println(sql);
                            try
                            {
                             Connection con =DBConnection.getConnection();
                             Statement stmt = con.createStatement();
                             ResultSet resultSet = stmt.executeQuery(sql);

                             System.out.println("===================================Borrow Record History===================================");
                             boolean recordFound = false;

                                // 1. Print the table header (outside the loop)
                                System.out.printf("%-10s | %-20s | %-12s | %-30s | %-10s | %-20s | %-10s%n",
                                        "Trans ID", "Student Name", "Student ID", "Book Title", "Book ID", "Issue Date", "Status");
                                System.out.println("-".repeat(125)); // Prints a dividing line

                                while (resultSet.next())
                                {
                                    recordFound = true;
                                    String trans_id = resultSet.getString("trans_id");
                                    String student_name = resultSet.getString("student_name");
                                    String student_id = resultSet.getString("student_id");
                                    String booktittle = resultSet.getString("booktittle");
                                    String book_id = resultSet.getString("book_id");

                                    // Handling potential null for the date safely
                                    java.sql.Timestamp timestamp = resultSet.getTimestamp("issue_date");
                                    String issue_date = (timestamp != null) ? timestamp.toString() : "null";

                                    String status = resultSet.getString("status");

                                    // 3. Print the formatted data row
                                    System.out.printf("%-10s | %-20s | %-12s | %-30s | %-10s | %-20s | %-10s%n",
                                            trans_id, student_name, student_id, booktittle, book_id, issue_date, status);
                                }

                                if (!recordFound)
                             {
                                 System.out.println("No active borrow record found");
                             }
                            }
                            catch (Exception e)
                            {
                               System.out.println("Error Displaying = " + e.getMessage());
                            }

                        }
}

