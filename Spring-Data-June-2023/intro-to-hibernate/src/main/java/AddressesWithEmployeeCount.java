import entities.Address;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AddressesWithEmployeeCount {

    private static final String PRINT_ADDRESSES_FORMAT = "%s %s - %d employees\n";

    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(address -> {
                    System.out.printf(PRINT_ADDRESSES_FORMAT, address.getText(), address.getTown().getName(), address.getEmployees().size());
                });


        em.getTransaction().commit();
        em.close();
    }
}
