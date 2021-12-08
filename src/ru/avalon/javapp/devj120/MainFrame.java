package ru.avalon.javapp.devj120;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
    private final JTable booksTable;
    private final BooksTableModel booksModel;

    public MainFrame() {
        super("Library books");

        setBounds(300,200,500,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        intMenuBar();

        booksModel = new BooksTableModel();
        booksTable = new JTable(booksModel);
        initLayout();
    }

    private void initLayout() {
        add(booksTable, BorderLayout.CENTER);
        add(booksTable.getTableHeader(), BorderLayout.NORTH);
    }

    private void intMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // создание меню с 3-мя вариантами
        JMenu menu = new JMenu("Operations");
        menu.setMnemonic(KeyEvent.VK_O);

        JMenuItem menuItem = new JMenuItem("Add...");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(e -> addBook());
        menu.add(menuItem);

        menuItem = new JMenuItem("Change...");
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
        menuItem.addActionListener(e -> changeBook());
        menu.add(menuItem);

        menuItem = new JMenuItem("Delete...");
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_DOWN_MASK));
        menu.add(menuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // при нажатии alt-o будет открываться наше меню
        // menu.setMnemonic(KeyEvent.VK_O);
        // выделим буквы, чтобы быстро переходить по ним к определенному полю
        // menuItem.setMnemonic(KeyEvent.VK_D);
        // заход в меню по нажатию комбинации клавиш
        // menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));

    }

    private void addBook() {
        BookDialog dlg = new BookDialog(this);
        while (true) {
            dlg.setVisible(true);
            if (!dlg.isOkPressed())
                return;
            try {
                Book b = new Book(
                        dlg.getBookCodeText(),
                        dlg.getIsbnText(),
                        dlg.getNameText(),
                        dlg.getAuthorText(),
                        Integer.parseInt(dlg.getPublishYearText())
                );
                booksModel.addBook(b);
                return;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeBook() {
        int rowNdx = booksTable.getSelectedRow();
        if (rowNdx == -1)
            return;

        Book book = booksModel.getBook(rowNdx);
        BookDialog dlg = new BookDialog(this, book);
        while (true) {
            dlg.setVisible(true);
            if (!dlg.isOkPressed())
                return;
            try {

                booksModel.bookChanged(rowNdx);
                return;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }

}
