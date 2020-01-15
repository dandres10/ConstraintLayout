package com.example.constraintlayout.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.constraintlayout.NuevaNotaDialogFragment;
import com.example.constraintlayout.NuevaNotaDialogViewModel;
import com.example.constraintlayout.R;
import com.example.constraintlayout.db.entity.NotaEntity;

import java.util.ArrayList;
import java.util.List;


public class NotaFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;

    private List<NotaEntity> notaEntityList;
    private MyNotaRecyclerViewAdapter adapterNotas;
    private NuevaNotaDialogViewModel notaDialogViewModel;


    public NotaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NotaFragment newInstance(int columnCount) {
        NotaFragment fragment = new NotaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //se indica que este fragment tiene un menu propio
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (DefinirOrientacion(view)) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(GenerarColumnas(context), StaggeredGridLayoutManager.VERTICAL));
            }

            notaEntityList = new ArrayList<>();

            adapterNotas = new MyNotaRecyclerViewAdapter(notaEntityList, getActivity());
            recyclerView.setAdapter(adapterNotas);

            lanzarViewModel();
        }
        return view;
    }

    private void lanzarViewModel() {
        notaDialogViewModel = ViewModelProviders.of(getActivity())
                .get(NuevaNotaDialogViewModel.class);
        notaDialogViewModel.getAllNotas().observe(getActivity(), new Observer<List<NotaEntity>>() {
            @Override
            public void onChanged(List<NotaEntity> notaEntities) {
                    adapterNotas.setNuevasNotas(notaEntities);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_nota_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_nota:
                mostrarDialogoNuevoNota();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoNuevoNota() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NuevaNotaDialogFragment dialogNuevaNota = new NuevaNotaDialogFragment();
        dialogNuevaNota.show(fm,"NuevaNotaDialogFragment");
    }

    private int GenerarColumnas(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((displayMetrics.widthPixels / displayMetrics.density) / 180);
    }


    private boolean DefinirOrientacion(View view) {
        return view.getId() == R.id.listPortrait ? true : false;
    }




}
