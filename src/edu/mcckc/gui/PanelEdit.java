package edu.mcckc.gui;

import edu.mcckc.dao.TaskDao;
import org.apache.logging.log4j.*;
import edu.mcckc.domain.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PanelEdit
        extends JPanel
        implements ActionListener
{
   private static Logger logger = LogManager.getLogger(PanelEdit.class);
   private Task selectedProduct;

   public void fillProductComboBox()
   {
      logger.debug(" !!!!    FILL THE PRODUCT COMBO BOX !!!");
      TaskDao dao = new TaskDao("sqliteconfig.properties");

      Task filter = new Task();
      filter.name = "";
      ArrayList<Object> tasks;
      try
      {
         cboTasks.removeAllItems();
         tasks = dao.getManyObjects(filter);
         for(Object obj : tasks)
         {
            cboTasks.addItem(    (Task)obj      );
         }
      }
      catch(Exception ex)
      {
         logger.error(ex.toString());
      }
   }


   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (e.getActionCommand().toLowerCase().equals("delete"))
      {
         logger.debug("PANELEDIT  !!!!    DELETE  BUTTON !!!");
         selectedProduct = (Task) cboTasks.getSelectedItem();

         logger.debug("YOU SELECTED: " + selectedProduct.toStringFull());

         try
         {
            TaskDao dao = new TaskDao("sqliteconfig.properties");
            dao.deleteSingleObject(selectedProduct);
            fillProductComboBox();
         }
         catch(Exception ex)
         {
            logger.error(ex.toString());
         }
      }


         if (e.getActionCommand().toLowerCase().equals("select"))
      {
         logger.debug("PANELEDIT  !!!!    SELECT  BUTTON !!!");

         selectedProduct = (Task) cboTasks.getSelectedItem();

         logger.debug("YOU SELECTED : " + selectedProduct.toStringFull());

         datName.setInputValue(selectedProduct.name);
         datDescription.setInputValue(selectedProduct.description);
         //datPrice.setInputValue(String.valueOf( selectedProduct.price));

         String datePurchased;
         SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
         datePurchased = df.format(selectedProduct.dateDue);
         datDateDue.setInputValue(datePurchased);

         chkComplete.setSelected( selectedProduct.complete  );

         if (selectedProduct.priority == 1)
         {
            rdoLow.setSelected(true);
         }
         if (selectedProduct.priority == 2)
         {
            rdoMedium.setSelected(true);
         }
         if (selectedProduct.priority == 3)
         {
            rdoHigh.setSelected(true);
         }


         Categories selectedColor;

         selectedColor = Categories.values()[selectedProduct.type];

         logger.debug("SELECTED CATEGORY:  " + selectedColor);

         cboType.setSelectedItem(selectedColor);

      }

      if (e.getActionCommand().toLowerCase().equals("submit"))
      {
         logger.debug("PANELEDIT  !!!!    SUBMIT  BUTTON !!!");



         Task temp = new Task();
         TaskDao dao = new TaskDao("sqliteconfig.properties");


         try
         {
            temp = (Task) cboTasks.getSelectedItem();

            String sName = datName.getStringValue();
            String sDescription = datDescription.getStringValue();

            //double dPrice = datPrice.getDoubleValue();

            boolean bComplete = chkComplete.isSelected();

            int iPriority = -1;
            if (rdoLow.isSelected())
            {
               iPriority = 1;
            }
            if (rdoMedium.isSelected())
            {
               iPriority = 2;
            }
            if (rdoHigh.isSelected())
            {
               iPriority = 3;
            }

            Categories category = (Categories) cboType.getSelectedItem();

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String sDatePurchased = datDateDue.getStringValue();
            Date dtPurchased = df.parse(sDatePurchased);


            logger.debug(String.format("ID: %d", temp.id));
            logger.debug(String.format("NAME: %s", sName));
            logger.debug(String.format("DESCRIPTION: %s", sDescription));
            //logger.debug(String.format("PRICE: %.2f", dPrice));
            logger.debug(String.format("PRIORITY: %d", iPriority));
            logger.debug(String.format("COMPLETE: %b", bComplete));
            logger.debug(String.format("TYPE: %d", category.ordinal()));
            logger.debug(String.format("DATE DUE: %s", dtPurchased.toString()));


            ////////////temp.id = temp.id;
            temp.name = sName;
            temp.type = category.ordinal();
            temp.description = sDescription;
            temp.priority = iPriority;
            temp.complete = bComplete;
            temp.dateDue = dtPurchased;

            dao.updateSingleObject(temp);

         }
         catch (Exception ex)
         {
            logger.error(temp);
            logger.error(ex.toString());
            logger.error(ex.fillInStackTrace());
         }


      }
   }

   /////////////public enum ProductColors { Red, Blue, Green, Yellow };

   private JComboBox<Task> cboTasks;
   private JButton btnSelect;
   private JButton btnDelete;

   private JDataInput datName;
   private JDataInput datDescription;
   //private JDataInput datPrice;
   private JDataInput datDateDue;
   private JCheckBox chkComplete;

   private JRadioButton rdoLow;
   private JRadioButton rdoMedium;
   private JRadioButton rdoHigh;
   private ButtonGroup grpPriority;

   private JComboBox cboType;
   private JButton btnEdit;

   public PanelEdit()
   {
      cboTasks = new JComboBox<Task>();
      btnSelect = new JButton("Select");
      btnDelete = new JButton("Delete");

      datName = new JDataInput("Name:", false);
      datDescription = new JDataInput("Description:", true);
      //datPrice = new JDataInput("Price:", false);
      datDateDue = new JDataInput("Date Purchased:", false);

      datName.setInputValue("Hammer");
      datDescription.setInputValue("This is a really awesome hammer !!!");
      //datPrice.setInputValue("3.14");

      chkComplete = new JCheckBox("Complete");
      rdoLow = new JRadioButton("Small");
      rdoMedium = new JRadioButton("Medium");
      rdoHigh = new JRadioButton("Large");
      grpPriority = new ButtonGroup();
      grpPriority.add(rdoLow);
      grpPriority.add(rdoMedium);
      grpPriority.add(rdoHigh);
      cboType = new JComboBox<Categories>(Categories.values());


      btnSelect = new JButton("Select");
      btnSelect.addActionListener(this);
      btnSelect.setActionCommand("select");


      btnDelete = new JButton("Delete");
      btnDelete.addActionListener(this);
      btnDelete.setActionCommand("delete");



      btnEdit = new JButton("Edit");
      btnEdit.addActionListener(this);
      btnEdit.setActionCommand("submit");


      fillProductComboBox();


      Box boxVertical = Box.createVerticalBox();
      Box boxHorizontal = Box.createHorizontalBox();

      boxVertical.add(cboTasks);

      Box boxHorizButtons = Box.createHorizontalBox();
      /*boxHorizButtons.add(btnSelect);
      boxHorizButtons.add(btnDelete);
      boxVertical.add(boxHorizButtons);*/
      boxVertical.add(btnSelect);
      boxVertical.add(datName);
      boxVertical.add(datDescription);
      //boxVertical.add(datPrice);
      boxVertical.add(chkComplete);
      boxHorizontal.add(rdoLow);
      boxHorizontal.add(rdoMedium);
      boxHorizontal.add(rdoHigh);
      boxVertical.add(boxHorizontal);
      boxVertical.add(cboType);

      boxVertical.add(datDateDue);

      boxHorizButtons.add(btnEdit);
      boxHorizButtons.add(btnDelete);
      boxVertical.add(boxHorizButtons);
      add(boxVertical);
   }
}
