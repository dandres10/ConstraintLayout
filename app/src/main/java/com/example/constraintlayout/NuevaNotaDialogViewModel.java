package com.example.constraintlayout;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.constraintlayout.db.entity.NotaEntity;

import java.util.List;

public class NuevaNotaDialogViewModel extends AndroidViewModel {
    private LiveData<List<NotaEntity>> allNotas;
    private NotaRepository notaRepository;

    public NuevaNotaDialogViewModel(Application application){
        super(application);
        notaRepository = new NotaRepository(application);
        allNotas = notaRepository.getAll();
    }

    //El fragmento necesita recibir la nueva lista de datos
    public LiveData<List<NotaEntity>> getAllNotas(){return allNotas;}
    //El fragmento que inserte una nueva nota, debera comunicarlo a este ViewModel
    public void insertNota(NotaEntity nuevaNotaEntity){ notaRepository.insert(nuevaNotaEntity); }
    public void updateNota(NotaEntity notaActualizarEntity){ notaRepository.update(notaActualizarEntity); }
}
