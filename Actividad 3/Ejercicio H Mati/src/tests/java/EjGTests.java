package tests.java;

import main.java.presenter.NotesEditorPresenter;
import main.java.presenter.NotesEditorPresenterImpl;
import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.model.repository.NonPersistentNotesRepository;
import main.java.presenter.NotesListerPresenter;
import main.java.presenter.NotesListerPresenterImpl;
import main.java.utils.WaitSimulator;
import main.java.views.NoteEditorView;
import main.java.views.NoteListerView;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;


import static org.junit.Assert.assertEquals;

public class EjGTests {

    NotesModel model;
    NotesEditorPresenter notesEditorPresenter;
    NotesListerPresenter notesListerPresenter;
    Date fixedDate;

    @Before
    public void setUp() throws Exception {
        WaitSimulator.timeBase = 0;

        model = new NotesModelImpl();
        fixedDate = new Date();
        model.setDateManager(new StubbedDateManager(fixedDate));
        model.setNotesRepository(new NonPersistentNotesRepository());

        notesListerPresenter = new NotesListerPresenterImpl(model);
        notesListerPresenter.start();

        notesEditorPresenter = new NotesEditorPresenterImpl(model);
        notesEditorPresenter.setNotesListerPresenter(notesListerPresenter);
        notesEditorPresenter.start();
        notesEditorPresenter.onEventCreateNewNote();
    }

    @Test(timeout = 500)
    public void testSimpleStorage() throws InterruptedException {
        notesEditorPresenter.onEventUpdate("Notin", "ouch!");
        waitForEditorPresenter();

        assertEquals(DateFormat.getTimeInstance().format(fixedDate), notesEditorPresenter.getMyNoteTime());
    }

    @Test(timeout = 500)
    public void testSimpleUpdate() throws InterruptedException {
        notesEditorPresenter.onEventUpdate("Nota 1", "da text");
        waitForEditorPresenter();

        fixedDate = new Date(1);
        model.setDateManager(new StubbedDateManager(fixedDate));

        notesEditorPresenter.onEventUpdate("Nota 1", "da text was changed");
        waitForEditorPresenter();

        assertEquals(DateFormat.getTimeInstance().format(fixedDate), notesEditorPresenter.getMyNoteTime());
    }

    @Test(timeout = 500)
    public void testShowSelection() throws InterruptedException {
        notesEditorPresenter.onEventUpdate("No me elijas!", "lo hiciste :(");
        waitForEditorPresenter();

        notesListerPresenter.onEventSelectedNoteTitle("No me elijas!");
        waitForEditorPresenter();

        assertEquals(DateFormat.getTimeInstance().format(fixedDate), notesEditorPresenter.getMyNoteTime());
    }

    @Test(timeout = 500)
    public void testShowUpdateAndSelectOld() throws InterruptedException {
        notesEditorPresenter.onEventUpdate("No me elijas!", "lo hiciste :(");
        waitForEditorPresenter();
        notesEditorPresenter.onEventUpdate("Elegime!", "por favor :D");
        waitForEditorPresenter();
        notesListerPresenter.onEventSelectedNoteTitle("No me elijas!");
        waitForListerPresenter();

        assertEquals("lo hiciste :(", model.getSelectedNote().getTextContent());
    }

    @Test(timeout = 500)
    public void testShowSelectUpdatedNoteAfterAdditions() throws InterruptedException {
        Date oldDate = fixedDate;
        notesEditorPresenter.onEventUpdate("No me cambies", "porfis");
        waitForEditorPresenter();
        notesEditorPresenter.onEventUpdate("Nota del Medio", "Gutierrez");
        waitForEditorPresenter();
        fixedDate = new Date(1);
        model.setDateManager(new StubbedDateManager(fixedDate));
        notesEditorPresenter.onEventUpdate("No me cambies", "lo hiciste nomas...");
        waitForEditorPresenter();
        notesEditorPresenter.onEventUpdate("Nota del Final", "Gutierrez");
        waitForEditorPresenter();
        notesListerPresenter.onEventSelectedNoteTitle("No me cambies");
        waitForEditorPresenter();


        assertEquals(DateFormat.getTimeInstance().format(fixedDate), notesEditorPresenter.getMyNoteTime());
    }

    private void waitForEditorPresenter() throws InterruptedException{
        while(notesEditorPresenter.isActivelyWorking()) Thread.sleep(1);
    }

    private void waitForListerPresenter() throws InterruptedException{
        while(notesListerPresenter.isActivelyWorking()) Thread.sleep(1);
    }

}
