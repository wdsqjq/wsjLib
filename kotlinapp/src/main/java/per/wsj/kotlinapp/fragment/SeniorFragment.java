package per.wsj.kotlinapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import per.wsj.kotlinapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeniorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeniorFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    public SeniorFragment() {
        // Required empty public constructor
    }

    public static SeniorFragment newInstance(String param1) {
        SeniorFragment fragment = new SeniorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
