package Editors.HTMLEditor;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.filters.XmlTagFilter;
import com.intellij.psi.scope.processor.FilterElementProcessor;
import com.intellij.psi.xml.XmlAttribute;
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
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class MyStructureViewTreeElement extends PsiTreeElementBase<XmlFile> {
  private final boolean myInStructureViewPopup;
  private HTMLEditor htmlEditor;

  MyStructureViewTreeElement(
      final boolean inStructureViewPopup, final XmlFile xmlFile, HTMLEditor htmlEditor) {
    super(xmlFile);
    myInStructureViewPopup = inStructureViewPopup;
    this.htmlEditor = htmlEditor;
  }

  @Override
  public void navigate(boolean requestFocus) {
    WebEngine webEngine = htmlEditor.getWebEngine();

    // this is the root (i.e. class-file-name itself) only !!
    String classID = "class_file_header";
    Runnable run = () -> webEngine.executeScript("scrollTo(\"" + classID + "\")");
    Platform.runLater(run);
  }

  @Override
  public Icon getIcon(boolean open) {
    // get the PsiFile of the corresponding .class file
    VirtualFile classFile = htmlEditor.getFile();
    Document document = FileDocumentManager.getInstance().getDocument(classFile);
    PsiFile psiFile = PsiDocumentManager.getInstance(htmlEditor.getProject()).getPsiFile(document);

    int flags = Iconable.ICON_FLAG_READ_STATUS | Iconable.ICON_FLAG_VISIBILITY;

    // the psiFile is at the same time the root element
    return psiFile.getIcon(flags);
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

    if (rootTags.isEmpty()) {
      return Collections.emptyList();
    } else if (rootTags.size() == 1) {
      XmlTag rootTag = rootTags.get(0);
      XmlTag members = getChildrenBaseForMembers(rootTag);
      List<StructureViewTreeElement> result = createMemberTree(members);
      return result;
    }
    // TODO: this case covers multiple roots and is probably not needed
    else {
      final Collection<StructureViewTreeElement> result = new ArrayList<>(rootTags.size());
      for (XmlTag tag : rootTags) {
        result.add(new HtmlTagTreeElement(tag, htmlEditor));
      }
      return result;
    }
  }

  // TODO: Hardcoded at the moment, can improve this somehow?
  private XmlTag getChildrenBaseForMembers(@NotNull XmlTag rootTag) {
    int length = rootTag.getSubTags().length;
    XmlTag body = rootTag.getSubTags()[length - 1];

    length = body.getSubTags().length;
    XmlTag divClassFile = body.getSubTags()[length - 1];

    length = divClassFile.getSubTags().length;
    XmlTag divMembers = divClassFile.getSubTags()[length - 1];

    return divMembers;
  }

  private List<StructureViewTreeElement> createMemberTree(@NotNull XmlTag members) {
    List<StructureViewTreeElement> result = new ArrayList<>();

    // go in reverse-order, so that methods are displayed before fields!
    for (int i = members.getSubTags().length - 1; i >= 0; --i) {
      XmlTag sub = members.getSubTags()[i];
      XmlAttribute classAttribute = sub.getAttribute("class");

      if (classAttribute == null) {
        continue;
      } else if (classAttribute.getValue().equals("fields")) {
        XmlTag details = sub.findSubTags("details")[0];
        result.addAll(new HtmlTagTreeElement(details, htmlEditor).getChildrenBase());
      } else if (classAttribute.getValue().equals("methods")) {
        XmlTag details = sub.findSubTags("details")[0];
        result.addAll(new HtmlTagTreeElement(details, htmlEditor).getChildrenBase());
      }
    }

    return result;
  }

  @Override
  @Nullable
  public String getPresentableText() {
    // default: HtmlFile:[filename].html
    String defaultText = toString();
    String className =
        defaultText.substring(defaultText.indexOf(":") + 1, defaultText.indexOf("."));
    return className;
  }
}
