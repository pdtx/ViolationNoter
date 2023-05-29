package com.yaki.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.yaki.data.DataCenter;
import com.yaki.dialog.AddNoteDialog;
import com.yaki.dialog.SetCommitIdDialog;
import com.yaki.utils.ContentUtils;
import com.yaki.window.ScanListWindow;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScanFile extends AnAction {
    //获取当前提交号
//    public static String getCurrentGitBranch() throws IOException, InterruptedException {
//        Process process = Runtime.getRuntime().exec( "git rev-parse --abbrev-ref HEAD" );
//        process.waitFor();
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader( process.getInputStream() ) );
//        return reader.readLine();
//    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        //获取文件名
        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile();
        String url = virtualFile.getPath();

        DataCenter.file_path = url.substring(url.indexOf("src"));

        String repoName = url.substring(0,url.indexOf("/src"));//"E:/projectSet/jmeter"
        DataCenter.repoName = repoName.substring(repoName.lastIndexOf("/")+1);

        System.out.println("file_path:"+ DataCenter.file_path);

        if (!DataCenter.login){
            //如果未登录

        }

        SetCommitIdDialog dialog = new SetCommitIdDialog();
        dialog.show();
    }
}
