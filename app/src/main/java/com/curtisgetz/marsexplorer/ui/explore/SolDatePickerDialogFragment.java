package com.curtisgetz.marsexplorer.ui.explore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import java.util.Calendar;

public class SolDatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DateDialogInteraction mListener;
    private RoverManifestViewModel mViewModel;




    public interface DateDialogInteraction{
        void onDialogDateSelect(String date);
    }

    public static SolDatePickerDialogFragment newInstance(Context context, int roverIndex){
        Bundle bundle =new Bundle();
        SolDatePickerDialogFragment fragment = new SolDatePickerDialogFragment();
        bundle.putInt(context.getString(R.string.rover_index_extra), roverIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    public SolDatePickerDialogFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = ViewModelProviders.of(getActivity()).get(RoverManifestViewModel.class);
        if(context instanceof DateDialogInteraction){
            mListener = (DateDialogInteraction) context;
        }else {
            throw new RuntimeException(context.toString() + " must implement DateDialogInteraction");
        }

    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //create new DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        //Set min and max dates
        dialog.getDatePicker().setMinDate(mViewModel.getMinDateMilliseconds());
        dialog.getDatePicker().setMaxDate(mViewModel.getMaxDateMilliseconds());
        return  dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String monthString = String.valueOf(month+1);
        String yearString = String.valueOf(year);
        String dayString = String.valueOf(dayOfMonth);
        //StringBuilder builder = new StringBuilder();
        char dash = '-';
        String dateString = yearString + dash + monthString + dash + dayString;
       // String date = builder.append(yearString).append(dash).append(monthString).append(dash).append(dayString).toString();
        mListener.onDialogDateSelect(dateString);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
