package entidad;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "CLIENTELUI")

public class Clilui {

    @Id
    @Column(name = "CLIID")
    private Integer codigo;
    @Column(name = "CLINOM")
    private String nombre;
    @Column(name = "CLIAPE")
    private String apellido;
    @Column(name = "CLISAL")
    private BigDecimal saldo;
    @Column(name = "CLICUE")
    private String tipo;

    public Clilui() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
