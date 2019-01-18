package HTMLEditor;

import com.intellij.openapi.util.IconLoader;
import javax.swing.*;

public interface OutlineIcons {
  Icon DUMMY_ICON = IconLoader.getIcon("/icons/jar-gray.png");

  // Common

  Icon METHOD = IconLoader.getIcon("/icons/common/method.png");
  Icon CLASS_TYPE_MAIN = IconLoader.getIcon("/icons/common/classTypeMain.png");

  Icon METHOD_PUBLIC = IconLoader.getIcon("icons/abc/method_public.png");
  Icon METHOD_PRIVATE = IconLoader.getIcon("icons/abc/method_private.png");
  Icon METHOD_PROTECTED = IconLoader.getIcon("icons/abc/method_protected.png");
  Icon METHOD_PACKAGE = IconLoader.getIcon("icons/abc/method_package.png");

  Icon METHOD_STATIC_PUBLIC = IconLoader.getIcon("icons/abc/method_static_public.png");
  Icon METHOD_STATIC_PRIVATE = IconLoader.getIcon("icons/abc/method_static_private.png");
  Icon METHOD_STATIC_PROTECTED = IconLoader.getIcon("icons/abc/method_static_protected.png");
  Icon METHOD_STATIC_PACKAGE = IconLoader.getIcon("icons/abc/method_static_package.png");

  Icon METHOD_STATIC_FINAL_PUBLIC = IconLoader.getIcon("icons/abc/method_static_final_public.png");
  Icon METHOD_STATIC_FINAL_PRIVATE =
      IconLoader.getIcon("icons/abc/method_static_final_private.png");
  Icon METHOD_STATIC_FINAL_PROTECTED =
      IconLoader.getIcon("icons/abc/method_static_final_protected.png");
  Icon METHOD_STATIC_FINAL_PACKAGE =
      IconLoader.getIcon("icons/abc/method_static_final_package.png");

  Icon METHOD_FINAL_PUBLIC = IconLoader.getIcon("icons/abc/method_final_public.png");
  Icon METHOD_FINAL_PRIVATE = IconLoader.getIcon("icons/abc/method_final_private.png");
  Icon METHOD_FINAL_PROTECTED = IconLoader.getIcon("icons/abc/method_final_protected.png");
  Icon METHOD_FINAL_PACKAGE = IconLoader.getIcon("icons/abc/method_final_package.png");
}
