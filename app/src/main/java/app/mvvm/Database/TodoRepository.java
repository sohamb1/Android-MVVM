package app.mvvm.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.mvvm.Database.Dao.DailyTasksDao;
import app.mvvm.Database.Tables.DailyTask;

public class TodoRepository {

    private static TodoRepository instance;
    private DailyTasksDao mDailyTasksDao;
    private LiveData<List<DailyTask>> mAllDailyTasks;
    private static final String KEYWORD_ADD = "ADD";
    private static final String KEYWORD_UPDATE = "UPDATE";
    private static final String KEYWORD_DELETE = "DELETE";

    private static final String TAG = TodoRepository.class.toString();

    public static TodoRepository getInstance(Application application) {
        if(instance==null){
            instance = new TodoRepository(application);
        }
        return instance;
    }

    public TodoRepository(Application application) {
        TodoDatabase db = TodoDatabase.getDatabase(application);
        mDailyTasksDao = db.todoDao();
    }

    /*
     * Functions for adding, deleting and updating daily tasks
     */

    public LiveData<List<DailyTask>> getAllTodo() {
        mAllDailyTasks = mDailyTasksDao.getAllTodo();
        return mAllDailyTasks;
    }

    public void insertDailyTask(DailyTask dailyTask) {
        new AsyncDailyTaskQuery(mDailyTasksDao, KEYWORD_ADD).execute(dailyTask);
    }

    public void deleteDailyTask(DailyTask dailyTask)  {
        new AsyncDailyTaskQuery(mDailyTasksDao, KEYWORD_DELETE).execute(dailyTask);
    }

    public void updateDailyTask(DailyTask dailyTask)  {
        new AsyncDailyTaskQuery(mDailyTasksDao, KEYWORD_UPDATE).execute(dailyTask);
    }

    private static class AsyncDailyTaskQuery extends AsyncTask<DailyTask, String, Void> {
        private DailyTasksDao mAsyncTaskDao;
        private String task;

        AsyncDailyTaskQuery(DailyTasksDao dao, String func) {
            mAsyncTaskDao = dao;
            task = func;
        }

        @Override
        protected Void doInBackground(final DailyTask... params) {
            if(task.equals(KEYWORD_UPDATE)) {
                mAsyncTaskDao.update(params[0]);
                Log.d(TAG,"updated");
            } else if(task.equals(KEYWORD_DELETE)) {
                mAsyncTaskDao.delete(params[0]);
                Log.d(TAG,"deleted");
            } else if(task.equals(KEYWORD_ADD)) {
                mAsyncTaskDao.insert(params[0]);
                Log.d(TAG,"added");
            }
            return null;
        }
    }
}
