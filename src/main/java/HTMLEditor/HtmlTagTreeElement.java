package HTMLEditor;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.navigation.LocationPresentation;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.xml.util.HtmlUtil;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import jdk.nashorn.api.scripting.JSObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class HtmlTagTreeElement extends PsiTreeElementBase<XmlTag> implements LocationPresentation {
    static final int MAX_TEXT_LENGTH = 50;
    MyHtmlEditor htmlEditor;

    HtmlTagTreeElement(final XmlTag tag) {
        super(tag);
    }

    // TODO
    HtmlTagTreeElement(final XmlTag tag, MyHtmlEditor htmlEditor) {
        super(tag);
        this.htmlEditor = htmlEditor;
    }

    @Override
    public void navigate(boolean requestFocus) {
        WebEngine webEngine = htmlEditor.getWebEngine();

        // this.getPresentableText() contains the desired id: "details#[my-id].method"
        String presentableText = this.getPresentableText();
        if(presentableText.startsWith("details#") && presentableText.endsWith(".method")) {
            int begin = presentableText.indexOf("#");
            int end   = presentableText.lastIndexOf(".");

            String id = presentableText.substring(begin+1, end);

//            Messages.showInfoMessage("id = " + id, "Tag");
            Runnable run = () -> webEngine.executeScript("scrollTo(\"" + id + "\")");
            Platform.runLater(run);
        }
        // check if it's field ...
        else if(presentableText.equals("div.field.details")) {
            // TODO: probably have to adjust the HTML file to contain field names in tag-attributes (currently ambiguous)
        }
    }

    @Override
    @NotNull
    public Collection<StructureViewTreeElement> getChildrenBase() {
        final XmlTag tag = getElement();
        if (tag == null || !tag.isValid()) return Collections.emptyList();
//        return ContainerUtil.map2List(tag.getSubTags(), HtmlTagTreeElement::new);
        return ContainerUtil.map2List(tag.getSubTags(), xmlTag -> new HtmlTagTreeElement(xmlTag, htmlEditor));
    }

    @Override
    public String getPresentableText() {
        final XmlTag tag = getElement();
        if (tag == null) {
            return IdeBundle.message("node.structureview.invalid");
        }
        return HtmlUtil.getTagPresentation(tag);
    }

    @Nullable
    @Override
    public String getLocationString() {
        final XmlTag tag = getElement();
        if (tag == null) {
            return null;
        }

        if (tag.getName().equalsIgnoreCase("img") || HtmlUtil.isScriptTag(tag)) {
            return getPathDescription(tag.getAttributeValue("src"));
        }
        else if (tag.getName().equalsIgnoreCase("link")) {
            return getPathDescription(tag.getAttributeValue("href"));
        }
        else {
            return StringUtil.nullize(normalizeSpacesAndShortenIfLong(tag.getValue().getTrimmedText()));
        }
    }

    private static String getPathDescription(String src) {
        if (StringUtil.isEmpty(src)) {
            return null;
        }
        else {
            return StringUtil.shortenPathWithEllipsis(src, MAX_TEXT_LENGTH, true);
        }
    }

    @Override
    public boolean isSearchInLocationString() {
        return true;
    }

    @Nullable
    public static String normalizeSpacesAndShortenIfLong(final @NotNull String text) {
        StringBuilder builder = normalizeSpaces(text);
        return builder == null ? null : shortenTextIfLong(builder);
    }

    @Nullable
    private static StringBuilder normalizeSpaces(@NotNull String text) {
        if (text.isEmpty()) {
            return null;
        }

        final StringBuilder buf = new StringBuilder(text.length());
        for (int i = 0, length = text.length(); i < length; i++) {
            char c = text.charAt(i);
            if (c <= ' ' || Character.isSpaceChar(c)) {
                if (buf.length() == 0 || buf.charAt(buf.length() - 1) != ' ') {
                    buf.append(' ');
                }
            }
            else {
                buf.append(c);
            }
        }
        return buf;
    }

    @Nullable
    private static String shortenTextIfLong(@NotNull StringBuilder text) {
        if (text.length() <= MAX_TEXT_LENGTH) {
            return text.toString();
        }

        int index;
        for (index = MAX_TEXT_LENGTH; index > MAX_TEXT_LENGTH - 20; index--) {
            if (!Character.isLetter(text.charAt(index))) {
                break;
            }
        }

        text.setLength(Character.isLetter(index) ? MAX_TEXT_LENGTH : index);
        return text.append("\u2026").toString();
    }

    @Override
    public String getLocationPrefix() {
        return "  ";
    }

    @Override
    public String getLocationSuffix() {
        return "";
    }
}
