package com.curtisgetz.marsexplorer.ui.explore;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.rover_manifest.RoverManifest;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SolSearchDialogFragment extends DialogFragment {

    @BindView(R.id.enter_sol_dialog_text)
    TextView mTitleText;
    @BindView(R.id.enter_sol_dialog_edit)
    EditText mEditText;


    private Unbinder mUnBinder;
    private SearchDialogInteraction mListener;
    private String mSolRange = "";

    public interface SearchDialogInteraction{
        void onDialogSearchClick(String solInput);
    }

    public static SolSearchDialogFragment newInstance(Context context, String solRange){
        SolSearchDialogFragment fragment = new SolSearchDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.sol_range_bundle_key), solRange);
        fragment.setArguments(bundle);
        return fragment;
    }

    public SolSearchDialogFragment(){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SearchDialogInteraction){
            mListener = (SearchDialogInteraction) context;
        }else {
            throw new RuntimeException(context.toString() + " must implement SearchDialogInteraction");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //rounded corner code from
        // https://stackoverflow.com/questions/15421271/custom-fragmentdialog-with-round-corners-and-not-100-screen-width
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View view = inflater.inflate(R.layout.enter_sol_dialog, container, false);

        mUnBinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();

        if(savedInstanceState == null){
            if(bundle != null){
                mSolRange = bundle.getString(getString(R.string.sol_range_bundle_key));
            }
        }else {
            mSolRange = savedInstanceState.getString(getString(R.string.sol_range_bundle_key));
        }


        String title = getString(R.string.sol_search_dialog_message, mSolRange);
        mTitleText.setText(title);

       return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.sol_range_bundle_key), mSolRange);
    }

    @OnClick(R.id.sol_search_dialog_cancel_btn)
    public void onCancelClick(){
        getDialog().dismiss();
    }

    @OnClick(R.id.sol_search_dialog_search_btn)
    public void onSearchClick(){
        mListener.onDialogSearchClick(mEditText.getText().toString().trim());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
