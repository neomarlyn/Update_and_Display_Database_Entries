package edu.mcckc.domain;

import java.util.Date;

public class Task
{
    /*
product
--------------------
id     int
name   string
price  double
complete boolean
priority   int/choice  radio button (large / med / sml)
type  int/choice  combobox (red/green/blue/yellow)
    */

    /*     PRIMITIVE DATA TYPES CANNOT BE SET TO NULL (DARNIT)
    public int id;
    public String name;
    public String description;
    public double price;
    public boolean complete;
    public int priority;
    public int type;
    public Date dateDue;
    */

    public Integer id;
    public String name;
    public String description;
    public Boolean complete;
    public Integer priority;
    public Integer type;
    public Date dateDue;


    @Override
    public String toString()
    {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    public String toStringFull()
    {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", complete=" + complete +
                ", priority=" + priority +
                ", type=" + type +
                ", dateDue=" + dateDue +
                '}';
    }
}

