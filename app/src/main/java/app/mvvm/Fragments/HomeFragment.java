package app.mvvm.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import app.mvvm.DailyTaskAdapter;
import app.mvvm.DailyTaskSwipeToDelete;
import app.mvvm.Database.Tables.DailyTask;
import app.mvvm.R;
import app.mvvm.ViewModels.DailyTaskViewModel;


public class HomeFragment extends Fragment {

    private View mView;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private DailyTaskViewModel mDailyTaskViewModel;
    private DailyTask mDeletedItem;
    private FloatingActionButton mAdd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = mView.findViewById(R.id.recyclerview);
        mAdd = mView.findViewById(R.id.fab_add);
        mDailyTaskViewModel = ViewModelProviders.of(this).get(DailyTaskViewModel.class);

        final DailyTaskAdapter mArchiveAdapter = new DailyTaskAdapter(mActivity, mView, mDailyTaskViewModel);
        mRecyclerView.setAdapter(mArchiveAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setNestedScrollingEnabled(false);

        ItemTouchHelper helper = new ItemTouchHelper(
                new DailyTaskSwipeToDelete(mArchiveAdapter) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        mDeletedItem = mArchiveAdapter.getItemAtPosition(position);
                        mDailyTaskViewModel.delete(mDeletedItem);
                        Snackbar mRestoreBar = Snackbar.make(mView.findViewById(R.id.fragment_daily_tasks), R.string.item_deleted,
                                Snackbar.LENGTH_LONG);
                        mRestoreBar.setAction(mActivity.getString(R.string.undo_txt), new RestoreDeletedTask());
                        mRestoreBar.show();
                    }
                });

        helper.attachToRecyclerView(mRecyclerView);

        mDailyTaskViewModel.getAllTodo().observe(this, new Observer<List<DailyTask>>() {
            @Override
            public void onChanged(List<DailyTask> dailyTasks) {
                mArchiveAdapter.setArchive(dailyTasks);
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.openToDoDialog);
            }
        });


        return mView;
    }

    public class RestoreDeletedTask implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mDailyTaskViewModel.insert(mDeletedItem);
        }
    }

}
