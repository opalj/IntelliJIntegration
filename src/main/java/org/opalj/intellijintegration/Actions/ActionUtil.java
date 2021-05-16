/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

public class ActionUtil {
  public static String ExtString(AnActionEvent e) {
    VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
    return virtualFile != null ? virtualFile.getExtension() : "";
  }

  public static boolean nonNull(Object... objects) {
    for (Object obj : objects) if (obj == null) return false;
    return true;
  }
}
