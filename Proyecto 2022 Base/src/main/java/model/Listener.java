package model;

import java.sql.SQLException;

public interface Listener {

    public void didUpdateListener();

    public void fetchPage();

    public void finishSearch();

    public void notifyViewSaveCorrect();

    public void notifyViewDeleteCorrect();

    public void notifyViewErrorLoadDB(SQLException exception);

    public void notifyViewErrorDeleting(SQLException exception);

    public void notifyViewErrorSavingLocally(SQLException exception);
}
