import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class RemoveTowns {
    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        String townName = scanner.nextLine();
        em.getTransaction().begin();

        Town townToDelete = em.createQuery("FROM Town WHERE name = :townName", Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        List<Address> addressesToDelete = em.createQuery("FROM Address WHERE town.id = :townToDeleteId", Address.class)
                .setParameter("townToDeleteId", townToDelete.getId())
                .getResultList();

        addressesToDelete.forEach(a->a.getEmployees().forEach(e->e.setAddress(null)));

        for (Address address : addressesToDelete) {
            em.remove(address);
        }

        em.remove(townToDelete);

        System.out.printf("%d address%s in %s deleted",
                addressesToDelete.size(),
                addressesToDelete.size() == 1? "":"es",
                townName);


        em.getTransaction().commit();
        em.close();
    }
}
