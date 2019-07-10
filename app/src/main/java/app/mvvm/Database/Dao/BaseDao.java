package app.mvvm.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract public void insert(T entity);

    @Update
    abstract public void update(T entity);

    @Delete
    abstract public void delete(T entity);
}