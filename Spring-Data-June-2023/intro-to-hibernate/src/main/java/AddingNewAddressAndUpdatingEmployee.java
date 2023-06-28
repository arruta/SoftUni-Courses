import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class AddingNewAddressAndUpdatingEmployee {


    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        String employeeLastName = scanner.nextLine();

        em.getTransaction().begin();
//        Town townForNewAdddress = em.createQuery("FROM Town WHERE id = 7", Town.class).getSingleResult();

        Set<Employee> employees = Set.copyOf(em.createQuery("FROM Employee WHERE lastName = :employeeLastName", Employee.class)
                .setParameter("employeeLastName", employeeLastName)
                .getResultList());


        Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");
//        newAddress.setTown(townForNewAdddress);
        newAddress.setEmployees(null);

        em.persist(newAddress);

        employees.forEach(e -> e.setAddress(newAddress));
        em.flush();

        em.getTransaction().commit();
        em.close();
    }
}
