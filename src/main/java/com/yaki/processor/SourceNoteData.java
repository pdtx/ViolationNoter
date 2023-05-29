package com.yaki.processor;

import com.yaki.data.NoteData;

import java.util.List;

public interface SourceNoteData {
    public String getFileName();//将来要生成的文件名称
    public String getTopic();
    public List<NoteData> getNoteList();
}
