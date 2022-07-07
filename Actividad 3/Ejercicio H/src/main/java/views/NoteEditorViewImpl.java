package main.java.views;

import main.java.model.Note;
import main.java.model.NotesModel;
import main.java.model.NotesModelListener;
import main.java.presenter.PresenterEditorView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.ArrayList;
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

    private NotesModel notesModel;
    private PresenterEditorView presenterEditorView;


    public NoteEditorViewImpl(PresenterEditorView presenterEditorView, NotesModel notesModel) {
        this.presenterEditorView = presenterEditorView;
        this.notesModel = notesModel;
        initListeners();
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

    @Override
    public String getUpdateText() {
        return lastUpdateLbl.getText();
    }

    @Override
    public String getTextContent() {
        return contentTextTP.getText();
    }

    @Override
    public void cleanFields() {
        noteTitleTF.setText("");
        contentTextTP.setText("");
        lastUpdateLbl.setText("");
    }

    @Override
    public Container getContent() {
        return this.content;
    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Note Editor");
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        frame.pack();
        frame.setLocation(new Random().nextInt(500), 0);
        frame.setVisible(true);
    }

    private void initListeners() {
        updateBtn.addActionListener(actionEvent -> presenterEditorView.onEventUpdate(noteTitleTF.getText(), contentTextTP.getText()));
        notesModel.addListener(new NotesModelListener() {
            @Override
            public void didUpdateNote() {
               presenterEditorView.updateFieldsOfStoredNote();
            }

            @Override
            public void didSelectNote() {
                presenterEditorView.updateFieldsOfSelectedNote();
            }
        });
    }


    public void updateNoteFields(Note note) {
        if (note != null) {
            noteTitleTF.setText(note.getTitle());
            contentTextTP.setText(note.getTextContent());
            lastUpdateLbl.setText(DateFormat.getTimeInstance().format(note.getLastUpdate()));
        }
    }

    @Override
    public void setTitle(String title) {
        noteTitleTF.setText(title);
    }

    public String getTitle() {
        return noteTitleTF.getText();
    }


    public void setContent(String content) {
        this.content.setToolTipText(content);
    }

    public void close() {
        ArrayList<NoteEditorView> lista = presenterEditorView.getNoteEditorViewArrayList();
        lista.remove(this);
    }

    public JLabel getLastUpdateLbl() {
        return lastUpdateLbl;
    }
}
