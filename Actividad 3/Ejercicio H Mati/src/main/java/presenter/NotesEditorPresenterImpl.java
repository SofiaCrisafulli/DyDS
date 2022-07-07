package main.java.presenter;

import main.java.model.Note;
import main.java.model.NotesModel;
import main.java.model.NotesModelEditorListener;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;

import java.text.DateFormat;

public class NotesEditorPresenterImpl implements NotesEditorPresenter {

    private NotesModel notesModel;
    public NoteEditorView noteEditorView;
    private NotesListerPresenter notesListerPresenter;
    private NotesModelEditorListener listener;

    private Thread taskThread;

    public NotesEditorPresenterImpl(NotesModel notesModel) {
        this.notesModel = notesModel;
    }

    @Override
    public void start() {
        noteEditorView = new NoteEditorViewImpl(this);
        noteEditorView.showView();
    }

    @Override
    public void editorClosed() {
        notesListerPresenter.removeEditorPresenter(this);
    }

    @Override
    public void setNoteEditorView(NoteEditorView noteEditorView) {
        this.noteEditorView = noteEditorView;
    }

    @Override
    public void setNotesListerPresenter(NotesListerPresenter notesListerPresenter) {
        this.notesListerPresenter = notesListerPresenter;
        this.notesListerPresenter.addNewNoteEditorPresenter(this);
        initListeners();
    }

    @Override
    public void onEventUpdate(String noteTitle, String noteContentText) {
        taskThread = new Thread(() -> {
            noteEditorView.startWaitingStatus();
            notesModel.updateNote(noteTitle, noteContentText, listener);
            noteEditorView.stopWaitingStatus();
        });
        taskThread.start();
    }

    @Override
    public void startWaitingStatus() {
        noteEditorView.startWaitingStatus();
    }

    @Override
    public void stopWaitingStatus() {
        noteEditorView.stopWaitingStatus();
    }

    @Override
    public void onEventCreateNewNote() {
        noteEditorView.cleanFields();
    }

    @Override
    public boolean isActivelyWorking() {
        return taskThread.isAlive();
    }

    @Override
    public String getMyNoteTitle() {
        return noteEditorView.getNoteTitle();
    }

    @Override
    public String getMyNoteContent() {
        return noteEditorView.getNoteContentText();
    }

    @Override
    public String getMyNoteTime() {
        return noteEditorView.getTime();
    }

    private void initListeners() {
        listener = () -> {
            updateFieldsOfStoredNote();
            notesListerPresenter.updateNoteList();
        };
    }

    private void updateFieldsOfStoredNote() {
        setFieldsOfNote(notesModel.getLastUpdatedNote());
    }

    @Override
    public void setFieldsOfNote(Note note) {
        if (note != null) {
            noteEditorView.setNoteTitle(note.getTitle());
            noteEditorView.setNoteContentText(note.getTextContent());
            noteEditorView.setTime(DateFormat.getTimeInstance().format(note.getLastUpdate()));
        }
    }

}
