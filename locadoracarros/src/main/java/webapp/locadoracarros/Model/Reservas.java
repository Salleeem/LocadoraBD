package webapp.locadoracarros.Model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservas implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReserva;

    private String modeloCarro;
    @ManyToOne // Indica que esta é uma relação muitos-para-um
    private Clientes cliente; // Adiciona um campo para referenciar o Cliente

    private String localRetirada;
    private Date dataRetirada;
    private Date dataDevolu;

    public long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(long idReserva) {
        this.idReserva = idReserva;
    }


    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }


    public String getLocalRetirada() {
        return localRetirada;
    }

    public void setLocalRetirada(String localRetirada) {
        this.localRetirada = localRetirada;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Date getDataDevolu() {
        return dataDevolu;
    }

    public void setDataDevolu(Date dataDevolu) {
        this.dataDevolu = dataDevolu;
    }
}