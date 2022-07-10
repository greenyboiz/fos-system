package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.CartItem;
import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.entities.QRCode;

import java.util.List;

public interface ICartItemService {
    public List<CartItem> listCartItemByQRCode(QRCode qrCode);

    public CartItem addDishesToCart(CartItem cartItem);
}
