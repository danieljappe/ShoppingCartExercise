package com.example.shoppingcartexercise;

import com.example.shoppingcartexercise.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private Cart cart; // reference til indkøbskurv session

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        // check om attributten ‘cart’ findes i sessions objektet
        cart = (Cart) session.getAttribute("cart");
        if (cart == null){
            // hvis ikke - opret en ny indkøbskurv (cart)
            cart = new Cart();

            // opret indkøbskurvattributten (cart) i session objektet
            session.setAttribute("cart", cart);

            // sæt sessionslevetiden til 30 sec til testformål
            session.setMaxInactiveInterval(30);

            // returner cart.html, der viser ui
            return "cart";
        }
        // tilføj attributterne ‘items’, og ‘total’ til model objektet
        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.getTotal());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String name, @RequestParam double price, @RequestParam int quantity) {
    // opret nyt item
    CartItem cartItem = new CartItem(name, price, quantity);

    // tilføj item til cart
    cart.addItem(cartItem);

    // redirect til /cart
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam int index) {
    // slet item på ‘index’ i cart
    cart.removeItem(index);

    // redirect til /cart
        return "cart";
    }

    @PostMapping("/cart/empty")
    public String emptyCart(HttpSession session) {
    // fjern ‘cart’ attribute fra session objekt
    session.removeAttribute("cart");
    // redirect til /cart
        return "cart";

    }
}
