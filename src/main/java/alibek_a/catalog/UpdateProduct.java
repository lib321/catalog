package alibek_a.catalog;

import alibek_a.catalog.entity.catalog.CharValue;
import alibek_a.catalog.entity.catalog.Characteristic;
import alibek_a.catalog.entity.catalog.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class UpdateProduct {

    public static void main(String[] args) {
        // Введите новое название [Intel Core I9]: __
        // Введите новое стоимость [198000]: __
        // Производитель [Intel]: __

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            System.out.print("Введите ID товара: ");
            Scanner scanner = new Scanner(System.in);
            String productId = scanner.nextLine();
            long productId1 = Integer.parseInt(productId);
            Product product = manager.find(Product.class, productId1);
            System.out.print("Введите новое название [" + product.getName() + "]: ");
            String productName = scanner.nextLine();
            if (!productName.isEmpty()) {
                product.setName(productName);
            }
            System.out.print("Введите новую стоимость [" + product.getPrice() + "]: ");
            String productPrice = scanner.nextLine();
            if (!productPrice.isEmpty()) {
                long productPrice1 = Integer.parseInt(productPrice);
                product.setPrice(productPrice1);
            }
            System.out.println("Введите следующие значения: ");
            List<Characteristic> characteristics = product.getCategory().getCharacteristics();
            for (Characteristic characteristic : characteristics) {
                TypedQuery<CharValue> charValueTypedQuery = manager.createQuery("select c from CharValue c where c.product.id = ?1" +
                        " and c.characteristic.id = ?2", CharValue.class);
                charValueTypedQuery.setParameter(1, productId1);
                charValueTypedQuery.setParameter(2, characteristic.getId());
                List<CharValue> charValues = charValueTypedQuery.getResultList();
                if (charValues.isEmpty()) {
                    System.out.print(characteristic.getName() + "[]: ");
                    String charValue = scanner.nextLine();
                    CharValue charValue1 = new CharValue();
                    charValue1.setValue(charValue);
                    charValue1.setProduct(product);
                    charValue1.setCharacteristic(characteristic);
                    manager.persist(charValue1);
                } else {
                    System.out.print(characteristic.getName() + " [" + charValues.get(0).getValue() + "]: ");
                    String charValue = scanner.nextLine();
                    if (!charValue.isEmpty()) {
                        charValues.get(0).setValue(charValue);
                    }
                }
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
