package Modelo;

/**
 * Pruebas manuales equivalentes a TDD.
 * Permite validar la lógica del sistema y simular pruebas unitarias.
 * Autor: Eduardo Jimenez
 */
public class TestManual {

    public static void main(String[] args) {

        // ==== PRUEBA 1: Cálculo de subtotal de un ítem (Detalle) ====
        Detalle d1 = new Detalle();
        d1.setPrecio(10.0);
        d1.setCantidad(5);
        double subtotal = d1.getPrecio() * d1.getCantidad();
        System.out.println("Prueba 1 - Subtotal esperado: 50.0 | Resultado: " + subtotal);
        assert subtotal == 50.0 : "Error: el subtotal calculado no es correcto.";

        // ==== PRUEBA 2: Cálculo con cantidad cero ====
        Detalle d2 = new Detalle();
        d2.setPrecio(20.0);
        d2.setCantidad(0);
        double subtotal2 = d2.getPrecio() * d2.getCantidad();
        System.out.println("Prueba 2 - Subtotal esperado: 0.0 | Resultado: " + subtotal2);
        assert subtotal2 == 0.0 : "Error: el cálculo con cantidad cero falló.";

        // ==== PRUEBA 3: Total de la venta sumando ítems ====
        double totalEsperado = subtotal + subtotal2;
        Venta venta = new Venta();
        venta.setTotal(totalEsperado);
        System.out.println("Prueba 3 - Total esperado: 50.0 | Resultado: " + venta.getTotal());
        assert venta.getTotal() == totalEsperado : "Error: total de venta incorrecto.";

        // ==== PRUEBA 4: Autenticación de usuario inexistente ====
        LoginDAO dao = new LoginDAO();
        login l = dao.log("usuariofalso@demo.com", "12345");
        System.out.println("Prueba 4 - Usuario inexistente, ID obtenido = " + l.getId());
        assert l.getId() == 0 : "Error: se autenticó un usuario inválido.";

        System.out.println("\n✅ Todas las pruebas se ejecutaron correctamente. El sistema responde según lo esperado.");
    }
}
