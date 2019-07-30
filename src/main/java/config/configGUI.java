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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class configGUI {
  private JTabbedPane tabbedPane1;
  private JPanel rootPanel;
  private JRadioButton besteEinstellungRadioButton;
  private JRadioButton zweitBesteEinstellungRadioButton;
  private JTree tree1;
  private JList list1;
  private JRadioButton TacRadioButton3;
  private JRadioButton TacRadioButton1;
  private JRadioButton TacRadioButton2;
  //####################
  private final BytecodeConfig BCConfig = BytecodeConfig.getInstance();
  public configGUI(){
    TacRadioButton1.addItemListener(listener);
    TacRadioButton2.addItemListener(listener);
    TacRadioButton3.addItemListener(listener);
    switch (BCConfig.getTacKey()){
      case ONE: TacRadioButton1.setSelected(true);TacConfig=TACKey.ONE;break;
      case TWO: TacRadioButton2.setSelected(true);TacConfig=TACKey.TWO;break;
      case THREE: TacRadioButton3.setSelected(true);TacConfig=TACKey.THREE;break;
    }
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
    return modified;
  }
  public void apply(){
    BCConfig.setTacKey(TacConfig);
  }

  private TACKey TacConfig;

  private ItemListener listener = e -> {
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
}
