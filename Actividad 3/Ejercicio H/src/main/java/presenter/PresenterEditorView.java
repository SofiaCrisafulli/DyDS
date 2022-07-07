package main.java.presenter;

import main.java.model.Note;
import main.java.model.NotesModel;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;

import java.text.DateFormat;
import java.util.ArrayList;

public class PresenterEditorView {

    private NotesModel notesModel;
    private ArrayList<NoteEditorView> noteEditorViewArrayList;
    private Thread taskThread;
    private NoteEditorView lastViewUpdate;
    private NoteEditorView lastViewSelected;


    public PresenterEditorView(NotesModel notesModel) {
        this.notesModel = notesModel;
        noteEditorViewArrayList = new ArrayList<>();
        taskThread = new Thread();
    }

    public void addList(NoteEditorView noteEditorView) {
        noteEditorViewArrayList.add(noteEditorView);
    }

    public void onEventUpdate(String title, String contentText) {
        taskThread = new Thread(() -> {
            NoteEditorView noteEditorView = searchForTitleInTheList(title);
            if (noteEditorView == null) {
                noteEditorView = new NoteEditorViewImpl(this, notesModel);
                noteEditorView.setTitle(title);
                noteEditorView.setContent(contentText);
                lastViewUpdate.getLastUpdateLbl().setText(DateFormat.getTimeInstance().format(notesModel.getSelectedNote().getLastUpdate()));
            }
            noteEditorView.startWaitingStatus();
            notesModel.updateNote(title, contentText);
            noteEditorView.stopWaitingStatus();
            lastViewUpdate = noteEditorView;
        });
        taskThread.start();
    }

    public void createAndShowViewEditorViewWhenNecesary(String title) {
        NoteEditorView noteEditorView = searchForTitleInTheList(title);
        if (noteEditorView == null) {
            noteEditorView = new NoteEditorViewImpl(this, notesModel);
            addList(noteEditorView);
            noteEditorView.showView();
            lastViewUpdate = noteEditorView;
        }
        updateNote(noteEditorView);
        lastViewSelected = noteEditorView;
    }

    public void updateNote(NoteEditorView noteEditorView) {
        Note note = notesModel.getSelectedNote();
        noteEditorView.updateNoteFields(note);
    }

    private NoteEditorView searchForTitleInTheList(String title) {
        NoteEditorView view = null;
        for (NoteEditorView editorView : noteEditorViewArrayList) {
            if (editorView.getTitle().equals(title))
                view = editorView;
        }
        return view;
    }

    public void createNewNote() {
        NoteEditorView noteEditorView = new NoteEditorViewImpl(this, notesModel);
        noteEditorViewArrayList.add(noteEditorView);
        noteEditorView.showView();
    }

    public void updateFieldsOfSelectedNote() {
        NoteEditorView noteEditorView = searchForTitleInTheList(notesModel.getSelectedNote().getTitle());
        lastViewSelected = noteEditorView;
    }

    public void updateFieldsOfStoredNote() {
        NoteEditorView noteEditorView = searchForTitleInTheList(notesModel.getLastUpdatedNote().getTitle());
        lastViewUpdate = noteEditorView;
    }

    public ArrayList<NoteEditorView> getNoteEditorViewArrayList() {
        return noteEditorViewArrayList;
    }

    public NoteEditorView getLastViewUpdate() {
        return lastViewUpdate;
    }

    public void setLastViewUpdate(String title) {
        NoteEditorView noteEditorView = searchForTitleInTheList(notesModel.getLastUpdatedNote().getTitle());
        this.lastViewUpdate = searchForTitleInTheList(title);
    }

    public NoteEditorView getLastViewSelected() {
        return lastViewSelected;
    }

    public void setLastViewSelected(NoteEditorView lastViewSelected) {
        this.lastViewSelected = lastViewSelected;
    }

    public boolean isActivellyWorking() {
        return taskThread.isAlive();
    }
}