package main.java.views;

public interface NoteEditorView extends BaseView{

  void cleanFields();

  void startWaitingStatus();

  void stopWaitingStatus();





  void setNoteTitle(String title);

  String getNoteTitle();

  void setNoteContentText(String contentText);

  String getNoteContentText();

  void setTime(String time);

  String getTime();
}
