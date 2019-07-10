package app.mvvm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.mvvm.Database.Tables.DailyTask;
import app.mvvm.ViewModels.DailyTaskViewModel;

public class DailyTaskAdapter extends RecyclerView.Adapter<DailyTaskAdapter.TodoViewHolder> {

    private Activity mActivity;
    private final LayoutInflater mInflater;
    private List<DailyTask> mDailyTask;
    private View mScreenView;
    private DailyTaskViewModel mDailyTaskViewModel;

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemView;
        private final CheckBox mCheckBox;

        private TodoViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView.findViewById(R.id.textView);
            mCheckBox = itemView.findViewById(R.id.check_item);
        }
    }

    public DailyTaskAdapter(Activity activity, View view, DailyTaskViewModel viewModel) {
        mActivity = activity;
        mDailyTaskViewModel = viewModel;
        mScreenView = view;
        mInflater = LayoutInflater.from(mActivity);
    }

    public Context getContext() {
        return mActivity;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, int position) {
        final DailyTask currentItem = mDailyTask.get(position);
        holder.mItemView.setText(currentItem.getTitle());

        holder.mCheckBox.setOnCheckedChangeListener(null);

        holder.mCheckBox.setChecked(currentItem.getIsCompleted());

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendTask = new Bundle();
                sendTask.putSerializable("dailyTask", currentItem);
                Navigation.findNavController(mScreenView).navigate(R.id.openToDoDialog, sendTask);
            }
        });

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean completedValue) {
                mDailyTaskViewModel.update(new DailyTask(currentItem.getId(),
                        currentItem.getTitle(), completedValue));
            }
        });
    }

    public void setArchive(List<DailyTask> archive){
        mDailyTask = archive;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDailyTask != null)
            return mDailyTask.size();
        else return 0;
    }

    public DailyTask getItemAtPosition (int position) {
        return mDailyTask.get(position);
    }

}
