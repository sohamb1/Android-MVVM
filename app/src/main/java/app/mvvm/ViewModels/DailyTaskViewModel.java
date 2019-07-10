package app.mvvm.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import app.mvvm.Database.Tables.DailyTask;
import app.mvvm.Database.TodoRepository;

public class DailyTaskViewModel extends AndroidViewModel {

    private TodoRepository mRepository;
    private LiveData<List<DailyTask>> mAllTodo;

    public DailyTaskViewModel(Application application) {
        super(application);
        mRepository = TodoRepository.getInstance(application);
    }

    public LiveData<List<DailyTask>> getAllTodo() {
        mAllTodo = mRepository.getAllTodo();
        return mAllTodo;
    }

    public void insert(DailyTask dailyTask) {
        mRepository.insertDailyTask(dailyTask);
    }

    public void delete(DailyTask dailyTask) {
        mRepository.deleteDailyTask(dailyTask);
    }

    public void update(DailyTask dailyTask) {
        mRepository.updateDailyTask(dailyTask);
    }

}
