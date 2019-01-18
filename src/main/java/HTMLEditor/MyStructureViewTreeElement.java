package HTMLEditor;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.filters.XmlTagFilter;
import com.intellij.psi.scope.processor.FilterElementProcessor;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.SmartList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MyStructureViewTreeElement extends PsiTreeElementBase<XmlFile> {
  private final boolean myInStructureViewPopup;
  private MyHtmlEditor htmlEditor;

  MyStructureViewTreeElement(
      final boolean inStructureViewPopup, final XmlFile xmlFile, MyHtmlEditor htmlEditor) {
    super(xmlFile);
    myInStructureViewPopup = inStructureViewPopup;
    this.htmlEditor = htmlEditor;
  }

  @Override
  public void navigate(boolean requestFocus) {
    WebEngine webEngine = htmlEditor.getWebEngine();

    // this is the root (i.e. class-file itself) only !!
    String classID = "class_file_header";
    Runnable run = () -> webEngine.executeScript("scrollTo(\"" + classID + "\")");
    Platform.runLater(run);
  }

  @Override
  public Icon getIcon(boolean open) {
    if(true) {
      return OutlineIcons.CLASS_TYPE_MAIN;
    }

    final PsiElement element = getElement();
    if (element != null) {
      int flags = Iconable.ICON_FLAG_READ_STATUS;
      if (!(element instanceof PsiFile) || !element.isWritable()) flags |= Iconable.ICON_FLAG_VISIBILITY;
      return element.getIcon(flags);
    }
    else {
      return null;
    }
  }

  @Override
  @NotNull
  public Collection<StructureViewTreeElement> getChildrenBase() {
    final XmlFile xmlFile = getElement();
    final XmlDocument document = xmlFile == null ? null : xmlFile.getDocument();
    if (document == null) {
      return Collections.emptyList();
    }

    final List<XmlTag> rootTags = new SmartList<>();
    document.processElements(new FilterElementProcessor(XmlTagFilter.INSTANCE, rootTags), document);

    // TODO: right direction, but currently hard-coded ... make it more robust !
    if (rootTags.size() == 1) {
      int length;

      XmlTag rootTag = rootTags.get(0);

      length = rootTag.getSubTags().length;
      XmlTag body = rootTag.getSubTags()[length - 1];

      length = body.getSubTags().length;
      XmlTag divClassFile = body.getSubTags()[length - 1];

      length = divClassFile.getSubTags().length;
      XmlTag divMembers = divClassFile.getSubTags()[length - 1];

      length = divMembers.getSubTags().length;
      XmlTag divMethods = divMembers.getSubTags()[length - 1];

      length = divMethods.getSubTags().length;
      XmlTag details = divMethods.getSubTags()[0];

      return new HtmlTagTreeElement(details, htmlEditor).getChildrenBase();

      //      length = details.getSubTags().length;
      //      if(length > 1) {
      //        XmlTag method1 = details.getSubTags()[1];
      //        // ...
      //        XmlTag methodN = details.getSubTags()[length-1];
      //      }
    }

    if (rootTags.isEmpty()) {
      return Collections.emptyList();
    } else if (rootTags.size() == 1) {
      final XmlTag rootTag = rootTags.get(0);
      if ("html".equalsIgnoreCase(rootTag.getLocalName())) {
        final XmlTag[] subTags = rootTag.getSubTags();
        if (subTags.length == 1
            && ("head".equalsIgnoreCase(subTags[0].getLocalName())
                || "body".equalsIgnoreCase(subTags[0].getLocalName()))) {
          return new HtmlTagTreeElement(subTags[0], htmlEditor).getChildrenBase();
        }
        return new HtmlTagTreeElement(rootTag, htmlEditor).getChildrenBase();
      }

      // final anchor (i.e. leaves of tree, e.g. "details#init()V.method") <- is it ?? NO it's not
      // !!
      return Collections.singletonList(new HtmlTagTreeElement(rootTag, htmlEditor));
    } else {
      final Collection<StructureViewTreeElement> result = new ArrayList<>(rootTags.size());
      for (XmlTag tag : rootTags) {
        result.add(new HtmlTagTreeElement(tag, htmlEditor));
      }
      return result;
    }
  }

  @Override
  @Nullable
  public String getPresentableText() {
    String defaultText = toString();
    String className =
        defaultText.substring(defaultText.indexOf(":") + 1, defaultText.lastIndexOf("."));
    return className;
  }
}
