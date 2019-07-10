package app.mvvm.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import app.mvvm.Database.Dao.DailyTasksDao;
import app.mvvm.Database.Tables.DailyTask;

@Database(entities = {DailyTask.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    public abstract DailyTasksDao todoDao();
    private static volatile TodoDatabase INSTANCE;

    static TodoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoDatabase.class, "dailytask_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


