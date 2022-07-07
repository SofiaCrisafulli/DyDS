package main.java.views;

import main.java.model.NotesModel;
import main.java.model.NotesModelImpl;
import main.java.presenter.NotesPresenter;
import main.java.model.NotesModelListener;
import main.java.presenter.PresenterEditorView;
import main.java.presenter.PresenterListerView;

import javax.swing.*;
import java.awt.*;

public class NoteListerViewImpl implements NoteListerView {

    private JPanel content;
    private JList notesJList;
    private JButton createNewNoteBtn;
    private PresenterListerView presenterListerView;
    private NotesModel notesModel;
    private DefaultListModel<String> notesListInternalModel = new DefaultListModel<>();
    private NoteEditorViewImpl noteEditorView;
    private PresenterEditorView presenterEditorView;


    public NoteListerViewImpl(PresenterListerView presenterListerView, NotesModel notesModel) {
        this.presenterListerView = presenterListerView;
        this.notesModel = notesModel;
        initListeners();
        notesJList.setModel(presenterListerView.getNotesListInternalModel());
    }

    @Override
    public Container getContent() {
        return content;
    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Notes");
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void selectNone() {
        notesJList.clearSelection();
    }

    private void initListeners() {
        presenterListerView.getNotesModel().addListener(new NotesModelListener() {
            @Override
            public void didUpdateNote() {
                presenterListerView.updateNoteList();
            }

            @Override
            public void didSelectNote() {
            }
        });

        notesJList.addListSelectionListener(listSelectionEvent -> {
            int selectedIndex = notesJList.getSelectedIndex();
            if (selectedIndex >= 0)
                presenterListerView.onEventSelectedNoteTitle(presenterListerView.getNotesListInternalModel().elementAt(selectedIndex));
        });

        createNewNoteBtn.addActionListener(actionEvent -> {
            presenterListerView.onEventCreateNewNote();
        });
    }

    public JList getNotesJList() {
        return notesJList;
    }

    public JButton getCreateNewNoteBtn() {
        return createNewNoteBtn;
    }


    public void setContent(JPanel content) {
        this.content = content;
    }

    public void setNotesJList(JList notesJList) {
        this.notesJList = notesJList;
    }

    public void setCreateNewNoteBtn(JButton createNewNoteBtn) {
        this.createNewNoteBtn = createNewNoteBtn;
    }

    public PresenterListerView getView() {
        return presenterListerView;
    }

    public void setView(PresenterListerView presenterListerView) {
        this.presenterListerView = presenterListerView;
    }

    public PresenterListerView getPresenter() {
        return presenterListerView;
    }

    public void setPresenter(PresenterListerView presenterListerView) {
        this.presenterListerView = presenterListerView;
    }
}
