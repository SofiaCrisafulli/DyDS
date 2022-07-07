package main.java.model;

import main.java.model.repository.NotesRepository;
import main.java.utils.DateManager;

public interface NotesModel {

  void updateNote(String title, String content, NotesModelEditorListener listener);

  Note getLastUpdatedNote();

  void selectNote(String noteTitle);

  Note getSelectedNote();

  void setDateManager(DateManager dateManager);

  void setNotesRepository(NotesRepository notesRepository);

  void setNotesModelListerListener(NotesModelListerListener listener);

}
