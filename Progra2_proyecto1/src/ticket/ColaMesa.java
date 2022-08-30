package ticket;

public class ColaMesa extends  ColaServicio{

    @Override
    public Ticket quitar() {
        if (this.cola.size() > 0) {
            Ticket ticket = this.cola.remove(0);
            System.out.println("Se asignó el ticket " + ticket.toString() + ": " + ticket.getProblema());
            return ticket;
        }
        System.out.println("No existen tickets para trabajar.");
        return null;
    }
}
