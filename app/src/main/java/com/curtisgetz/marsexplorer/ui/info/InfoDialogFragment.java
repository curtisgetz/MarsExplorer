


package com.curtisgetz.marsexplorer.ui.info;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.utils.InformationUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dialog Fragment for showing information popups when clicked on by user
 */
public class InfoDialogFragment extends DialogFragment {

    @BindView(R.id.information_text)
    TextView mInfoText;


    /**
     * Get new instance of this fragment
     *
     * @param context   needed to get access to resources
     * @param infoIndex index of the info requested
     * @return the new instance of the fragment
     */
    public static InfoDialogFragment newInstance(Context context, int infoIndex) {
        InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(context.getString(R.string.info_index_key), infoIndex);
        infoDialogFragment.setArguments(bundle);
        return infoDialogFragment;
    }

    public InfoDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //rounded corner code from
        // https://stackoverflow.com/questions/15421271/custom-fragmentdialog-with-round-corners-and-not-100-screen-width
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View view = inflater.inflate(R.layout.fragment_info_dialog, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        int infoIndex = bundle.getInt(getString(R.string.info_index_key), -1);
        String text = InformationUtils.getInformationText(getActivity(), infoIndex);
        mInfoText.setText(text);

        return view;
    }


    /**
     * Dismiss info fragment when clicked.
     */
    @OnClick({R.id.information_text, R.id.dialog_info_imageview})
    public void closeInfo() {
        getDialog().dismiss();
    }


}
