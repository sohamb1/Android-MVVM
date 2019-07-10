package app.mvvm.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import app.mvvm.Database.Tables.DailyTask;

@Dao
public abstract class DailyTasksDao extends BaseDao<DailyTask> {

    @Query("DELETE FROM table_dailytasks")
    abstract public void deleteAll();

    @Query("SELECT * FROM table_dailytasks")
    abstract public LiveData<List<DailyTask>> getAllTodo();

}
