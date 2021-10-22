package com.khalid.AgeCalculator;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.Period;

public class Main extends JFrame {

    JLabel birthDate, result;
    JComboBox dayOfBirth, monthOfBirth, yearOfBirth;
    JButton calculateAge;

    int d1, d2, m1, m2, y1, y2;

    LocalDate startDate, endDate;
    long daysCounter, monthsCounter, yearsCounter;

    Font font = new Font("Arial", Font.BOLD, 18);

    public Main(){
        createAndShowGui();
    }

    private void createAndShowGui(){
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if ("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }

            }
        }
        catch (Exception ex){ }

        String[] months = {"Jan -1", "Feb -2", "Mar -3", "Apr -4", "May -5", "Jun -6",
                "Jul -7", "Aug -8", "Sep -9", "Oct -10", "Nov -11", "Dec -12"};

        Integer[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};


        int currentYear = LocalDate.now().getYear();

        Integer[] years = new Integer[currentYear];

        for (int i = 0; i < years.length; i++)
            years[i] = i+1;


        birthDate = new JLabel("Birth Date");
        dayOfBirth = new JComboBox(days);
        monthOfBirth = new JComboBox(months);
        yearOfBirth = new JComboBox(years);
        calculateAge = new JButton("Calculate Age");
        result = new JLabel("", JLabel.CENTER);

        birthDate.setBounds(175, 10, 200, 30);
        dayOfBirth.setBounds(120, 50, 50, 30);
        monthOfBirth.setBounds(173, 50, 70, 30);
        yearOfBirth.setBounds(246, 50, 70, 30);
        calculateAge.setBounds(118, 100, 200, 60);
        result.setBounds(0, 170, 440, 30);

        birthDate.setFont(font);
        calculateAge.setFont(font);
        result.setFont(new Font("Arial", Font.BOLD, 17));

        yearOfBirth.setSelectedItem(2000);

        add(birthDate);
        add(dayOfBirth);
        add(monthOfBirth);
        add(yearOfBirth);
        add(calculateAge);
        add(result);

        monthOfBirth.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String month = monthOfBirth.getSelectedItem().toString();

                if( month.equals("Jan") || month.equals("Mar") || month.equals("May")
                        || month.equals("Jul") || month.equals("Aug") || month.equals("Oct") || month.equals("Dec") )
                    for (int i = 1; i <= 31; i++)
                        dayOfBirth.addItem(i);
                else if (month.equals("feb"))
                    for (int i = 1; i <= 28; i++)
                        dayOfBirth.addItem(i);
                else
                    for (int i = 1 ; i<= 30; i++)
                        dayOfBirth.addItem(i);
            }
        });

        calculateAge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = "";

                y1 = yearOfBirth.getSelectedIndex() + 1;
                m1 = monthOfBirth.getSelectedIndex() + 1;
                d1 = dayOfBirth.getSelectedIndex() + 1;

                y2 = LocalDate.now().getYear();
                m2 = LocalDate.now().getMonthValue();
                d2 = LocalDate.now().getDayOfMonth();

                startDate = LocalDate.of(y1, m1, d1);
                endDate = LocalDate.of(y2, m2, d2);

                yearsCounter = Period.between(startDate, endDate).getYears();
                monthsCounter = Period.between(startDate, endDate).getMonths();
                daysCounter = Period.between(startDate, endDate).getDays();


                if (yearsCounter == 0 && monthsCounter == 0 && daysCounter == 0) {
                    result.setForeground(Color.red);
                    result.setText("Cannot compare same date!");
                }

                else if (!Period.between(startDate, endDate).isNegative())
                {

                    if (yearsCounter == 1)
                        text += yearsCounter + " year ";

                    else if (yearsCounter > 1)
                        text += yearsCounter + " years ";

                    if (monthsCounter == 1)
                    {
                        if (yearsCounter > 0 && daysCounter > 0)
                            text += ", " + monthsCounter + " month ";

                        else if (yearsCounter > 0 && daysCounter == 0)
                            text += "and " + monthsCounter + " month ";

                        else
                            text += monthsCounter + " month ";
                    }

                    if (monthsCounter > 1)
                    {
                        if (yearsCounter > 0 && daysCounter > 0)
                            text += ", " + monthsCounter + " months ";

                        else if (yearsCounter > 0 && daysCounter == 0)
                            text += "and " + monthsCounter + " months ";

                        else
                            text += monthsCounter + " months ";
                    }

                    if (daysCounter == 1)
                    {
                        if (yearsCounter == 0 && monthsCounter == 0)
                            text += daysCounter + " day";

                        else
                            text += "and " + daysCounter + " day";
                    }

                    if (daysCounter > 1)
                    {
                        if (yearsCounter == 0 && monthsCounter == 0)
                            text += daysCounter + " days";

                        else
                            text += "and " + daysCounter + " days";
                    }

                    result.setForeground(Color.black);
                    result.setText(text);
                }
                else
                {
                    result.setForeground(Color.red);
                    result.setText("Logic order of Dates is wrong!");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(436, 250);
        setTitle("Age Calculator");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });

    }
}
