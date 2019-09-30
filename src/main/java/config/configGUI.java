/*
 * BSD 2-Clause License:
 * Copyright (c) 2018 - 2019
 * Software Technology Group
 * Department of Computer Science
 * Technische Universität Darmstadt
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package config;

import globalData.TACKey;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class configGUI {
  private JTabbedPane tabbedPane1;
  private JPanel rootPanel;
  private JTree tree1;
  private JList list1;
  private JRadioButton TacRadioButton3;
  private JRadioButton TacRadioButton1;
  private JRadioButton TacRadioButton2;
  private JTextArea ConfigTextArea;
  private JRadioButton loadLastUsedProjectConfigRadioButton;
  //####################
  private final BytecodeConfig BCConfig = BytecodeConfig.getInstance();
  public configGUI(){
    // TAC-Configs
    TacRadioButton1.addItemListener(tacButtonListener);
    TacRadioButton2.addItemListener(tacButtonListener);
    TacRadioButton3.addItemListener(tacButtonListener);
    switch (BCConfig.getTacKey()){
      case ONE: TacRadioButton1.setSelected(true);TacConfig=TACKey.ONE;break;
      case TWO: TacRadioButton2.setSelected(true);TacConfig=TACKey.TWO;break;
      case THREE: TacRadioButton3.setSelected(true);TacConfig=TACKey.THREE;break;
    }
    // Project Load Configs
    ProjectConfigTextChanged = false;
    ProjectConfigString = BCConfig.getProjectConfigString();
    ConfigTextArea.setText(ProjectConfigString);
    ConfigTextArea.getDocument().addDocumentListener(projectConfigLisener);
  }
  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setRootPanel(JPanel rootPanel) {
    this.rootPanel = rootPanel;
  }

  public boolean isModified(){
    boolean modified = false;
    modified |= !(BCConfig.getTacKey().compareTo(TacConfig) == 0);
    modified |= ProjectConfigTextChanged;
    return modified;
  }
  public void apply(){
    BCConfig.setTacKey(TacConfig);
    //Project-Config
    ProjectConfigTextChanged = false;
    BCConfig.setProjectConfigString(ProjectConfigString);
  }
// TAC-Config-Lisener
  private TACKey TacConfig;

  private ItemListener tacButtonListener = e -> {
    if(e.getStateChange() == ItemEvent.SELECTED) {
      JRadioButton jRadioButton = (JRadioButton) e.getSource();
      if (jRadioButton == TacRadioButton1) {
        TacConfig = TACKey.ONE;
      } else if (jRadioButton == TacRadioButton2) {
        TacConfig = TACKey.TWO;
      } else if (jRadioButton == TacRadioButton3) {
        TacConfig = TACKey.THREE;
      }
    }
  };
  // Project-Config-Lisener
  private boolean ProjectConfigTextChanged;
  private String ProjectConfigString;
  private DocumentListener projectConfigLisener = new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
      changedModfied(e);
    }
    @Override
    public void removeUpdate(DocumentEvent e) {
      changedModfied(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      changedModfied(e);
    }

    private void changedModfied(DocumentEvent e) {
      int intEndPosition = e.getDocument().getEndPosition().getOffset();
      ProjectConfigString = BCConfig.getProjectConfigString();
      try {
        ProjectConfigString = e.getDocument().getText(0,intEndPosition).trim();
      } catch (BadLocationException ex) {
        //TODO
        ex.printStackTrace();
      }
      ProjectConfigTextChanged=true;
    }
  };
}
