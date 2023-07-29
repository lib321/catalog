package alibek_a.catalog;

import alibek_a.catalog.entity.catalog.CharValue;
import alibek_a.catalog.entity.catalog.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class DeleteProduct {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите ID товара: ");
            String productID = scanner.nextLine();
            long productID1  = Integer.parseInt(productID);
            Product product = manager.find(Product.class, productID1);
            List<CharValue> charValues = product.getCharValues();
            for (CharValue charValue : charValues) {
                manager.remove(charValue);
            }
            manager.remove(product);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
