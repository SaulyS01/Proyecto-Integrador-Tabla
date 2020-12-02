
package proyectointegrador.gui.complements;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import proyectointegrador.data.ProductoData;
import proyectointegrador.entities.Producto;

public class modelTableProducto extends AbstractTableModel {

    private List<Producto> lis = new ArrayList();
    private String[] columns = {"id", "nombres", "Apellidos", "edad"};
    private Class[] columnsType = {Integer.class, String.class, String.class, Integer.class};

    public modelTableProducto() {
        lis = ProductoData.list("");
    }

    public modelTableProducto(String filter) {

        lis = ProductoData.list(filter);
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        Producto p = (Producto) lis.get(row);
        switch (column) {
            case 0:
                return row + 1;
            case 1:
                return p.getNombre();
            case 2:
                return p.getApellido();
            case 3:
                return p.getEdad();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnsType[column];
    }

    @Override
    public int getRowCount() {
        return lis.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
}
