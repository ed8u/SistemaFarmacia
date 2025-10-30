package Modelo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.filechooser.FileSystemView;

/**
 * DAO de ventas: operaciones de lectura/escritura en BD y generación de PDF con iText.
 *
 * <p>Contiene métodos para obtener el último ID de venta, registrar ventas y detalles,
 * actualizar stock, listar ventas, buscar una venta específica y generar un comprobante
 * PDF con los datos de la venta y del cliente.</p>
 *
 * <p><b>Notas (solo comentarios, sin cambiar código):</b></p>
 * <ul>
 *   <li>Se utilizan {@code PreparedStatement} para evitar SQL Injection.</li>
 *   <li>Falta cierre sistemático de recursos ({@code rs}, {@code ps}, {@code con}) en varios métodos. <!-- TODO --></li>
 *   <li>Los métodos {@code RegistrarVenta} y {@code RegistrarDetalle} retornan el campo {@code r} pero nunca lo asignan. <!-- TODO --></li>
 *   <li>En {@code pdfV(...)} la ruta del archivo usa concatenación sin separador; podría requerir {@code File.separator}. <!-- TODO --></li>
 *   <li>El campo fecha se maneja como {@code String}; evaluar tipos de fecha/tiempo según necesidades. <!-- TODO --></li>
 * </ul>
 *
 * @author Eduardo Jimenez
 */
public class VentaDao {
    Connection con;          // Conexión activa a BD
    Conexion cn = new Conexion(); // Proveedor de conexiones
    PreparedStatement ps;    // Sentencia preparada
    ResultSet rs;            // Resultado de consultas
    int r;                   // Variable de retorno genérica (no se asigna en algunos métodos)
    
    /**
     * Obtiene el ID máximo (última venta registrada) desde la tabla {@code ventas}.
     *
     * @return el valor de MAX(id) o 0 si no hay registros o ocurre un error
     */
    public int IdVenta(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1); // primera columna del SELECT
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar rs, ps y con (try-with-resources) para liberar recursos.
        return id;
    }
    
    /**
     * Inserta una venta en la tabla {@code ventas}.
     *
     * @param v objeto Venta con cliente, vendedor, total y fecha
     * @return el valor de {@code r} (actualmente no se asigna dentro del método)
     */
    public int RegistrarVenta(Venta v){
        String sql = "INSERT INTO ventas (cliente, vendedor, total, fecha) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            ps.execute(); // Ejecuta INSERT
            // TODO: asignar 'r' con filas afectadas (ps.getUpdateCount()) o con el id generado si se requiere.
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close(); // TODO: cerrar también ps
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    /**
     * Inserta un detalle de venta en la tabla {@code detalle}.
     *
     * @param Dv objeto Detalle con id_pro, cantidad, precio e id de venta
     * @return el valor de {@code r} (actualmente no se asigna dentro del método)
     */
    public int RegistrarDetalle(Detalle Dv){
       String sql = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId()); // Nota: aquí se usa Dv.getId() en lugar de getId_venta(). <!-- TODO: confirmar intención -->
            ps.execute();
            // TODO: asignar 'r' con filas afectadas si se necesita un retorno útil.
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close(); // TODO: cerrar también ps
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    /**
     * Actualiza el stock de un producto.
     *
     * @param cant nuevo stock a establecer
     * @param id   identificador del producto
     * @return true si se ejecutó el UPDATE; false si ocurrió un error
     */
    public boolean ActualizarStock(int cant, int id){
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        // TODO: cerrar ps y con.
    }
    
    /**
     * Lista las ventas con datos del cliente (JOIN contra {@code clientes}).
     *
     * @return lista de ventas; si falla, devuelve una lista (posiblemente vacía)
     */
    public List Listarventas(){
       List<Venta> ListaVenta = new ArrayList(); // TODO: tipar también la firma como List<Venta>
       String sql = "SELECT c.id AS id_cli, c.nombre, v.* FROM clientes c INNER JOIN ventas v ON c.id = v.cliente";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {
               // Mapeo de fila a objeto Venta (campos seleccionados)
               Venta vent = new Venta();
               vent.setId(rs.getInt("id"));
               vent.setNombre_cli(rs.getString("nombre"));
               vent.setVendedor(rs.getString("vendedor"));
               vent.setTotal(rs.getDouble("total"));
               ListaVenta.add(vent);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       // TODO: cerrar rs, ps y con.
       return ListaVenta;
   }

    /**
     * Busca una venta por su ID.
     *
     * @param id identificador de la venta
     * @return objeto Venta con los datos si existe; caso contrario, objeto por defecto
     */
    public Venta BuscarVenta(int id){
        Venta cl = new Venta();
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setCliente(rs.getInt("cliente"));
                cl.setTotal(rs.getDouble("total"));
                cl.setVendedor(rs.getString("vendedor"));
                cl.setFecha(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar rs, ps y con.
        return cl;
    }

    /**
     * Genera un comprobante PDF de la venta y lo abre con el visor predeterminado.
     *
     * @param idventa id de la venta
     * @param Cliente id del cliente (para extraer sus datos)
     * @param total   monto total de la venta
     * @param usuario nombre del vendedor/usuario que emite
     */
    public void pdfV(int idventa, int Cliente, double total, String usuario) {
        try {
            Date date = new Date();
            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + "venta.pdf"); // TODO: considerar url + File.separator + "venta.pdf"
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance(getClass().getResource("/Img/logo_pdf.jpg")); // Requiere que el recurso exista en el classpath

            // Sección: Fecha y cabecera
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.GREEN);
            fecha.add(Chunk.NEWLINE);
            fecha.add("Vendedor: " + usuario + "\nFolio: " + idventa + "\nFecha: "
                    + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
            Encabezado.addCell(img);
            Encabezado.addCell("");

            // Info empresa (tabla config)
            String config = "SELECT * FROM config";
            String mensaje = "";
            try {
                con = cn.getConnection();
                ps = con.prepareStatement(config);
                rs = ps.executeQuery();
                if (rs.next()) {
                    mensaje = rs.getString("mensaje");
                    Encabezado.addCell("Ruc:    " + rs.getString("ruc") + "\nNombre: " + rs.getString("nombre") + "\nTeléfono: " + rs.getString("telefono") + "\nDirección: " + rs.getString("direccion") + "\n\n");
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            // Sección: Datos del cliente
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS DEL CLIENTE" + "\n\n");
            doc.add(cli);

            PdfPTable proveedor = new PdfPTable(3);
            proveedor.setWidthPercentage(100);
            proveedor.getDefaultCell().setBorder(0);
            float[] columnWidthsCliente = new float[]{50f, 25f, 25f};
            proveedor.setWidths(columnWidthsCliente);
            proveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliTel = new PdfPCell(new Phrase("Télefono", negrita));
            PdfPCell cliDir = new PdfPCell(new Phrase("Dirección", negrita));
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliTel.setBorder(Rectangle.NO_BORDER);
            cliDir.setBorder(Rectangle.NO_BORDER);
            proveedor.addCell(cliNom);
            proveedor.addCell(cliTel);
            proveedor.addCell(cliDir);

            String prove = "SELECT * FROM clientes WHERE id = ?";
            try {
                ps = con.prepareStatement(prove);
                ps.setInt(1, Cliente);
                rs = ps.executeQuery();
                if (rs.next()) {
                    proveedor.addCell(rs.getString("nombre"));
                    proveedor.addCell(rs.getString("telefono"));
                    proveedor.addCell(rs.getString("direccion") + "\n\n");
                } else {
                    proveedor.addCell("Publico en General");
                    proveedor.addCell("S/N");
                    proveedor.addCell("S/N" + "\n\n");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(proveedor);

            // Sección: Detalle de productos
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 50f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Descripción.", negrita));
            PdfPCell c3 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. Total", negrita));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);

            String product = "SELECT d.id, d.id_pro,d.id_venta, d.precio, d.cantidad, p.id, p.nombre FROM detalle d INNER JOIN productos p ON d.id_pro = p.id WHERE d.id_venta = ?";
            try {
                ps = con.prepareStatement(product);
                ps.setInt(1, idventa);
                rs = ps.executeQuery();
                while (rs.next()) {
                    double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                    tabla.addCell(rs.getString("cantidad"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("precio"));
                    tabla.addCell(String.valueOf(subTotal));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(tabla);

            // Total
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total S/: " + total); // TODO: formatear moneda con NumberFormat según locale.
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            // Firma
            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion \n\n");
            firma.add("------------------------------------\n");
            firma.add("Firma \n");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            // Mensaje final desde config
            Paragraph gr = new Paragraph();
            gr.add(Chunk.NEWLINE);
            gr.add(mensaje);
            gr.setAlignment(Element.ALIGN_CENTER);
            doc.add(gr);

            // Cierre y apertura del PDF
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida); // Puede fallar en entornos sin GUI. <!-- TODO -->
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
        // TODO: cerrar cualquier recurso de BD aún abierto (rs/ps/con) en esta rutina.
    }

    
}
