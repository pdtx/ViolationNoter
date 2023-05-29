package com.yaki.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.yaki.data.DataCenter;
import com.yaki.utils.ContentUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Vector;

//创建scanFileWindow
public class ScanListWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ScanListWindow scanListWindow = new ScanListWindow(project, toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(scanListWindow.getScanPanel(),"",false);
//        Content content1 = contentFactory.createContent(scanListWindow.getDetailLabel(),"",false);
//        scanListWindow.getDetailLabel().setText(DataCenter.detail);
        toolWindow.getContentManager().addContent(content);
//        toolWindow.getContentManager().addContent(content1);


//        JList issueList = scanListWindow.getIssueList();
//        issueList.setListData((Vector) DataCenter.issueList);


    }
}
