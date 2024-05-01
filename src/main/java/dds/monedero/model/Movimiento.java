package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private double monto;
  private boolean esDeposito;

  public Movimiento(LocalDate fecha, double monto, boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.esDeposito = esDeposito;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean esDeposito() {
    return esDeposito;
  }


//  en el sistema actualmente solo se realizan sumas y restas, pero si el sistema creciera
//  y se agregan nuevos movimientos o se complejizan los procesos quiza seria mejor separar
//  los depositos y extracciones en clases distintas
  public double calcularValor() {
    if (esDeposito) {
      return + getMonto();
    } else {
      return - getMonto();
    }
  }
}
