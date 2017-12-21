package app.munc.munccoordinator.fragment.homepage.page1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.munc.munccoordinator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialColumnFragment extends Fragment {


    public SpecialColumnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_special_column, container, false);
    }

}
