package ticket;

import ticket.enums.TipoCola;

import java.util.ArrayList;

public abstract class ColaServicio {

    protected ArrayList<Ticket> cola;
    private TipoCola tipo;

    public ColaServicio(){
        this.cola = new ArrayList<>();
    }

    public void agregar(Ticket ticket) {
        this.cola.add(ticket);
    }

    public abstract Ticket quitar();

    public ArrayList<Ticket> getCola() {
        return cola;
    }

    public void setCola(ArrayList<Ticket> cola) {
        this.cola = cola;
    }

    public TipoCola getTipo() {
        return tipo;
    }

    public void setTipo(TipoCola tipo) {
        this.tipo = tipo;
    }
}
