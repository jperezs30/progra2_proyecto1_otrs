package ticket;

import ticket.enums.TipoEvento;
import java.time.LocalDateTime;

public class Bitacora {

    private String nitSoporte;
    private LocalDateTime fechaHora;
    private String mensaje;
    private TipoEvento evento;

    public Bitacora(String nitSoporte, String mensaje, TipoEvento evento) {
        this.nitSoporte = nitSoporte;
        this.mensaje = mensaje;
        this.evento = evento;
        this.fechaHora = LocalDateTime.now();
    }

    public String getNitSoporte() {
        return nitSoporte;
    }

    public void setNitSoporte(String nitSoporte) {
        this.nitSoporte = nitSoporte;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TipoEvento getEvento() {
        return evento;
    }

    public void setEvento(TipoEvento evento) {
        this.evento = evento;
    }
}
