import java.sql.*;
import java.util.ArrayList;

public class DB {

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public void connect() throws SQLException {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn =
                    DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/mobaran?serverTimezone=Europe/Warsaw",
                            "mobaran", "9Zef6mNt3BETGyUV");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void listNames() {
        try {
            connect();
            stmt = conn.createStatement();

            // Wyciagamy wszystkie pola z kolumny name
            // znajdujące się w tabeli users
            rs = stmt.executeQuery("SELECT title FROM books");

            while (rs.next()) {
                String name = rs.getString(1);
                System.out.println("Uzytkownik: " + name);
            }
        } catch (SQLException ex) {
            // handle any errors

        } finally {
            // zwalniamy zasoby, które nie będą potrzebne
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
    }*/

    public Book getBookByIsbn(Long isbn) {

        Book book = new Book();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books WHERE isbn ='" + isbn + "'");

            while (rs.next()) {
                book.setTitle(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setYear(rs.getInt(4));
                book.setIsbn(isbn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public ArrayList<Book> getBookByAuthor(String author) {

        ArrayList<Book> bookList = new ArrayList<>();
        Book book = new Book();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books WHERE author ='" + author + "'");

            while (rs.next()) {
                book.setTitle(rs.getString(2));
                book.setAuthor(author);
                book.setYear(rs.getInt(4));
                book.setIsbn(rs.getLong(1));

                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public void deleteBookByIsbn(Long isbn) {

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM books WHERE isbn ='" + isbn + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}