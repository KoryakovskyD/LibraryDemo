package ru.avalon.javapp.devj120;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BooksTableModel implements TableModel {
    private static final String[] COLUMN_HEADERS = {
            "Book code",
            "ISBN",
            "Name",
            "Author(s)",
            "Publish year"
    };

    private static final Class<?>[] COLUMN_TYPES = {
            String.class,
            String.class,
            String.class,
            String.class,
            Integer.class,
    };

    private final BooksList booksList;
    private final List<TableModelListener> listeners = new ArrayList<>();

    public BooksTableModel() throws IOException, ClassNotFoundException {
        this.booksList = new BooksList();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_HEADERS.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override
    public int getRowCount() {
        return booksList.getCount();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book b = booksList.get(rowIndex);
        switch (columnIndex) {
            case 0: return b.getBookCode();
            case 1: return b.getIsbn();
            case 2: return b.getName();
            case 3: return b.getAuthors();
            case 4: return b.getPublishYear();
            default:
                throw new IllegalArgumentException("Unknown column index.");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // редактировать внутри запрещено
    }

    public Book getBook(int rowIndex) {
        return booksList.get(rowIndex);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    private void fireTableModeEvent(TableModelEvent tme) {
        for (TableModelListener l : listeners) {
            l.tableChanged(tme);
        }
    }

    public void addBook(Book b) {
        int newRowNdx = booksList.getCount();
        booksList.add(b);
        TableModelEvent tme = new TableModelEvent(this, newRowNdx, newRowNdx,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        fireTableModeEvent(tme);
    }

    public void bookChanged(int rowNdx) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
        fireTableModeEvent(tme);
    }

    public void deleteBook(int bookIndex) {
        booksList.remove(bookIndex);
            TableModelEvent tme = new TableModelEvent(this, bookIndex, bookIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
            fireTableModeEvent(tme);
    }

    public void save() throws  IOException {
        booksList.save();
    }
}
