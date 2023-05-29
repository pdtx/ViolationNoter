package com.yaki.processor;

import com.yaki.data.DataCenter;
import com.yaki.data.NoteData;

import java.io.*;
import java.util.List;

public class DataConvert {
    List<NoteData> noteList = DataCenter.NOTE_LIST;

    public void dataConvert(SourceNoteData sourceNoteData) throws Exception {


        String fileName = sourceNoteData.getFileName();
        File file = new File(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        bufferedWriter.write("## " + DataCenter.HEAD + "\n" + "[TOC]\n");
//        bufferedWriter.newLine();
//        String res = "## "+DataCenter.HEAD+"\n"+"[TOC]\n";
        for (NoteData note : noteList) {
            bufferedWriter.write("### " + note.getTitle() + "\n");
            bufferedWriter.write("- " + note.getMark() + "\n");
            bufferedWriter.write("- " + note.getFileName() + "\n");
            bufferedWriter.write("```" + note.getFileType() + "\n");
            bufferedWriter.write(note.getContent() + "\n" + "```\n");
//            res += "### "+note.getTitle()+"\n";
//            res += "- "+note.getMark()+"\n";
//            res += "- "+note.getFileName()+"\n";
//            res += "```"+note.getFileType()+"\n";
//            res += note.getContent()+"\n"+"```\n";
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

}
