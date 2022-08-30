package ticket;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import ticket.enums.TipoEvento;
import ticket.enums.TipoUsuario;
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

/**
 *Clase principal del proyecto de tickets.
 */
public class Principal {

    private ColaMesa colaMesa = new ColaMesa();
    private ColaSoporte colaSoporte = new ColaSoporte();
    private ColaDesarrollo colaDesarrollo = new ColaDesarrollo();
    private ColaAtendidos colaAtendidos = new ColaAtendidos();
    private String nitUsuario = "";
    private String rolUsuario = "";

    public static void main(String[] params) {
        Principal principal = new Principal();
        principal.mostrarMenuPrincipal();
    }

    /**
     * Muestra el menú principal del proyecto.
     */

    void mostrarMenuPrincipal() {
        int opcion = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("----------  Menu principal ---------");
            System.out.println("1 .............. Agregar ticket");
            System.out.println("2 .............. Trabajar tickets");
            System.out.println("3 .............. Cargar tickets");
            System.out.println("4 .............. Listado general de tickets");
            System.out.println("5 .............. Listado de tickets por cola");
            System.out.println("6 .............. Listado de tickets por usuario");
            System.out.println("0 .............. Salir");
            System.out.println("Seleccione la opción deseada ");
            opcion = scanner.nextInt();
            switch (opcion){
                case 1:
                    Ticket ticket1 = new Ticket();
                    ticket1.crear();
                    this.colaMesa.agregar(ticket1);
                    break;
                case 2:
                    this.ingresoSistema();
                    break;
                case 3:
                    try {
                        cargar();
                    }catch(Exception ex) {
                        System.out.println("Error al cargar el archivo. " +  ex.toString());
                    }
                    break;
                case 4:
                    generarReporteListadoGeneral();
                    break;
                case 5:
                    generarReporteListadoPorCola();
                    break;
                case 6:
                    generarReportListadoPorUsuario();
                    break;
                default:
                    if (opcion != 0) {
                        System.out.println("Opción inválida");
                    }
                    break;
            }
        } while(opcion != 0);
    }

    public void ingresoSistema() {
        this.nitUsuario = "";
        this.rolUsuario = "";
        int opcion = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------- Ingreso sistema ---------");
        System.out.println("Ingrese NIT");
        this.nitUsuario = scanner.nextLine();
        System.out.println("Seleccione rol");
        System.out.println("1. " + TipoUsuario.MESA);
        System.out.println("2. " + TipoUsuario.SOPORTE);
        System.out.println("3. " + TipoUsuario.DESARROLLO);
        opcion = scanner.nextInt();
        if (opcion == 1){
            this.rolUsuario = TipoUsuario.MESA.name();
        }else if(opcion ==2 ) {
            this.rolUsuario = TipoUsuario.SOPORTE.name();
        }else if (opcion == 3) {
            this.rolUsuario = TipoUsuario.DESARROLLO.name();
        } else {
            System.out.println("Rol inválido.");
            return;
        }

        do {
            System.out.println("----------  Menu principal OTRS ---------");
            System.out.println("1 .............. Asignar ticket");
            System.out.println("0 .............. Logout");
            System.out.println("Seleccione la opción deseada ");
            opcion = scanner.nextInt();
            switch (opcion){
                case 1:
                    Ticket ticketActual;
                    if (this.rolUsuario == TipoUsuario.MESA.name()) {
                        ticketActual = this.colaMesa.quitar();
                        if (ticketActual == null) break;
                        ticketActual.agregarBitacora(new Bitacora(this.nitUsuario, "Se asignó ticket para trabajar.", TipoEvento.ASIGNAR));
                        int opcionTicket = 0;
                        do {
                            System.out.println("--------- Opciones de ticket ----------");
                            System.out.println("1 .............. Escalar a cola de soporte");
                            System.out.println("2 .............. Resolver ticket");
                            opcionTicket = scanner.nextInt();
                            switch (opcionTicket) {
                                case 1:
                                    ticketActual.agregarBitacora(new Bitacora(this.nitUsuario,"Se movió a la cola de soporte.", TipoEvento.MOVER));
                                    this.colaSoporte.agregar(ticketActual);
                                    System.out.println("Se movió el ticket a la cola de soporte.");
                                    opcionTicket = -1;
                                    break;
                                case 2:
                                    System.out.println("Ingresar solución:");
                                    scanner.nextLine();
                                    String solucion = scanner.nextLine();
                                    ticketActual.setEstado("RESUELTO");
                                    ticketActual.agregarBitacora(new Bitacora(this.nitUsuario, "Se resolvió el ticket. " + solucion, TipoEvento.SOLUCION));
                                    this.colaAtendidos.agregar(ticketActual);
                                    opcionTicket = -1;
                                    break;
                                default:
                                    System.out.println("Opción inválida");
                                    break;
                            }
                        }while(opcionTicket != -1);
                    }else if (this.rolUsuario == TipoUsuario.SOPORTE.name()) {
                        ticketActual = this.colaSoporte.quitar();
                        if (ticketActual == null) break;
                        ticketActual.agregarBitacora(new Bitacora(this.nitUsuario, "Se asignó ticket para trabajar.", TipoEvento.ASIGNAR));
                        int opcionTicket = 0;
                        do {
                            System.out.println("--------- Opciones de ticket ----------");
                            System.out.println("1 .............. Escalar a cola de desarrollo");
                            System.out.println("2 .............. Resolver ticket");
                            opcionTicket = scanner.nextInt();
                            switch (opcionTicket) {
                                case 1:
                                    ticketActual.agregarBitacora(new Bitacora(this.nitUsuario,"Se movió a la cola de desarrollo.", TipoEvento.MOVER));
                                    this.colaDesarrollo.agregar(ticketActual);
                                    System.out.println("Se movió el ticket a la cola de desarrollo.");
                                    opcionTicket = -1;
                                    break;
                                case 2:
                                    System.out.println("Ingresar solución:");
                                    scanner.nextLine();
                                    String solucion = scanner.nextLine();
                                    ticketActual.setEstado("RESUELTO");
                                    ticketActual.agregarBitacora(new Bitacora(this.nitUsuario, "Se resolvió el ticket. " + solucion, TipoEvento.SOLUCION));
                                    this.colaAtendidos.agregar(ticketActual);
                                    opcionTicket = -1;
                                    break;
                                default:
                                    System.out.println("Opción inválida");
                                    break;
                            }
                        }while(opcionTicket != -1);
                    }else if (this.rolUsuario == TipoUsuario.DESARROLLO.name()) {
                        ticketActual = this.colaDesarrollo.quitar();
                        if (ticketActual == null) break;
                        ticketActual.agregarBitacora(new Bitacora(this.nitUsuario, "Se asignó ticket para trabajar.", TipoEvento.ASIGNAR));
                        int opcionTicket = 0;
                        do {
                            System.out.println("--------- Opciones de ticket ----------");
                            System.out.println("1 .............. Resolver ticket");
                            opcionTicket = scanner.nextInt();
                            switch (opcionTicket) {
                                case 1:
                                    System.out.println("Ingresar solución:");
                                    scanner.nextLine();
                                    String solucion = scanner.nextLine();
                                    ticketActual.setEstado("RESUELTO");
                                    ticketActual.agregarBitacora(new Bitacora(this.nitUsuario, "Se resolvió el ticket. " + solucion, TipoEvento.SOLUCION));
                                    this.colaAtendidos.agregar(ticketActual);
                                    opcionTicket = -1;
                                    break;
                                default:
                                    System.out.println("Opción inválida");
                                    break;
                            }
                        }while(opcionTicket != -1);
                    }
                    break;
                default:
                    if (opcion != 0) {
                        System.out.println("Opción inválida");
                    }
                    break;
            }
        } while(opcion != 0);
    }

    public void cargar()  throws Exception {
        String jsonString="";
        File file = new File("informacion.json");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            jsonString = jsonString +  sc.nextLine();
        }

        JSONArray tickets = new JSONArray(jsonString);
        for (int i = 0;  i < tickets.length(); i ++) {
            JSONObject ticket = tickets.getJSONObject(i);
            Ticket nuevoTicket = new Ticket(ticket.get("nitUsuario").toString(), ticket.get("problema").toString(), "CREADO");
            if (ticket.get("cola").toString().equals("mesa")) {
                this.colaMesa.agregar(nuevoTicket);
            }else if (ticket.get("cola").toString().equals("soporte")) {
                this.colaSoporte.agregar(nuevoTicket);
            }else if (ticket.get("cola").toString().equals("desarrollo")) {
                this.colaDesarrollo.agregar(nuevoTicket);
            }
        }
        System.out.println("Archivo cargado correctamente");
    }

    public void generarReporteListadoGeneral(){
        System.out.println("********* REPORTE LISTADO GENERAL ***********");
        for (int i = 0; i < this.colaMesa.cola.size(); i++) {
            Ticket t = this.colaMesa.cola.get(i);
            System.out.println("\t \t " +  t.toString() + "\t \t " +  t.getEstado());

            for (int j = 0; j < t.getBitacora().size(); j++) {
                Bitacora b = t.getBitacora().get(j);
                System.out.println("\t\t\t\t" + b.getNitSoporte() + " \t\t" + b.getFechaHora() + " \t\t" + b.getMensaje() + " \t\t"+ b.getEvento());
            }
        }
        for (int i = 0; i < this.colaSoporte.cola.size(); i++) {
            Ticket t = this.colaSoporte.cola.get(i);
            System.out.println("\t \t " +  t.toString() + "\t \t " +  t.getEstado());

            for (int j = 0; j < t.getBitacora().size(); j++) {
                Bitacora b = t.getBitacora().get(j);
                System.out.println("\t\t\t\t" + b.getNitSoporte() + " \t\t" + b.getFechaHora() + " \t\t" + b.getMensaje() + " \t\t"+ b.getEvento());
            }
        }
        for (int i = 0; i < this.colaDesarrollo.cola.size(); i++) {
            Ticket t = this.colaDesarrollo.cola.get(i);
            System.out.println("\t \t " +  t.toString() + "\t \t " +  t.getEstado());

            for (int j = 0; j < t.getBitacora().size(); j++) {
                Bitacora b = t.getBitacora().get(j);
                System.out.println("\t\t\t\t" + b.getNitSoporte() + " \t\t" + b.getFechaHora() + " \t\t" + b.getMensaje() + " \t\t"+ b.getEvento());
            }
        }
    }

    public void generarReporteListadoPorCola(){
        System.out.println("********* REPORTE LISTADO POR COLA ***********");
        System.out.println("********* MESA DE AYUDA ***********");
        for (int i = 0; i < this.colaMesa.cola.size(); i++) {
            Ticket t = this.colaMesa.cola.get(i);
            System.out.println("\t \t " + t.getNitUsuario() + "\t \t " +  t.toString() + "\t \t Mesa de ayuda");
        }
        System.out.println("********* SOPORTE ***********");
        for (int i = 0; i < this.colaSoporte.cola.size(); i++) {
            Ticket t = this.colaSoporte.cola.get(i);
            System.out.println("\t \t " + t.getNitUsuario() + "\t \t " +  t.toString() + "\t \t Mesa de soporte");
        }
        System.out.println("********* DESARROLLO ***********");
        for (int i = 0; i < this.colaDesarrollo.cola.size(); i++) {
            Ticket t = this.colaDesarrollo.cola.get(i);
            System.out.println("\t \t " + t.getNitUsuario() + "\t \t " +  t.toString() + "\t \t Mesa de desarrollo");
        }
        System.out.println("********* ATENDIDOS ***********");
        for (int i = 0; i < this.colaAtendidos.cola.size(); i++) {
            Ticket t = this.colaAtendidos.cola.get(i);
            System.out.println("\t \t " + t.getNitUsuario() + "\t \t " +  t.toString() + "\t \t Tickets atendidos");
        }
    }

    public void generarReportListadoPorUsuario() {
        System.out.println("********* REPORTE LISTADO POR USUARIO ***********");
        for (int i = 0; i < this.colaMesa.cola.size(); i++) {
            Ticket t = this.colaMesa.cola.get(i);
            System.out.println("\t \t " + t.getNitUsuario() + "\t \t " +  t.toString() + "\t \t " +  t.getEstado() + "\t \t Mesa de ayuda");
        }
        for (int i = 0; i < this.colaSoporte.cola.size(); i++) {
            Ticket t = this.colaSoporte.cola.get(i);
            System.out.println("\t \t " + t.getNitUsuario() + "\t \t " +  t.toString() + "\t \t " +  t.getEstado() + "\t \t Mesa de soporte");
        }
        for (int i = 0; i < this.colaDesarrollo.cola.size(); i++) {
            Ticket t = this.colaDesarrollo.cola.get(i);
            System.out.println("\t \t " + t.getNitUsuario() + "\t \t " +  t.toString() + "\t \t " +  t.getEstado() + "\t \t Mesa de desarrollo");
        }
    }

}