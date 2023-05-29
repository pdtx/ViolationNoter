package com.yaki.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

//此工厂就是要将notelistwindow里的内容创建出来
public class NoteListWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //创建NoteListWindow对象
        NoteListWindow noteListWindow = new NoteListWindow(project, toolWindow);
        //获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        //获取用于toolwindow显示的内容
        Content content = contentFactory.createContent(noteListWindow.getContentPanel(),"",false);
        //给toolwindow设置内容
        toolWindow.getContentManager().addContent(content);
    }
}
