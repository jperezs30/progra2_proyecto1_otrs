package ticket;
import java.util.Random;

public class ColaDesarrollo extends  ColaServicio{

    @Override
    public Ticket quitar() {
        if (this.cola.size() > 0) {
            Random random = new Random();
            int index = random.nextInt(this.cola.size() -1);
            Ticket ticket = this.cola.remove(index);
            System.out.println("Se asignó el ticket " + ticket.toString() + ": " + ticket.getProblema());
            return ticket;
        }
        System.out.println("No existen tickets para trabajar.");
        return null;
    }

}
