package per.wsj.kotlinapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private TextView tvTest;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_senior, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTest = (TextView) view.findViewById(R.id.tvTest);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
