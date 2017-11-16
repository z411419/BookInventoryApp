package be.tcla.bookinventory.repository;

import be.tcla.bookinventory.mapper.EnumMapper;
import be.tcla.bookinventory.model.Book;
import be.tcla.bookinventory.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryJDBCImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addBook(Book book) {
        String sql ="INSERT INTO spring.book VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1,book.getISBN());
            ps.setString(2,book.getAuthor());
            ps.setBoolean(3,book.isEbook());
            ps.setInt(4,book.getGenre().ordinal());
            ps.setInt(5,book.getLanguage().ordinal());
            ps.setInt(6,book.getPages());
            ps.setString(7,book.getPublisher());
            ps.setString(8,book.getSubject());
            ps.setString(9,book.getTitle());

        });
        return 1;}
        catch (Exception ex){
            return 0;
        }
    }

    @Override
    public int updateBook(Book book) {
        String sql = "UPDATE book SET author=?, ebook=?, genre= ?, isbn= ?, language= ?, pages= ?,publisher=?, subject= ?, title= ?" +
                "  WHERE id = ? ";
        return 0;
    }

    @Override
    public int deleteBook(Book book) {
        String sql = "DELETE FROM book WHERE id=?";

        try{
            jdbcTemplate.update(sql,ps->{
                ps.setInt(1,book.getId());
            });
            return 1;
        }
        catch(Exception ex) {
            return 0;
        }


    }

    @Override

            public List<Book> findAll() {
                return jdbcTemplate.query("SELECT * FROM book", (resultSet, i) -> {
                    Book book = new Book();
                    book.setAuthor(resultSet.getString("author"));
                    book.setTitle(resultSet.getString("title"));
                    book.setEbook(resultSet.getBoolean("ebook"));
                    book.setISBN(resultSet.getString("isbn"));
                    book.setPages(resultSet.getInt("pages"));
                    book.setPublisher(resultSet.getString("publisher"));
                    book.setSubject(resultSet.getString("subject"));
                    book.setId(resultSet.getInt("id"));

                    //TODO: ENUMS MAPPEN
                    //System.out.println(resultSet.getInt("genre"));
                    if(resultSet.getString("genre")!= null) {
                        book.setGenre(EnumMapper.mapToGenre(Integer.parseInt(resultSet.getString("genre"))));
                    }
                    if(resultSet.getString("language")!=null){
                        book.setLanguage(EnumMapper.mapToLanguage(Integer.parseInt(resultSet.getString("language"))));
                    }

                    return book;
                });

            }


    @Override
    public List<be.tcla.bookinventory.model.Book> findByAuthor(String author) {
        return null;
    }

    @Override
    public List<be.tcla.bookinventory.model.Book> findByGenre(Genre genre) {
        return null;
    }

    @Override
    public List<be.tcla.bookinventory.model.Book> findByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<be.tcla.bookinventory.model.Book> findByEbooks(boolean ebook) {
        return null;
    }

    @Override
    public be.tcla.bookinventory.model.Book findByISBN(String isbn) {
        return null;
    }

    @Override
    public be.tcla.bookinventory.model.Book findByTitleAndAuthor(String title, String author) {
        return null;
    }
}