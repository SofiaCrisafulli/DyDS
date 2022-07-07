package main.java.presenter;

import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.model.repository.NonPersistentNotesRepository;
import main.java.utils.CurrentDateManager;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    NotesModel model = new NotesModelImpl();
    model.setDateManager(new CurrentDateManager());
    model.setNotesRepository(new NonPersistentNotesRepository());

    NotesListerPresenter notesListerPresenter = new NotesListerPresenterImpl(model);
    notesListerPresenter.start();
  }

}
