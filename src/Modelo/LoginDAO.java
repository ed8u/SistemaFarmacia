package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {
    // Proveedor de conexiones
    Conexion cn = new Conexion();

    /** Autentica a un usuario por correo y contraseña (hash SHA-256). */
    public login log(String correo, String pass) {
        login l = new login(); // objeto por defecto (id=0)
        final String sql = "SELECT id, nombre, correo, rol FROM usuarios WHERE correo = ? AND pass = ?";

        final String correoNorm = (correo == null) ? null : correo.trim().toLowerCase();
        final String passHash   = HashUtil.sha256(pass);

        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correoNorm);
            ps.setString(2, passHash);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    l.setId(rs.getInt("id"));
                    l.setNombre(rs.getString("nombre"));
                    l.setCorreo(rs.getString("correo"));
                    // Por seguridad NO seteamos l.setPass(...)
                    l.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }

    /** Registra un nuevo usuario (almacenando hash de contraseña). */
    public boolean Registrar(login reg) {
        final String sql = "INSERT INTO usuarios (nombre, correo, pass, rol) VALUES (?,?,?,?)";
        final String correoNorm = reg.getCorreo().trim().toLowerCase();
        final String passHash   = HashUtil.sha256(reg.getPass());

        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reg.getNombre());
            ps.setString(2, correoNorm);
            ps.setString(3, passHash);
            ps.setString(4, reg.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /** Lista todos los usuarios (sin exponer contraseñas). */
    public List<login> ListarUsuarios() {
        List<login> lista = new ArrayList<>();
        final String sql = "SELECT id, nombre, correo, rol FROM usuarios";

        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                login lg = new login();
                lg.setId(rs.getInt("id"));
                lg.setNombre(rs.getString("nombre"));
                lg.setCorreo(rs.getString("correo"));
                lg.setRol(rs.getString("rol"));
                lista.add(lg);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
    }
}

// NOTE: Se usa SHA-256 + correo normalizado (trim/lowercase). Evitar exponer pass en memoria.
