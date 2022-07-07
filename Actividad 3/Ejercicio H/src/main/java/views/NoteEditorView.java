package main.java.views;

import main.java.model.Note;

import javax.swing.*;

public interface NoteEditorView extends BaseView {

    void startWaitingStatus();

    void stopWaitingStatus();

    String getUpdateText();

    String getTextContent();

    void cleanFields();

    String getTitle();

    void updateNoteFields(Note note);

    void setTitle(String title);

    void setContent(String content);

    JLabel getLastUpdateLbl();
}
