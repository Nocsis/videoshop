package videoshop.order;

import videoshop.catalog.Disc;

import java.util.Optional;

import org.salespointframework.catalog.Product;
import org.salespointframework.core.AbstractEntity;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes("wishlist")
class WishlistController {

  private final OrderManager<Order> orderManager;

  /**
   * Creates a new {@link OrderController} with the given {@link OrderManager}.
   *
   * @param orderManager must not be {@literal null}.
   */
  WishlistController(OrderManager<Order> orderManager) {

    Assert.notNull(orderManager, "OrderManager must not be null!");
    this.orderManager = orderManager;
  }

  /**
   * Creates a new {@link Cart} instance to be stored in the session (see the class-level {@link SessionAttributes}
   * annotation).
   *
   * @return a new {@link Cart} instance.
   */
  @ModelAttribute("wishlist")
  Cart initializeWishlist() {
    return new Cart();
  }

  @PostMapping("/wishlist")
  String addItem(@RequestParam("pid") Disc disc, @ModelAttribute Cart wishlist) {

    wishlist.addOrUpdateItem(disc, Quantity.of(1));

    System.out.println(wishlist.isEmpty());

    return "redirect:wishlist";
  }

  @PostMapping("/remWish")
  String remove(@RequestParam("name") String name, @ModelAttribute Cart wishlist) {

    wishlist.removeItem(name);

    return "redirect:wishlist";
  }

  @PostMapping("/printstuff")
  String print(@ModelAttribute Cart wishlist) {

    System.out.println(wishlist.isEmpty());

    return "redirect:wishlist";
  }

  @GetMapping("/wishlist")
  String wish() {
    return "wishlist";
  }
}

