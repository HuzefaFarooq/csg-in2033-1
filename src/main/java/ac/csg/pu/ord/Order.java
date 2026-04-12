package ac.csg.pu.ord;

import ac.csg.pu.sales.CartItem;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Order {

    private String id;
    private OrderStatus status;
    private LocalDate date;

    private Map<Integer, CartItem> items;

    // ---- Getters ----
    public String getId() { return id; }
    public Collection<CartItem> getItems() { return items.values(); }
    public OrderStatus getStatus() { return status; }
    public LocalDate getDate() { return date; }

    // ---- Setters ----
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setDate(LocalDate date) { this.date = date; }

    // ---- Item Management ----
    public void addItem(CartItem item) { this.items.put(-1, item); }
    public void addItems(List<CartItem> items) { }
    public void clearItems() { this.items.clear(); }
}
