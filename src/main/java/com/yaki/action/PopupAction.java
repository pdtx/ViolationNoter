package com.yaki.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.yaki.data.DataCenter;
import com.yaki.dialog.AddNoteDialog;
import org.jetbrains.annotations.NotNull;

public class PopupAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        //获取鼠标选中内容
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        DataCenter.SELECT_TXT = selectionModel.getSelectedText();

        //获取文件名
        String name = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        DataCenter.FILE_NAME  =name;
        AddNoteDialog dialog = new AddNoteDialog();
        dialog.show();

        System.out.println(DataCenter.SELECT_TXT);
    }
}
