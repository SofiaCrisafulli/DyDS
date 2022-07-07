package main.java.model;

import main.java.model.repository.NonPersistentNotesRepository;
import main.java.model.repository.NotesRepository;
import main.java.presenter.NotesPresenter;
import main.java.presenter.PresenterEditorView;
import main.java.presenter.PresenterListerView;
import main.java.utils.CurrentDateManager;
import main.java.utils.DateManager;
import main.java.utils.WaitSimulator;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;

import java.util.ArrayList;

public class NotesModelImpl implements NotesModel {

    private DateManager dateManager;
    private NotesRepository notesRepository;
    private Note selectedNote;
    private Note lastStoredNote;
    private ArrayList<NotesModelListener> listeners = new ArrayList<>();
    private NoteEditorViewImpl noteEditorView;


    public NotesModelImpl(){
        PresenterEditorView presenterEditorView = new PresenterEditorView(this);
        noteEditorView = new NoteEditorViewImpl(presenterEditorView, this);
        dateManager = new CurrentDateManager();
        notesRepository = new NonPersistentNotesRepository();
    }

    @Override
    public void addListener(NotesModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void setNotesRepository(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void setDateManager(DateManager dateManager) {
        this.dateManager = dateManager;
    }

    @Override
    public Note getLastUpdatedNote() {
        return lastStoredNote;
    }

    @Override
    public Note getSelectedNote() {
        return selectedNote;
    }

    @Override
    public void updateNote(String title, String content) {
        if (Note.isValidTitleForNote(title)) {
            WaitSimulator.simulateLongWait();
            updateNoteNow(title, content);
            notifyUpdateListener();
        }
    }

    private void updateNoteNow(String title, String content) {
        Note noteToUpdate = new Note();
        noteToUpdate.setName(title);
        noteToUpdate.setTextContent(content);
        noteToUpdate.setLastUpdate(dateManager.getDate());
        if (notesRepository.storeNote(noteToUpdate))
            lastStoredNote = noteToUpdate;
    }

    private void notifyUpdateListener() {
        for (NotesModelListener listener : listeners)
            listener.didUpdateNote();
    }
    @Override
    public void selectNote(String noteTitle) {
        WaitSimulator.simulateShortWait();
        selectedNote = notesRepository.retreiveNote(noteTitle);
        noteEditorView.updateNoteFields(selectedNote);
        updateNoteNow(noteTitle, selectedNote.getTextContent());
        notifySelectionListener();
    }

    private void notifySelectionListener() {
        for (NotesModelListener listener : listeners)
            listener.didSelectNote();
    }

    public NoteEditorView getNoteEditorView() {
        return noteEditorView;
    }
}
