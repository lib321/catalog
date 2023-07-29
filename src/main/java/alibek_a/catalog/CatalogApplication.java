package alibek_a.catalog;

import alibek_a.catalog.entity.catalog.Category;
import alibek_a.catalog.entity.catalog.CharValue;
import alibek_a.catalog.entity.catalog.Characteristic;
import alibek_a.catalog.entity.catalog.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class CatalogApplication {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        System.out.println("[1] - Создать категорию");
        System.out.println("[2] - Создать товар");
        System.out.println("[3] - Удалить товар");
        System.out.println("[4] - Изменить товар");
        System.out.print("Выберите действие: ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        try {
            manager.getTransaction().begin();
            switch (str) {
                case "1" -> {
                    System.out.print("Введите название категории: ");
                    String categoryName = scanner.nextLine();
                    TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c where c.name = ?1", Category.class);
                    categoryTypedQuery.setParameter(1, categoryName);
                    List<Category> categories = categoryTypedQuery.getResultList();
                    if (categories.isEmpty()) {
                        Category category = new Category();
                        category.setName(categoryName);
                        manager.persist(category);
                        System.out.print("Введите характеристики категории: ");
                        String characteristics = scanner.nextLine();
                        String[] split = characteristics.split(",\\s");
                        for (int i = 0; i < split.length; i++) {
                            Characteristic characteristic = new Characteristic();
                            characteristic.setName(split[i]);
                            characteristic.setCategory(category);
                            manager.persist(characteristic);
                        }
                    } else {
                        System.out.println("Категория с таким названием существует\n");
                    }
                    manager.getTransaction().commit();
                }
                case "2" -> {
                    TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c", Category.class);
                    List<Category> categories = categoryTypedQuery.getResultList();
                    for (Category category : categories) {
                        System.out.println(" - " + category.getName() + " [" + category.getId() + "]");
                    }
                    System.out.print("Выберите категорию: ");
                    String categoryId = scanner.nextLine();
                    long categoryID1 = Integer.parseInt(categoryId);
                    Category category = manager.find(Category.class, categoryID1);
                    while (category == null) {
                        System.out.print("Введите еще раз: ");
                        categoryId = scanner.nextLine();
                        categoryID1 = Integer.parseInt(categoryId);
                        category = manager.find(Category.class, categoryID1);
                    }
                    System.out.print("Введите название: ");
                    String productName = scanner.nextLine();
                    Product product = new Product();
                    product.setName(productName);
                    product.setCategory(category);
                    System.out.print("Введите стоимость: ");
                    String price = scanner.nextLine();
                    while (!price.matches("\\d+")) {
                        System.out.print("Введите еще раз: ");
                        price = scanner.nextLine();
                    }
                    long price1 = Integer.parseInt(price);
                    product.setPrice(price1);
                    manager.persist(product);
                    List<Characteristic> characteristics = category.getCharacteristics();
                    for (Characteristic characteristic : characteristics) {
                        System.out.print(characteristic.getName() + ": ");
                        String charValueName = scanner.nextLine();
                        CharValue charValue = new CharValue();
                        charValue.setValue(charValueName);
                        charValue.setCharacteristic(characteristic);
                        charValue.setProduct(product);
                        manager.persist(charValue);

                    }
                    manager.getTransaction().commit();
                }
                case "3" -> {
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
                }
                case "4" -> {
                    System.out.print("Введите ID товара: ");
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
                }
            }
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
