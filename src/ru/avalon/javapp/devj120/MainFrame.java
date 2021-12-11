package ru.avalon.javapp.devj120;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrame extends JFrame {
    private final JTable booksTable;
    private final BooksTableModel booksModel;

    public MainFrame() {
        super("Library books");

        setBounds(300,200,500,400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            // нажатие на крестик
            @Override
            public void windowClosing(WindowEvent e) {
                saveAndExit();
            }

            // метод вызывается после того, как окно закрыто
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });

        intMenuBar();

        BooksTableModel btm = null;
        try {
            btm = new BooksTableModel();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        booksModel = btm;
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
        menuItem.addActionListener(e -> deleteBook());
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

    private void deleteBook() {
        int rowNdx = booksTable.getSelectedRow();
        if (rowNdx == -1)
            return;

        Book book = booksModel.getBook(rowNdx);
        if (JOptionPane.showConfirmDialog(this, "Are you sure want to delete book with code" +
                book.getBookCode() + "?", "Delete confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION) {
            booksModel.deleteBook(rowNdx);
        }
    }


    private void saveAndExit() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure want to exit?", "Exit confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                == JOptionPane.YES_OPTION) {
            try {
                booksModel.save();
            } catch (IOException e) {
                if (JOptionPane.showConfirmDialog(this, "Error saving books list to a file "  +
                        e.getMessage() + ".\n" + "Are you still sure you want to exit?","Exit conformation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.NO_OPTION)
                    return;
            }
            dispose();
        }
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
                book.setIsbn(dlg.getIsbnText());
                book.setName(dlg.getName());
                book.setAuthors(dlg.getAuthorText());
                book.setPublishYear(Integer.parseInt(dlg.getPublishYearText()));
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
