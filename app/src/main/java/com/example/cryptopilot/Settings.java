package com.example.cryptopilot;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Settings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Settings() {
        // Required empty public constructor

    }
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        GridLayout gridLayout = view.findViewById(R.id.grid_layout);
        LinearLayout row1 = gridLayout.findViewById(R.id.row_1);
        LinearLayout row2 = gridLayout.findViewById(R.id.row_2);
        LinearLayout row3 = gridLayout.findViewById(R.id.row_3);
        LinearLayout row4 = gridLayout.findViewById(R.id.row_4);
        LinearLayout row5 = gridLayout.findViewById(R.id.row_5);
        LinearLayout row6 = gridLayout.findViewById(R.id.row_6);
        LinearLayout row7 = gridLayout.findViewById(R.id.row_7);
        LinearLayout row8 = gridLayout.findViewById(R.id.row_8);
        LinearLayout row9 = gridLayout.findViewById(R.id.row_9);
        LinearLayout row10 = gridLayout.findViewById(R.id.row_10);

        row1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Wallet_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Alerts_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Friends_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Pref_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Sec_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Notif_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Help_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Policy_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Agree_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        row10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),About_set.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.from_right,R.anim.out_left);
            }
        });

        return view;
    }
}