package edu.mcckc.gui;

import edu.mcckc.dao.TaskDao;
import edu.mcckc.domain.Task;
import edu.mcckc.domain.Categories;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelDisplay
        extends JPanel
         implements ActionListener
{
   private static Logger logger = LogManager.getLogger(PanelDisplay.class);

   private JDataInput datName;
   //private JDataInput datPrice;

   private JCheckBox chkUseComplete;

   private JCheckBox chkUsePriority;
   private JCheckBox chkUseType;

   private JCheckBox chkComplete;

   private JRadioButton rdoLow;
   private JRadioButton rdoMedium;
   private JRadioButton rdoHigh;
   private ButtonGroup grpPriorities;

   private JComboBox cboTypes;
   private JButton btnSubmit;

   private JTextArea taOutput;
   private JScrollPane spOutput;


   public PanelDisplay()
   {
      datName = new JDataInput("Name:", false);
      //datPrice = new JDataInput("Price:", false);

      datName.setInputValue("");
      //datPrice.setInputValue("");


      chkUseComplete = new JCheckBox("Use Complete");
      // = new JCheckBox("Use Price");
      chkUsePriority = new JCheckBox("Use Priority");
      chkUseType = new JCheckBox("Use Type");


      chkComplete = new JCheckBox("Complete");

      rdoLow = new JRadioButton("Low");
      rdoMedium = new JRadioButton("Medium");
      rdoHigh = new JRadioButton("High");
      grpPriorities = new ButtonGroup();
      grpPriorities.add(rdoLow);
      grpPriorities.add(rdoMedium);
      grpPriorities.add(rdoHigh);
      cboTypes = new JComboBox<Categories>(Categories.values());

      btnSubmit = new JButton("Submit");
      btnSubmit.addActionListener(this);
      btnSubmit.setActionCommand("submit");

      taOutput = new JTextArea(20, 70);
      spOutput = new JScrollPane(taOutput);

      Box boxVertical = Box.createVerticalBox();

      boxVertical.add(datName);

      Box boxHorizontalUse = Box.createHorizontalBox();
      //boxHorizontalUse.add();
      boxHorizontalUse.add(chkUseComplete);
      boxHorizontalUse.add(chkUsePriority);
      boxHorizontalUse.add(chkUseType);
      boxVertical.add(boxHorizontalUse);

      Box boxHorizontalCriteria = Box.createHorizontalBox();
      //boxHorizontalCriteria.add(datPrice);
      boxHorizontalCriteria.add(chkComplete);
      boxHorizontalCriteria.add(rdoLow);
      boxHorizontalCriteria.add(rdoMedium);
      boxHorizontalCriteria.add(rdoHigh);
      boxHorizontalCriteria.add(cboTypes);
      boxVertical.add(boxHorizontalCriteria);


      boxVertical.add(btnSubmit);

      boxVertical.add(spOutput);

      add(boxVertical);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      logger.debug("    ABOUT TO DO FILTERING !!!!!!!!!!!");

      TaskDao dao = new TaskDao("sqliteconfig.properties");
      Task filter = new Task();

      logger.debug(" DEFAULT FILTER VALUES: " + filter.toStringFull());

      filter.name = datName.getStringValue();

      //if (chkUsePrice.isSelected())
      //{
      //   filter.price = datPrice.getDoubleValue();
      //}

      if (chkUseComplete.isSelected())
      {
         filter.complete = chkComplete.isSelected();
      }

      if (chkUsePriority.isSelected())
      {
         if (rdoLow.isSelected())
         {
            filter.priority = 1;
         }
         if (rdoMedium.isSelected())
         {
            filter.priority = 2;
         }
         if (rdoHigh.isSelected())
         {
            filter.priority = 3;
         }
      }

      if (chkUseType.isSelected())
      {
         Categories selectedColor = (Categories) cboTypes.getSelectedItem();
         filter.type = selectedColor.ordinal();
      }

      taOutput.setText("");

      try
      {
         ArrayList<Object> products = dao.getManyObjects(filter);
         for(Object temp : products)
         {
            Task prd = (Task)temp;
            taOutput.append(prd.toStringFull());
            taOutput.append("\n");
         }
      }
      catch (Exception ex)
      {
         logger.error(ex.toString());
      }

   }
}
