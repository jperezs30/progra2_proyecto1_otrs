package ticket;

public class ColaAtendidos extends ColaServicio{

    @Override
    public Ticket quitar() {
        if (this.cola.size() > 0) {
            Ticket ticket = this.cola.remove(0);
            System.out.println("Se quitó el ticket " + ticket.toString());
            return ticket;
        }
        System.out.printf("No existen tickets atendidos.");
        return null;
    }

}
