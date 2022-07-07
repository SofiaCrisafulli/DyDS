package main.java.views;

import main.java.presenter.NotesEditorPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class NoteEditorViewImpl implements NoteEditorView {
    private JTextField noteTitleTF;
    private JButton updateBtn;
    private JLabel lastUpdateLbl;
    protected JPanel content;
    private JPanel updateIconCard;
    private JLabel stoppedLoadingIcon;
    private JLabel runningLoadingIcon;
    private JPanel mainPanel;
    private JTextPane contentTextTP;

    private NotesEditorPresenter notesEditorPresenter;

    public NoteEditorViewImpl(NotesEditorPresenter notesEditorPresenter) {
        this.notesEditorPresenter = notesEditorPresenter;
        initListeners();
        lastUpdateLbl.setText("");
    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Note Editor");
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(new Random().nextInt(500), 0);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                notesEditorPresenter.editorClosed();
            }
        });
    }

    @Override
    public void cleanFields() {
        noteTitleTF.setText("");
        contentTextTP.setText("");
        lastUpdateLbl.setText("");
    }

    @Override
    public void startWaitingStatus() {
        ((CardLayout) updateIconCard.getLayout()).show(updateIconCard, "Running");
        for (Component c : mainPanel.getComponents())
            c.setEnabled(false);
    }

    @Override
    public void stopWaitingStatus() {
        ((CardLayout) updateIconCard.getLayout()).show(updateIconCard, "Stopped");
        for (Component c : mainPanel.getComponents())
            c.setEnabled(true);
    }

    private void initListeners() {
        updateBtn.addActionListener(actionEvent -> notesEditorPresenter
                .onEventUpdate(noteTitleTF.getText(), contentTextTP.getText()));
    }

    @Override
    public Container getContent() {
        return this.content;
    }

    @Override
    public void setNoteTitle(String title) {
        noteTitleTF.setText(title);
    }

    @Override
    public String getNoteTitle() {
        return noteTitleTF.getText();
    }

    @Override
    public void setNoteContentText(String contentText) {
        contentTextTP.setText(contentText);
    }

    @Override
    public String getNoteContentText() {
        return contentTextTP.getText();
    }

    @Override
    public void setTime(String time) {
        lastUpdateLbl.setText(time);
    }

    @Override
    public String getTime() {
        return lastUpdateLbl.getText();
    }

}
