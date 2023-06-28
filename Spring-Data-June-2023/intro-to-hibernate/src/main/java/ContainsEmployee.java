import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class ContainsEmployee {

    public static void main(String[] args) {
        EntityManager em = Utils.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        String fullName = scanner.nextLine();

        em.getTransaction().begin();


        String isEmployeePresented = em.createQuery("FROM Employee where CONCAT_WS(' ', firstName, lastName) = :fullName", Employee.class)
                .setParameter("fullName", fullName)
                .getResultList()
                .isEmpty() ? "No" : "Yes";

        em.getTransaction().commit();
        System.out.println(isEmployeePresented);

        em.close();

    }
}
