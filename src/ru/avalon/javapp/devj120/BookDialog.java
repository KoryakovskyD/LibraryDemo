package ru.avalon.javapp.devj120;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BookDialog extends JDialog {
    private final JTextField bookCodeField = new JTextField(10);
    private final JTextField isbnField = new JTextField(10);
    private final JTextField nameField = new JTextField(50);
    private final JTextField authorField = new JTextField(50);
    private final JTextField publishYearField = new JTextField(5);

    private boolean okPressed;

    public BookDialog(JFrame owner) {
        super(owner,"New book registration", true);
        // модальность диалогового окна - это возможность работать только с диалоговым окном
        initDialog();
    }

    public BookDialog(JFrame owner, Book book) {
        super(owner, "Book editing", true);
        initDialog();
        bookCodeField.setText(book.getBookCode());
        isbnField.setText(book.getIsbn());
        nameField.setText(book.getName());
        authorField.setText(book.getAuthors());
        publishYearField.setText(Integer.toString(book.getPublishYear()));
        bookCodeField.setEnabled(false);
    }

    private void initDialog() {
        intLayout();
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                pack();
                setLocationRelativeTo(getOwner());
                bookCodeField.requestFocus();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                okPressed = false;
            }
        });
    }

    private void intLayout() {
        layoutControls();
        initButtons();
    }

    private void layoutControls() {
        JPanel p = new JPanel(null);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        addControlRow(bookCodeField, "Book code", p);
        addControlRow(isbnField, "ISBN", p);
        addControlRow(nameField, "Name",p);
        addControlRow(authorField, "Author(s)",p);
        addControlRow(publishYearField, "Publish year", p);

        add(p, BorderLayout.CENTER);
    }

    private void addControlRow(JTextField field, String label, JPanel parent) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(label);
        lbl.setLabelFor(field);
        p.add(lbl);
        p.add(field);
        parent.add(p);
    }

    private void initButtons() {
        JPanel p = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });
        p.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));
        p.add(cancelButton);

        add(p, BorderLayout.SOUTH);
    }

    public boolean isOkPressed() {
        return okPressed;
    }

    public String getBookCodeText() {
        return bookCodeField.getText();
    }

    public String getIsbnText() {
        return isbnField.getText();
    }

    public String getNameText() {
        return nameField.getText();
    }

    public String getAuthorText() {
        return authorField.getText();
    }

    public String getPublishYearText() {
        return publishYearField.getText();
    }
}
