package app.mvvm.Database.Tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "table_dailytasks")
public class DailyTask implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle = null;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "is_completed")
    private boolean mIsCompleted = false;

    public DailyTask(){}

    @Ignore
    public DailyTask(int id, String title, boolean isCompleted) {
        this.mId = id;
        this.mTitle = title;
        this.mIsCompleted = isCompleted;
    }

    /*@Ignore
    public DailyTask(int id, boolean isCompleted) {
        this.mId = id;
        this.mIsCompleted = isCompleted;
    }*/

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mTask) {
        this.mDescription = mTask;
    }

    public boolean getIsCompleted() {
        return mIsCompleted;
    }

    public void setIsCompleted(boolean mIsCompleted) {
        this.mIsCompleted = mIsCompleted;
    }
}
