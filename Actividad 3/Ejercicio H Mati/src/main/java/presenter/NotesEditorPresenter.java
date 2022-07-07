package main.java.presenter;

import main.java.model.Note;
import main.java.views.NoteEditorView;

public interface NotesEditorPresenter {

  void start();

  void editorClosed();

  void setNoteEditorView(NoteEditorView noteEditorView);

  void setNotesListerPresenter(NotesListerPresenter notesListerPresenter);

  boolean isActivelyWorking();

  void startWaitingStatus();

  void stopWaitingStatus();

  void onEventUpdate(String title, String contentText);

  void onEventCreateNewNote();

  void setFieldsOfNote(Note note);

  String getMyNoteTitle();

  String getMyNoteContent();

  String getMyNoteTime();

}
