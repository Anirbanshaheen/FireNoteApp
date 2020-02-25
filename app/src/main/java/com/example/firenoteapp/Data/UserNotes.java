package com.example.firenoteapp.Data;

import java.io.Serializable;

public class UserNotes implements Serializable {

    String noteTitle = "", noteDescription = "", noteDate = "", noteId = "";

    public UserNotes(String noteTitle, String noteDescription, String noteDate, String noteId) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
        this.noteId = noteId;
    }

    public UserNotes(){

    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
