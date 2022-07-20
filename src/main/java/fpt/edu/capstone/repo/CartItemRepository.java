//package fpt.edu.capstone.repo;
//
//import fpt.edu.capstone.entities.CartItem;
//import fpt.edu.capstone.entities.Customer;
//import fpt.edu.capstone.entities.Dishes;
//import fpt.edu.capstone.entities.QRCode;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CartItemRepository extends JpaRepository<CartItem,Long> {
//
//    public List<CartItem> findByCustomer(Customer customer);
//
//    public List<CartItem> findByDishes(Dishes dishes);
//
//    @Query("select c from CartItem c where c.qrCode.QRCodeId = ?1")
//    public List<CartItem> findAllByQRCodeId(Long id);
//
//    public CartItem findByCustomerAndDishes(Customer customer, Dishes dishes);
//
//    @Query("select c from CartItem c where c.qrCode.QRCodeId = ?1 and c.dishes.dishesId = ?2 ")
//    public CartItem checkExistCartItem(Long qrCodeId, Long dishesId);
//
//    @Query("delete from CartItem c where c.dishes.dishesId = ?1")
//    public void removeDishesInCart(Long dishesId);
//}
