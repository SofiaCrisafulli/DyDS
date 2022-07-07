package main.java.views;

import main.java.presenter.NotesListerPresenter;

import javax.swing.*;
import java.awt.*;

public class NoteListerViewImpl implements NoteListerView{

    private JPanel content;
    private JList notesJList;
    private JButton createNewNoteBtn;

    private NotesListerPresenter notesListerPresenter;
    private DefaultListModel<String> notesListInternalModel = new DefaultListModel<>();


    public NoteListerViewImpl(NotesListerPresenter notesListerPresenter) {
        this.notesListerPresenter = notesListerPresenter;
        initListeners();
        notesJList.setModel(notesListInternalModel);

    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Notes");
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override public Container getContent() {
        return content;
    }

    @Override public void selectNone(){
        notesJList.clearSelection();
    }

    @Override
    public void updateNoteList(String noteTitleToAddOrUpdate) {
        notesListInternalModel.removeElement(noteTitleToAddOrUpdate);
        notesListInternalModel.insertElementAt(noteTitleToAddOrUpdate, 0);
    }

    private void initListeners() {
        notesJList.addListSelectionListener(listSelectionEvent -> {
            int selectedIndex = notesJList.getSelectedIndex();
            if(selectedIndex >= 0)
                notesListerPresenter.onEventSelectedNoteTitle(notesListInternalModel.elementAt(selectedIndex));
        });

        createNewNoteBtn.addActionListener(actionEvent -> {
            notesListerPresenter.onEventCreateNewNote();
        });
    }
}
