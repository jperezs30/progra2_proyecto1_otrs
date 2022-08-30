package ticket;

import java.util.ArrayList;
import java.util.Scanner;
import ticket.enums.TipoEvento;

public class Ticket {

    private String nitUsuario;
    private int id;
    private String problema;
    private String estado;
    private ArrayList<Bitacora> bitacora;

    private static int correlativo = 0;

    public Ticket() {
        this.bitacora = new ArrayList<>();
    }

    public Ticket(String nitUsuario, String problema, String estado) {
        this.correlativo++;
        this.bitacora = new ArrayList<>();
        this.id = this.correlativo;
        this.nitUsuario = nitUsuario;
        this.problema = problema;
        this.estado = estado;
        this.bitacora.add(new Bitacora(this.nitUsuario,this.problema, TipoEvento.CREAR));
    }

    public void crear() {
        this.correlativo ++;
        System.out.println("-------- Crear ticket ---------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el NIT");
        this.nitUsuario = scanner.nextLine();
        System.out.println("Ingrese descripción del problema");
        this.problema = scanner.nextLine();
        this.estado = "CREADO";
        this.id = this.correlativo;
        this.bitacora.add(new Bitacora(this.nitUsuario,this.problema, TipoEvento.CREAR));
        System.out.println("Se creó el ticket correctamente.");
    }

    public void agregarBitacora(Bitacora bitacora) {
        this.bitacora.add(bitacora);
    }

    @Override
    public String toString() {
        return String.format("%05d", this.id);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Bitacora> getBitacora() {
        return bitacora;
    }

    public void setBitacora(ArrayList<Bitacora> bitacora) {
        this.bitacora = bitacora;
    }

    public String getNitUsuario() {
        return nitUsuario;
    }

    public void setNitUsuario(String nitUsuario) {
        this.nitUsuario = nitUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }
}
