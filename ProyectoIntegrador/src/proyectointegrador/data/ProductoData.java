
package proyectointegrador.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import proyectointegrador.entities.Producto;

public class ProductoData {
    
    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    //static ErrorLogger log = new ErrorLogger(ClienteData.class.getName());

    public static int create(Producto p) {
        int rsId = 0;
        String[] returns = {"id_producto"};
        String sql = "INSERT INTO producto(nombre, apellido, edad) "
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setString(++i, p.getNombre());
            ps.setString(++i, p.getApellido());
            ps.setInt(++i, p.getEdad());
            rsId = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
        }
        return rsId;
    }

    public static int update(Producto p) {
        int comit = 0;
        String sql = "UPDATE producto SET "
                + "nombre=?, "
                + "apellido=? "
                + "WHERE id_producto=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, p.getNombre());
            ps.setString(++i, p.getApellido());
            ps.setInt(++i, p.getEdad());
            ps.setInt(++i, p.getId_producto());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Producto> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        
        List<Producto> ls = new ArrayList();

        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM producto ORDER BY id_producto";
        } else {
            sql = "SELECT * FROM producto WHERE (id_producto LIKE'" + filter + "%' OR "
                    + "nombre LIKE'" + filter + "%' OR apellido LIKE'" + filter + "%' OR "
                    + "edad LIKE'" + filter + "%') "
                    + "ORDER BY nombre";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Producto p = new Producto();
                p.setId_producto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setEdad(rs.getInt("edad"));
                ls.add(p);
            }
        } catch (SQLException ex) {
        }
        return ls;
    }

    public static Producto getByPId(int id) {
        Producto p = new Producto();
        String sql = "SELECT * FROM producto WHERE id_producto = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setId_producto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setEdad(rs.getInt("edad"));

            }
        } catch (SQLException ex) {
        }
        return p;
    }
}
