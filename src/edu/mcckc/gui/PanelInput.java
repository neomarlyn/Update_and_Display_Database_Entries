package edu.mcckc.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.mcckc.dao.TaskDao;
import edu.mcckc.domain.*;
import org.apache.logging.log4j.*;


public class PanelInput
    extends JPanel
    implements ActionListener
{
    private static Logger logger = LogManager.getLogger(PanelInput.class);


    private PanelEdit pnlEdit;
    public void setEditPanelReference(PanelEdit pnlEdit)
    {
        this.pnlEdit = pnlEdit;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        logger.debug("INSIDE   PanelInput   actionPerformed !!!");

        Task temp = new Task();
        TaskDao dao = new TaskDao("sqliteconfig.properties");

        try
        {
            String sName = datTask.getStringValue();
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

            Categories type = (Categories)cboType.getSelectedItem();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String sDatePurchased = datDateDue.getStringValue();
            Date dtDue = df.parse(sDatePurchased);


            logger.debug(String.format("NAME: %s", sName));
            logger.debug(String.format("DESCRIPTION: %s", sDescription));
            logger.debug(String.format("PRIORITY: %d", iPriority));
            logger.debug(String.format("COMPLETE: %b", bComplete));
            logger.debug(String.format("TYPE: %d", type.ordinal()));
            logger.debug(String.format("DATEDUE: %s", dtDue.toString()));


            temp.id = -1;
            temp.name = sName;
            temp.type = type.ordinal();
            temp.description = sDescription;
            temp.priority = iPriority;
            temp.complete = bComplete;
            temp.dateDue = dtDue;

            dao.createSingleObject(temp);

            pnlEdit.fillProductComboBox();
        }
        catch(Exception ex)
        {
            logger.error(ex.toString());
        }
    }

    ////////public enum ProductColors { Red, Blue, Green, Yellow };

    private JDataInput datTask;
    private JDataInput datDescription;
    private JDataInput datDateDue;
    //private JDataInput datPrice;
    private JCheckBox chkComplete;

    private JRadioButton rdoLow;
    private JRadioButton rdoMedium;
    private JRadioButton rdoHigh;
    private ButtonGroup grpPriority;

    private JComboBox cboType;
    private JButton btnSubmit;

    public PanelInput()
    {
        datTask = new JDataInput("Name:", false);
        datDescription = new JDataInput("Description:", true);
        //datPrice = new JDataInput("Price:", false);
        datDateDue = new JDataInput("Date Due:", false);
        chkComplete = new JCheckBox("Complete");
        rdoLow = new JRadioButton("Low");
        rdoMedium = new JRadioButton("Medium");
        rdoHigh = new JRadioButton("High");
        grpPriority = new ButtonGroup();
        grpPriority.add(rdoLow);
        grpPriority.add(rdoMedium);
        grpPriority.add(rdoHigh);
        cboType = new JComboBox<Categories>(Categories.values());

        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(this);
        btnSubmit.setActionCommand("submit");


        Box boxVertical = Box.createVerticalBox();
        Box boxHorizontal = Box.createHorizontalBox();

        boxVertical.add(datTask);
        boxVertical.add(datDescription);
       // boxVertical.add(datPrice);
        boxVertical.add(chkComplete);
        boxHorizontal.add(rdoLow);
        boxHorizontal.add(rdoMedium);
        boxHorizontal.add(rdoHigh);
        boxVertical.add(boxHorizontal);
        boxVertical.add(cboType);
        boxVertical.add(datDateDue);
        boxVertical.add(btnSubmit);

        add(boxVertical);
    }


}
