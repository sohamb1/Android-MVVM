package app.mvvm.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import app.mvvm.Database.Tables.DailyTask;
import app.mvvm.ViewModels.DailyTaskViewModel;
import app.mvvm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoDialog extends DialogFragment {

    private View mView;
    private EditText mTitle;
    private EditText mDescrp;
    private AppCompatCheckBox mIsCompleted;
    private DailyTask dailyTask = null;
    private boolean isUpdating = false;
    private DailyTaskViewModel mDailyTaskViewModel;
    private FloatingActionButton mSave;
    private ImageButton mDelete;

    public ToDoDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_task_dialog, container, false);
        mTitle = mView.findViewById(R.id.title_enter);
        mIsCompleted = mView.findViewById(R.id.check_item);
        mSave = mView.findViewById(R.id.button_save);
        mDelete = mView.findViewById(R.id.delete);
        mDescrp = mView.findViewById(R.id.descrp_enter);

        Bundle extras = getArguments();
        if (extras != null) {
            dailyTask = (DailyTask) extras.getSerializable("dailyTask");
            mTitle.setText(dailyTask.getTitle());
            mIsCompleted.setChecked(dailyTask.getIsCompleted());
            mDescrp.setText(dailyTask.getDescription());
            isUpdating = true;
        }

        mDailyTaskViewModel = ViewModelProviders.of(this).get(DailyTaskViewModel.class);
        mSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String task = mTitle.getText().toString();
                if(!(task.isEmpty() || task.equals("") || task.equals(null))) {
                    if (isUpdating) {
                        DailyTask updateDailyTask = new DailyTask();
                        updateDailyTask.setId(dailyTask.getId());
                        updateDailyTask.setTitle(task);
                        updateDailyTask.setIsCompleted(mIsCompleted.isChecked());
                        updateDailyTask.setDescription(mDescrp.getText().toString());
                        mDailyTaskViewModel.update(updateDailyTask);
                        dismiss();
                    } else {
                        if (TextUtils.isEmpty(mTitle.getText())) {
                            dismiss();
                        } else {
                            DailyTask dailyTask = new DailyTask();
                            dailyTask.setTitle(task);
                            dailyTask.setIsCompleted(mIsCompleted.isChecked());
                            dailyTask.setDescription(mDescrp.getText().toString());
                            mDailyTaskViewModel.insert(dailyTask);
                            dismiss();
                        }
                    }
                } else {
                    Snackbar mRestoreBar = Snackbar.make(mView.findViewById(R.id.task_dialog), R.string.task_cannot_save,
                            Snackbar.LENGTH_LONG);
                    mRestoreBar.show();
                }

            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog mDialog = new AlertDialog.Builder(getActivity())
                        .setMessage(getActivity().getString(R.string.sure_delete_task_txt))
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dailyTask != null)
                                    mDailyTaskViewModel.delete(dailyTask);
                                dialogInterface.dismiss();
                                dismiss();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();

                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
            }
        });

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
