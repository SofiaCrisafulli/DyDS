package main.java.presenter;

import main.java.views.NoteListerView;

public interface NotesListerPresenter {

    void start();

    void setNoteListerView(NoteListerView noteListerView);

    void listerClosed();

    boolean isActivelyWorking();

    void removeEditorPresenter(NotesEditorPresenter notesEditorPresenter);

    void addNewNoteEditorPresenter(NotesEditorPresenter newPresenter);

    void onEventCreateNewNote();

    void onEventSelectedNoteTitle(String title);

    void updateFieldsOfSelectedNote();

    void updateNoteList();
}
