package main.java.presenter;

import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;

import javax.swing.*;

public class PresenterListerView implements NotesPresenter {
    private NoteListerView noteListerView;
    private NotesModel notesModel;
    private DefaultListModel<String> notesListInternalModel = new DefaultListModel<>();
    private PresenterEditorView presenterEditorView;
    private Thread taskThread;
    private NoteEditorView noteEditorView;


    public PresenterListerView(NotesModel notesModel) {
        this.notesModel = notesModel;
        presenterEditorView = new PresenterEditorView(notesModel);
        taskThread = new Thread();
        noteEditorView = new NoteEditorViewImpl(presenterEditorView, notesModel);
        noteListerView = new NoteListerViewImpl(this, notesModel);
    }

    public void updateNoteList() {
        String noteTitleToAddOrUpdate = notesModel.getLastUpdatedNote().getTitle();
        notesListInternalModel.removeElement(noteTitleToAddOrUpdate);
        notesListInternalModel.insertElementAt(noteTitleToAddOrUpdate, 0);
    }


    public DefaultListModel<String> getNotesListInternalModel() {
        return notesListInternalModel;
    }

    public NotesModel getNotesModel() {
        return notesModel;
    }

    public void start() {
        noteListerView = new NoteListerViewImpl(this, notesModel);
        noteListerView.showView();
    }

    @Override
    public void setNoteEditorView(NoteEditorView noteEditorView) {
        this.noteEditorView = noteEditorView;
    }

    @Override
    public void setNoteListerView(NoteListerView noteListerView) {
        this.noteListerView = noteListerView;
    }

    @Override
    public void onEventUpdate(String title, String contentText) {
        taskThread = new Thread(() -> {
            noteEditorView.startWaitingStatus();
            notesModel.updateNote(title, contentText);
            presenterEditorView.setLastViewUpdate(title);
            noteEditorView.stopWaitingStatus();
        });
        taskThread.start();
    }

    @Override
    public void onEventSelectedNoteTitle(String title) {
        taskThread = new Thread(() -> {
            notesModel.selectNote(title);
            noteListerView.selectNone();
            noteEditorView.startWaitingStatus();
            presenterEditorView.createAndShowViewEditorViewWhenNecesary(title);
            noteEditorView.stopWaitingStatus();
        });
        taskThread.start();
    }

    @Override
    public void onEventCreateNewNote() {
        noteEditorView.cleanFields();
        presenterEditorView.createNewNote();
    }

    @Override
    public boolean isActivellyWorking() {
        return taskThread.isAlive();
    }

    @Override
    public void editorClosed() {
        noteEditorView = null;
    }

    public PresenterEditorView getPresenterEditorView() {
        return presenterEditorView;
    }
}
