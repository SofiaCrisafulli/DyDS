package main.java.model.repository;

import main.java.model.Note;

public interface NotesRepository {
    boolean storeNote(Note note);

    Note retrieveNote(String noteTitle);

}
