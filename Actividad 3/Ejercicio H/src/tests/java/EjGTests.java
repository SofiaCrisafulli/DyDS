package tests.java;

import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.model.repository.NonPersistentNotesRepository;
import main.java.presenter.PresenterEditorView;
import main.java.presenter.PresenterListerView;
import main.java.utils.WaitSimulator;
import main.java.views.NoteEditorView;
import main.java.views.NoteEditorViewImpl;
import main.java.views.NoteListerView;
import main.java.views.NoteListerViewImpl;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class EjGTests {

    NotesModel model;
    PresenterListerView presenterListerView;
    NoteEditorView noteEditorView;
    NoteListerView noteListerView;
    Date fixedDate;
    PresenterEditorView presenterEditorView;

    @Before
    public void setUp() throws Exception {
        WaitSimulator.timeBase = 0;

        fixedDate = new Date();
        model = new NotesModelImpl();
        model.setDateManager(new StubbedDateManager(fixedDate));
        model.setNotesRepository(new NonPersistentNotesRepository());

        presenterListerView = new PresenterListerView(model);
        presenterEditorView = presenterListerView.getPresenterEditorView();
        noteEditorView = new NoteEditorViewImpl(presenterEditorView, model);
        noteListerView = new NoteListerViewImpl(presenterListerView, model);
        presenterListerView.setNoteEditorView(noteEditorView);
        presenterListerView.setNoteListerView(noteListerView);
    }

    @Test(timeout = 500)
    public void testSimpleStorage() throws InterruptedException {
        presenterEditorView.onEventUpdate("Notin", "ouch!");
        waitForControllerTask();
        presenterListerView.onEventSelectedNoteTitle("Notin");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate), presenterEditorView.getLastViewSelected().getUpdateText());
    }

    @Test(timeout = 500)
    public void testSimpleUpdate() throws InterruptedException {
        presenterEditorView.onEventUpdate("Nota 1", "da text");
        waitForControllerTask();
        fixedDate = new Date(1);
        model.setDateManager(new StubbedDateManager(fixedDate));
        presenterEditorView.onEventUpdate("Nota 1", "da text was changed");
        waitForControllerTask();
        presenterListerView.onEventSelectedNoteTitle("Nota 1");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate),  presenterEditorView.getLastViewSelected().getUpdateText());
    }


    @Test(timeout = 500)
    public void testShowSelection() throws InterruptedException {
        presenterListerView.onEventUpdate("No me elijas!", "lo hiciste :(");
        waitForControllerTask();
        presenterListerView.onEventSelectedNoteTitle("No me elijas!");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate),
                presenterEditorView.getLastViewSelected().getUpdateText());
    }

    @Test(timeout = 500)
    public void testShowUpdateAndSelectOld() throws InterruptedException {
        presenterListerView.onEventUpdate("No me elijas!", "lo hiciste :(");
        waitForControllerTask();
        presenterListerView.onEventUpdate("Elegime!", "por favor :D");
        waitForControllerTask();
        presenterListerView.onEventSelectedNoteTitle("No me elijas!");
        waitForControllerTask();
        assertEquals("lo hiciste :(", presenterEditorView.getLastViewSelected().getTextContent());
    }

    @Test(timeout = 700)
    public void testShowSelectUpdatedNoteAfterAdditions() throws InterruptedException {
        Date oldDate = fixedDate;
        presenterEditorView.onEventUpdate("No me cambies", "porfis");
        waitForControllerTask();
        presenterEditorView.onEventUpdate("Nota del Medio", "Gutierrez");
        waitForControllerTask();
        fixedDate = new Date(1);
        model.setDateManager(new StubbedDateManager(fixedDate));
        presenterEditorView.onEventUpdate("No me cambies", "lo hiciste nomas...");
        waitForControllerTask();
        presenterEditorView.onEventUpdate("Nota del Final", "Gutierrez");
        waitForControllerTask();
        presenterListerView.onEventSelectedNoteTitle("No me cambies");
        waitForControllerTask();
        assertEquals(DateFormat.getTimeInstance().format(fixedDate), presenterEditorView.getLastViewSelected().getUpdateText());
    }

    private void waitForControllerTask() throws InterruptedException {
        while (presenterListerView.isActivellyWorking()) {
            Thread.sleep(1);
        }
        while (presenterEditorView.isActivellyWorking()) {
            Thread.sleep(1);
        }
    }
}

