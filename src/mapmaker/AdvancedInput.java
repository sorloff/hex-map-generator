package mapmaker;

import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import mapmaker.modrules.ModRule;

public class AdvancedInput extends javax.swing.JFrame {
  private final TypeRules rules;
  private String currType;
  private String currOther;
  
  /**
   * Creates new form AdvancedInput
   */
  public AdvancedInput() {
    this.rules = new TypeRules();
    
    initComponents();
    
    tableRules.getTableHeader().setReorderingAllowed(false);
    this.currType  = comboHexType.getItemAt(comboHexType.getSelectedIndex());
    this.currOther = comboOtherHex.getItemAt(comboOtherHex.getSelectedIndex());
  }
  
  private void run() {
    updateHexType();
    
    int[] dims = {Integer.parseInt(textHeight.getText()),
		  Integer.parseInt(textWidth.getText())};
    
    HexMap map = new HexMap(dims, rules, TypeRules.BLANK);
    map.makeMap();
    map.makeMapImage();
  }
  
  private ComboBoxModel<String> getTypeBoxVals() {
    return new DefaultComboBoxModel<>(rules.getNames());
  }
  
  private ComboBoxModel<String> getModBoxVals() {
    return new DefaultComboBoxModel<>(ModList.getRuleNames());
  }
  
  //TODO: implement this
  private TableModel getRulesTableVals() {
    DefaultTableModel m = new DefaultTableModel();
    
    return m;
  }
  
  private void createRule() {
    DefaultTableModel m = (DefaultTableModel) tableRules.getModel();
    
    String ruleName = comboRules.getItemAt(comboRules.getSelectedIndex());
    String ruleID   = rules.getRule(currType).createNewMod(ruleName);
    
    //TODO: hide ID field
    //TODO: make it so rules like CantSpawn don't show unnecessary data types
    m.addRow(new Object[] {ruleName, 0, "", ruleID});
  }
  
  private void deleteRule() {
    int row = tableRules.getSelectedRow();
    if (row!=-1) {
      DefaultTableModel m = (DefaultTableModel) tableRules.getModel();    

      String ruleID = (String) m.getValueAt(row, 3);
      rules.getRule(currType).getModRules().deleteRule(ruleID);
      
      m.removeRow(row);
    }
  }
  
    private void updateOther() {
    String base  = textBase.getText();
    String mod   = textMod.getText();
    String color = textColor.getText().trim().substring(1);
    
    rules.updateRule(currType, base, mod, currOther, color);
    
    currOther = comboOtherHex.getItemAt(comboOtherHex.getSelectedIndex());
    
    textMod.setText(Double.toString(rules.getRule(currType).getMod(currOther)));
  }
  
  //saves hex information and then prints new info based on currently selected types
  //should be called whenever different hex types want to be viewed, or before running to save data
  private void updateHexType() {
    String base  = textBase.getText();
    String mod   = textMod.getText();
    String color = textColor.getText().substring(1);
    
    rules.updateRule(currType, base, mod, currOther, color);
    updateTable();
    
    currType  = comboHexType.getItemAt(comboHexType.getSelectedIndex());
    
    textBase.setText(Double.toString(rules.getRule(currType).getBase()));
    textMod.setText(Double.toString(rules.getRule(currType).getMod(currOther)));
    textColor.setText("#" + Integer.toString(rules.getRule(currType).getColor(), 16));
  }
  
  //saves and updates rule table for switching to new hex type
  private void updateTable() {
    DefaultTableModel m = (DefaultTableModel) tableRules.getModel();
    
    //saving rules
    for (Vector victor : (Vector<Vector>) m.getDataVector()) {
      int val      = (int) victor.get(1);
      String other = (String) victor.get(2);
      String ID    = (String) victor.get(3);
            
      rules.updateMod(currType, ID, val, other);
      
    }
    
    String newType = comboHexType.getItemAt(comboHexType.getSelectedIndex());
    
    if (tableRules.getRowCount()!=0)
      m.setRowCount(0);
//      tableRules.removeRowSelectionInterval(0, tableRules.getRowCount());
    
    //populating rules table
    HashMap<String, ModRule> modList = rules.getRule(newType).getModRules().getMods();
    
    for (String ID : modList.keySet()) {
      System.out.println(modList.get(ID).getName());
      m.addRow(new Object[] {modList.get(ID).getName(), 
			     modList.get(ID).getVal(), 
			     modList.get(ID).getOther(),
			     ID});
    }
  }
  
  private void addHex() {
    String newName = textNewHex.getText();
    rules.addRule(newName);
    
    updateCombos();
  }
  
  private void deleteHex() {
    rules.deleteRule(comboHexType.getSelectedItem().toString());
    
    updateCombos();
  }
  
  private void updateCombos() {
    comboHexType.setModel(getTypeBoxVals());
    comboOtherHex.setModel(getTypeBoxVals());
    comboOtherRule.setModel(getTypeBoxVals());
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    comboOtherRule = new javax.swing.JComboBox<>();
    dialogNewHex = new javax.swing.JDialog();
    textNewHex = new javax.swing.JTextField();
    labelNewHex = new javax.swing.JLabel();
    buttonNewHex = new javax.swing.JButton();
    comboHexType = new javax.swing.JComboBox<>();
    labelType = new javax.swing.JLabel();
    labelBase = new javax.swing.JLabel();
    textBase = new javax.swing.JTextField();
    labelOther = new javax.swing.JLabel();
    comboOtherHex = new javax.swing.JComboBox<>();
    labelMod = new javax.swing.JLabel();
    textMod = new javax.swing.JTextField();
    labelHeight = new javax.swing.JLabel();
    textHeight = new javax.swing.JTextField();
    labelWidth = new javax.swing.JLabel();
    textWidth = new javax.swing.JTextField();
    scrollRules = new javax.swing.JScrollPane();
    tableRules = new javax.swing.JTable();
    buttonDeleteRule = new javax.swing.JButton();
    buttonNewRule = new javax.swing.JButton();
    buttonColor = new javax.swing.JButton();
    textColor = new javax.swing.JTextField();
    comboRules = new javax.swing.JComboBox<>();
    jMenuBar1 = new javax.swing.JMenuBar();
    menuFile = new javax.swing.JMenu();
    menuSave = new javax.swing.JMenuItem();
    menuRun = new javax.swing.JMenuItem();
    menuHelp = new javax.swing.JMenu();
    menuSimpleHelp = new javax.swing.JMenuItem();
    menuAdvancedHelp = new javax.swing.JMenuItem();
    menuGeneralHelp = new javax.swing.JMenuItem();
    menuOtherRules = new javax.swing.JMenuItem();
    menuOtherRules1 = new javax.swing.JMenuItem();
    menuModify = new javax.swing.JMenu();
    menuAdd = new javax.swing.JMenuItem();
    menuDelete = new javax.swing.JMenuItem();

    comboOtherRule.setModel(getTypeBoxVals());

    dialogNewHex.setTitle("Enter Hex Name");
    dialogNewHex.setMinimumSize(new java.awt.Dimension(178, 135));
    dialogNewHex.setResizable(false);

    labelNewHex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    labelNewHex.setText("Hex Name:");

    buttonNewHex.setText("Create Hex");
    buttonNewHex.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonNewHexActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout dialogNewHexLayout = new javax.swing.GroupLayout(dialogNewHex.getContentPane());
    dialogNewHex.getContentPane().setLayout(dialogNewHexLayout);
    dialogNewHexLayout.setHorizontalGroup(
      dialogNewHexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(dialogNewHexLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(dialogNewHexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(textNewHex)
          .addComponent(labelNewHex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(buttonNewHex, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    dialogNewHexLayout.setVerticalGroup(
      dialogNewHexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(dialogNewHexLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(labelNewHex)
        .addGap(15, 15, 15)
        .addComponent(textNewHex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(buttonNewHex)
        .addContainerGap(15, Short.MAX_VALUE))
    );

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setMinimumSize(new java.awt.Dimension(300, 300));
    setResizable(false);
    setSize(new java.awt.Dimension(300, 300));

    comboHexType.setModel(getTypeBoxVals());
    comboHexType.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        comboHexTypeActionPerformed(evt);
      }
    });

    labelType.setText("Hex Type:");

    labelBase.setText("Base Chance:");

    textBase.setText(Double.toString(rules.getRule(comboHexType
      .getItemAt(comboHexType.getSelectedIndex()))
    .getBase()));
textBase.addActionListener(new java.awt.event.ActionListener() {
  public void actionPerformed(java.awt.event.ActionEvent evt) {
    textBaseActionPerformed(evt);
  }
  });

  labelOther.setText("Other Hex:");

  comboOtherHex.setModel(getTypeBoxVals());
  comboOtherHex.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      comboOtherHexActionPerformed(evt);
    }
  });

  labelMod.setText("Modifier:");

  textMod.setText(Double.toString(rules.getRule(comboHexType.getItemAt(comboHexType.getSelectedIndex()))
    .getMod(comboOtherHex.getItemAt(comboOtherHex.getSelectedIndex())))

  );

  labelHeight.setText("Height");

  textHeight.setText("30");

  labelWidth.setText("Width");

  textWidth.setText("30");

  scrollRules.setBorder(javax.swing.BorderFactory.createTitledBorder("Special Rules"));
  scrollRules.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  scrollRules.setEnabled(false);
  scrollRules.setFocusable(false);

  tableRules.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
      "Rule", "Val ", "Other Hex", "ID"
    }
  ) {
    Class[] types = new Class [] {
      java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
    };
    boolean[] canEdit = new boolean [] {
      false, true, true, false
    };

    public Class getColumnClass(int columnIndex) {
      return types [columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return canEdit [columnIndex];
    }
  });
  tableRules.setFocusable(false);
  tableRules.getTableHeader().setReorderingAllowed(false);
  scrollRules.setViewportView(tableRules);
  if (tableRules.getColumnModel().getColumnCount() > 0) {
    tableRules.getColumnModel().getColumn(0).setPreferredWidth(100);
    tableRules.getColumnModel().getColumn(0).setCellEditor(null);
    tableRules.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboOtherRule));
    tableRules.getColumnModel().getColumn(3).setResizable(false);
    tableRules.getColumnModel().getColumn(3).setPreferredWidth(0);
  }

  buttonDeleteRule.setText("Delete Selected Rule");
  buttonDeleteRule.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      buttonDeleteRuleActionPerformed(evt);
    }
  });

  buttonNewRule.setText("Make New Rule:");
  buttonNewRule.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      buttonNewRuleActionPerformed(evt);
    }
  });

  buttonColor.setText("Color:");
  buttonColor.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      buttonColorActionPerformed(evt);
    }
  });

  textColor.setBackground(new java.awt.Color(255, 255, 255));
  textColor.setText("#" + Integer.toString(rules.getRule(comboHexType.getItemAt(comboHexType.getSelectedIndex())).getColor(), 16));
  textColor.setMinimumSize(new java.awt.Dimension(22, 22));
  textColor.setPreferredSize(new java.awt.Dimension(20, 20));

  comboRules.setModel(getModBoxVals());

  menuFile.setText("File");

  menuSave.setText("Save");
  menuSave.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuSaveActionPerformed(evt);
    }
  });
  menuFile.add(menuSave);

  menuRun.setText("Run");
  menuRun.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuRunActionPerformed(evt);
    }
  });
  menuFile.add(menuRun);

  jMenuBar1.add(menuFile);

  menuHelp.setText("Help");

  menuSimpleHelp.setText("Equations - Simple");
  menuSimpleHelp.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuSimpleHelpActionPerformed(evt);
    }
  });
  menuHelp.add(menuSimpleHelp);

  menuAdvancedHelp.setText("Equations - Advanced");
  menuAdvancedHelp.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuAdvancedHelpActionPerformed(evt);
    }
  });
  menuHelp.add(menuAdvancedHelp);

  menuGeneralHelp.setText("General Help");
  menuGeneralHelp.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuGeneralHelpActionPerformed(evt);
    }
  });
  menuHelp.add(menuGeneralHelp);

  menuOtherRules.setText("Other Rules");
  menuOtherRules.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuOtherRulesActionPerformed(evt);
    }
  });
  menuHelp.add(menuOtherRules);

  menuOtherRules1.setText("Other Rules");
  menuOtherRules1.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuOtherRules1ActionPerformed(evt);
    }
  });
  menuHelp.add(menuOtherRules1);

  jMenuBar1.add(menuHelp);

  menuModify.setText("Modify");

  menuAdd.setText("Add Type");
  menuAdd.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuAddActionPerformed(evt);
    }
  });
  menuModify.add(menuAdd);

  menuDelete.setText("Delete Type");
  menuDelete.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      menuDeleteActionPerformed(evt);
    }
  });
  menuModify.add(menuDelete);

  jMenuBar1.add(menuModify);

  setJMenuBar(jMenuBar1);

  javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
  getContentPane().setLayout(layout);
  layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(layout.createSequentialGroup()
      .addContainerGap()
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(scrollRules)
        .addGroup(layout.createSequentialGroup()
          .addComponent(labelOther)
          .addGap(23, 23, 23)
          .addComponent(comboOtherHex, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
            .addGroup(layout.createSequentialGroup()
              .addComponent(labelMod)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(textMod))
            .addGroup(layout.createSequentialGroup()
              .addComponent(labelHeight)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(textHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(labelWidth)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(textWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addComponent(buttonColor)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(textColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(buttonDeleteRule, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)))
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelType)
            .addGroup(layout.createSequentialGroup()
              .addComponent(labelBase, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(textBase, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(comboHexType, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
          .addGap(18, 18, 18)
          .addComponent(buttonNewRule)
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(comboRules, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
      .addContainerGap())
  );
  layout.setVerticalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(layout.createSequentialGroup()
      .addContainerGap()
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(labelType)
        .addComponent(comboHexType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(buttonNewRule)
        .addComponent(comboRules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
      .addGap(7, 7, 7)
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(labelMod)
        .addComponent(textMod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(labelOther)
        .addComponent(comboOtherHex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(buttonColor)
          .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(labelWidth)
          .addComponent(textWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(buttonDeleteRule))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(labelBase)
          .addComponent(textBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(labelHeight)
          .addComponent(textHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(scrollRules, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addContainerGap())
  );

  pack();
  }// </editor-fold>//GEN-END:initComponents

  private void menuAdvancedHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAdvancedHelpActionPerformed
    JOptionPane.showMessageDialog(null, "Equation is:\n"
				      + "Given hex types n_1, n_2, n_3, etc:\n"
				      + "Given hex type base values b_1, b_2, b_3, etc:\n"
				      + "Given r = random number in range: (0, sum(b)]:\n"
				      + "Given s_i = sum(b_1 to b_i). That is, the sum of all the preceding hex type base values:\n"
				      + "If s_i-1 < r < s_i: hex type = n_i\n"
				      + "Modifiers change the b_# values directly. Remember, everything is normalized.");
  }//GEN-LAST:event_menuAdvancedHelpActionPerformed

  private void menuSimpleHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSimpleHelpActionPerformed
    JOptionPane.showMessageDialog(null, "All base values and modifiers are relative to eachother, "
				      + "so setting base values 0.2 and 0.5 is equivalent to setting base values 2 and 5.\n"
				      + "Higher base values = higher base chance of spawning.\n"
				      + "Modifiers essentially change base values before the calculation is done.");
  }//GEN-LAST:event_menuSimpleHelpActionPerformed

  private void menuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveActionPerformed
    JOptionPane.showMessageDialog(null, "Sorry, this hasn't been implemented yet!");
  }//GEN-LAST:event_menuSaveActionPerformed

  private void comboHexTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboHexTypeActionPerformed
    updateHexType();
  }//GEN-LAST:event_comboHexTypeActionPerformed

  private void textBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBaseActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_textBaseActionPerformed

  private void comboOtherHexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboOtherHexActionPerformed
    updateOther();
  }//GEN-LAST:event_comboOtherHexActionPerformed

  private void menuGeneralHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGeneralHelpActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_menuGeneralHelpActionPerformed

  private void menuOtherRulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOtherRulesActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_menuOtherRulesActionPerformed

  private void menuOtherRules1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOtherRules1ActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_menuOtherRules1ActionPerformed

  private void menuAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddActionPerformed
    dialogNewHex.setVisible(true);
  }//GEN-LAST:event_menuAddActionPerformed

  private void menuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeleteActionPerformed
    deleteHex();
  }//GEN-LAST:event_menuDeleteActionPerformed

  private void buttonColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonColorActionPerformed
    //changes the color of the background to show the hex color
    //changes the color of the text so to stay visible
    Color bg = Color.decode(textColor.getText());
    
    float[] tx = new float[3];
    Color.RGBtoHSB(255-bg.getRed(), 
		   255-bg.getGreen(), 
		   255-bg.getBlue(), 
		   tx);
    
    textColor.setBackground(bg);
    textColor.setForeground(Color.getHSBColor(tx[0], tx[1], tx[2]));
  }//GEN-LAST:event_buttonColorActionPerformed

  private void buttonNewRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewRuleActionPerformed
    createRule();
  }//GEN-LAST:event_buttonNewRuleActionPerformed

  private void buttonDeleteRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteRuleActionPerformed
    deleteRule();
  }//GEN-LAST:event_buttonDeleteRuleActionPerformed

  private void menuRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRunActionPerformed
    run();
  }//GEN-LAST:event_menuRunActionPerformed

  private void buttonNewHexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewHexActionPerformed
    addHex();
  }//GEN-LAST:event_buttonNewHexActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(AdvancedInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(AdvancedInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(AdvancedInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(AdvancedInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
	new AdvancedInput().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonColor;
  private javax.swing.JButton buttonDeleteRule;
  private javax.swing.JButton buttonNewHex;
  private javax.swing.JButton buttonNewRule;
  private javax.swing.JComboBox<String> comboHexType;
  private javax.swing.JComboBox<String> comboOtherHex;
  private javax.swing.JComboBox<String> comboOtherRule;
  private javax.swing.JComboBox<String> comboRules;
  private javax.swing.JDialog dialogNewHex;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JLabel labelBase;
  private javax.swing.JLabel labelHeight;
  private javax.swing.JLabel labelMod;
  private javax.swing.JLabel labelNewHex;
  private javax.swing.JLabel labelOther;
  private javax.swing.JLabel labelType;
  private javax.swing.JLabel labelWidth;
  private javax.swing.JMenuItem menuAdd;
  private javax.swing.JMenuItem menuAdvancedHelp;
  private javax.swing.JMenuItem menuDelete;
  private javax.swing.JMenu menuFile;
  private javax.swing.JMenuItem menuGeneralHelp;
  private javax.swing.JMenu menuHelp;
  private javax.swing.JMenu menuModify;
  private javax.swing.JMenuItem menuOtherRules;
  private javax.swing.JMenuItem menuOtherRules1;
  private javax.swing.JMenuItem menuRun;
  private javax.swing.JMenuItem menuSave;
  private javax.swing.JMenuItem menuSimpleHelp;
  private javax.swing.JScrollPane scrollRules;
  private javax.swing.JTable tableRules;
  private javax.swing.JTextField textBase;
  private javax.swing.JTextField textColor;
  private javax.swing.JTextField textHeight;
  private javax.swing.JTextField textMod;
  private javax.swing.JTextField textNewHex;
  private javax.swing.JTextField textWidth;
  // End of variables declaration//GEN-END:variables
}
