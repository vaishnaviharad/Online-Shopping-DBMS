package com.anudip.main;

import java.util.List;
import java.util.Scanner;

import com.anudip.dao.CategoryDao;
import com.anudip.dao.ProductDao;
import com.anudip.dao.CustomerDao;
import com.anudip.model.Category;
import com.anudip.model.Products;
import com.anudip.model.Customer;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CustomerDao cdao = new CustomerDao();
        CategoryDao catdao = new CategoryDao();
        ProductDao pdao = new ProductDao();

        int choice;
        do {
            System.out.println("\n===== ONLINE SHOPPING SYSTEM MENU =====");
            System.out.println("1️⃣ Add Customer");
            System.out.println("2️⃣ View All Customers");
            System.out.println("3️⃣ Add Category");
            System.out.println("4️⃣ View All Categories");
            System.out.println("5️⃣ Add Product");
            System.out.println("6️⃣ View All Products");
            System.out.println("7️⃣ Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    Customer cust = new Customer();
                    System.out.print("Enter username: ");
                    cust.setUsername(sc.nextLine());
                    System.out.print("Enter first name: ");
                    cust.setFirst_name(sc.nextLine());
                    System.out.print("Enter last name: ");
                    cust.setLast_name(sc.nextLine());
                    System.out.print("Enter password: ");
                    cust.setPassword(sc.nextLine());
                    System.out.print("Enter address: ");
                    cust.setAddress(sc.nextLine());
                    System.out.print("Enter email: ");
                    cust.setEmail_id(sc.nextLine());
                    cdao.saveCustomer(cust);
                    break;

                case 2:
                    List<Customer> customers = cdao.getAllCustomers();
                    System.out.println("\n--- Customer List ---");
                    for (Customer c : customers) {
                        System.out.println(c.getCustomer_id() + " | " + c.getUsername() + " | " + c.getEmail_id());
                    }
                    break;

                case 3:
                    System.out.print("Enter category name: ");
                    String cname = sc.nextLine();
                    Category cat = new Category(cname);
                    catdao.save(cat);
                    break;

                case 4:
                    List<Category> categories = catdao.getAll();
                    System.out.println("\n--- Categories ---");
                    for (Category c : categories) {
                        System.out.println(c.getCategory_id() + " | " + c.getCategory_name());
                    }
                    break;

                case 5:
                    System.out.print("Enter product name: ");
                    String pname = sc.nextLine();
                    System.out.print("Enter product model: ");
                    String pmodel = sc.nextLine();
                    System.out.print("Enter product price: ");
                    double pprice = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter category ID: ");
                    int catid = sc.nextInt();
                    sc.nextLine();

                    Category selectedCat = catdao.getById(catid);
                    if (selectedCat == null) {
                        System.out.println("❌ Category not found. Create it first!");
                    } else {
                        Products prod = new Products();
                        prod.setP_name(pname);
                        prod.setP_model(pmodel);
                        prod.setP_price(pprice);
                        prod.setCategory(selectedCat);
                        pdao.save(prod);
                    }
                    break;

                case 6:
                    List<Products> products = pdao.getAll();
                    System.out.println("\n--- Product List ---");
                    for (Products p : products) {
                        String categoryName = (p.getCategory() != null)
                                ? p.getCategory().getCategory_name()
                                : "Uncategorized";
                        System.out.println(p.getP_id() + " | " + p.getP_name() + " | " + categoryName + " | ₹" + p.getP_price());
                    }
                    break;

                case 7:
                    System.out.println("👋 Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        } while (choice != 7);

        sc.close();
    }
}
