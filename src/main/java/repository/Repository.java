package repository;

import javax.sql.DataSource;
import java.util.List;

public interface Repository<T> {
    void insert(DataSource dataSource, T t);

    void delete(DataSource dataSource, T t);

    void printAll(DataSource dataSource);

    void sort(DataSource dataSource);

    List<T> getAll(DataSource dataSource);
}
