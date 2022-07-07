package main.java.presenter;

import main.java.model.Note;
import main.java.model.NotesModel;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;

import java.util.ArrayList;

public class NotesListerPresenterImpl implements NotesListerPresenter{

    private NotesModel notesModel;
    private NoteListerView noteListerView;
    private ArrayList<NotesEditorPresenter> listOfActiveEditorPresenters;
    private NotesEditorPresenter actualEditorPresenter;

    private Thread taskThread;

    public NotesListerPresenterImpl(NotesModel notesModel) {
        listOfActiveEditorPresenters = new ArrayList<>();
        this.notesModel = notesModel;
        initListeners();
    }

    @Override
    public void start() {
        noteListerView = new NoteListerViewImpl(this);
        noteListerView.showView();
    }

    @Override public void setNoteListerView(NoteListerView noteListerView) { this.noteListerView = noteListerView; }

    @Override public void listerClosed() { noteListerView = null; }

    @Override public boolean isActivelyWorking() {
        return taskThread.isAlive();
    }

    @Override
    public void removeEditorPresenter(NotesEditorPresenter notesEditorPresenter){
        this.listOfActiveEditorPresenters.remove(notesEditorPresenter);
    }

    @Override
    public void addNewNoteEditorPresenter(NotesEditorPresenter newPresenter){
        this.listOfActiveEditorPresenters.add(newPresenter);
    }

    @Override
    public void onEventCreateNewNote(){
        createAndShowViewEditorViewWhenNecessary();
        actualEditorPresenter.onEventCreateNewNote();
    }

    @Override
    public void onEventSelectedNoteTitle(String title) {
        taskThread = new Thread(() -> {
            noteListerView.selectNone();
            if(!noteEditorPresenterOfNoteIsActive(title)) {
                createAndShowViewEditorViewWhenNecessary();
            }
            actualEditorPresenter.startWaitingStatus();
            notesModel.selectNote(title);
            actualEditorPresenter.stopWaitingStatus();
        });
        taskThread.start();
    }

    private boolean noteEditorPresenterOfNoteIsActive(String title){
        boolean isActive = false;
        for(NotesEditorPresenter noteEditorPresenter2Check: listOfActiveEditorPresenters){
            if(noteEditorPresenter2Check.getMyNoteTitle().equals(title)){
                actualEditorPresenter = noteEditorPresenter2Check;
                isActive = true;
                break;
            }
        }
        return isActive;
    }

    private void createAndShowViewEditorViewWhenNecessary(){
        NotesEditorPresenter newNoteEditorPresenter = new NotesEditorPresenterImpl(notesModel);
        actualEditorPresenter = newNoteEditorPresenter;
        actualEditorPresenter.setNotesListerPresenter(this);
        actualEditorPresenter.start();
    }

    private void initListeners() { notesModel.setNotesModelListerListener(() -> updateFieldsOfSelectedNote()); }

    @Override public void updateFieldsOfSelectedNote() { updateNoteFields(notesModel.getSelectedNote()); }

    private void updateNoteFields(Note note) {
        if(note != null){
            actualEditorPresenter.setFieldsOfNote(note);
        }
    }

    @Override
    public void updateNoteList() {
        String noteTitleToAddOrUpdate = notesModel.getLastUpdatedNote().getTitle();
        noteListerView.updateNoteList(noteTitleToAddOrUpdate);
    }
}
