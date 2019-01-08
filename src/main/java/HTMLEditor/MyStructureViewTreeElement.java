package HTMLEditor;

import com.intellij.ide.structureView.StructureViewFactoryEx;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.treeView.smartTree.TreeStructureUtil;
import com.intellij.lang.html.structureView.Html5SectionsNodeProvider;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyStructureViewTreeElement extends PsiTreeElementBase<XmlFile> {
  private final boolean myInStructureViewPopup;
  private MyHtmlEditor htmlEditor;

  MyStructureViewTreeElement(final boolean inStructureViewPopup, final XmlFile xmlFile) {
    super(xmlFile);
    myInStructureViewPopup = inStructureViewPopup;
  }

  // TODO: also need to call HtmlTagTreeElement constructors with MyHtmlEditor
  MyStructureViewTreeElement(
      final boolean inStructureViewPopup, final XmlFile xmlFile, MyHtmlEditor htmlEditor) {
    super(xmlFile);
    myInStructureViewPopup = inStructureViewPopup;
    this.htmlEditor = htmlEditor;
  }

  @Override
  public void navigate(boolean requestFocus) {
    // super.navigate(requestFocus);
    // Messages.showInfoMessage("I'm here", "TreeElement.navigate()");

    // this is the root only !!
  }

  @Override
  @NotNull
  public Collection<StructureViewTreeElement> getChildrenBase() {
    //        if (isHtml5SectionsMode()) {
    //            return Collections.emptyList(); // Html5SectionsNodeProvider will return its
    // structure
    //        }

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

      return Collections.singletonList(new HtmlTagTreeElement(rootTag, htmlEditor));
    } else {
      final Collection<StructureViewTreeElement> result = new ArrayList<>(rootTags.size());
      for (XmlTag tag : rootTags) {
        result.add(new HtmlTagTreeElement(tag, htmlEditor));
      }
      return result;
    }
  }

  private boolean isHtml5SectionsMode() {
    final XmlFile xmlFile = getElement();
    if (xmlFile == null) return false;

    if (myInStructureViewPopup) {
      final String propertyName =
          TreeStructureUtil.getPropertyName(
              Html5SectionsNodeProvider.HTML5_OUTLINE_PROVIDER_PROPERTY);
      if (PropertiesComponent.getInstance().getBoolean(propertyName)) {
        return true;
      }
    } else if (StructureViewFactoryEx.getInstanceEx(xmlFile.getProject())
        .isActionActive(Html5SectionsNodeProvider.ACTION_ID)) {
      return true;
    }

    return false;
  }

  @Override
  @Nullable
  public String getPresentableText() {
    return toString();
  }
}
