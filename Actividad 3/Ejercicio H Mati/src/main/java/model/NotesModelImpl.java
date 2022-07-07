package main.java.model;

import main.java.model.repository.NotesRepository;
import main.java.utils.DateManager;
import main.java.utils.WaitSimulator;

public class NotesModelImpl implements NotesModel {

    private DateManager dateManager;
    private NotesRepository notesRepository;
    private Note selectedNote;
    private Note lastStoredNote;
    private NotesModelListerListener notesModelListerListener;

    @Override
    public void setNotesModelListerListener(NotesModelListerListener listener) {
        notesModelListerListener = listener;
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
    public void updateNote(String title, String content, NotesModelEditorListener listener) {
        if (Note.isValidTitleForNote(title)) {
            WaitSimulator.simulateLongWait();
            updateNoteNow(title, content);
            notifyUpdateListener(listener);
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

    private void notifyUpdateListener(NotesModelEditorListener listener) {
        listener.didUpdateNote();
    }

    private void notifySelectionListener() {
        notesModelListerListener.didSelectNote();
    }

    @Override
    public void selectNote(String noteTitle) {
        WaitSimulator.simulateShortWait();
        selectedNote = notesRepository.retrieveNote(noteTitle);
        notifySelectionListener();
    }
}
