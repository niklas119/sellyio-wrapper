# sellyio-wrapper

Usage examples

```java
import com.hawolt.Selly;
import com.hawolt.impl.Orders;
import com.hawolt.impl.Payments;
import com.hawolt.objects.Order;
import com.hawolt.objects.Payment;
import com.hawolt.objects.PaymentCreator;
import com.hawolt.pagination.Pagination;
import com.hawolt.pagination.impl.OrderPagination;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Selly selly = Selly.authenticate("username", "api-key");

        Orders orders = selly.getOrders();

        try {
            OrderPagination orderPagination = orders.getAll(Pagination.Direction.DESCENDING);
            while (orderPagination.hasNext()) {
                List<Order> list = orderPagination.next();
                for (Order order : list) {
                    System.out.println(order.getCustomerEmail());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try {
            Order order = orders.get("order-id");
            System.out.format("Order-status %s\n", order.getStatus().name());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        PaymentCreator creator = PaymentCreator.builder()
                .setTitle("Product Title")
                .setGateway("PayPal")
                .setCustomerEmail("customer@email.com")
                .setValue(4.99D)
                .setCurrency("EUR")
                .setIpAddress("123.211.123.211")
                .setReturnUrl("http://example.com")
                .setWhitelabel(true)
                .setWebhookUrl("https://mycoolwebhook.com")
                .build();

        Payments payments = selly.getPayments();
        try {
            Payment payment = payments.create(creator);
            System.out.format("Payment-status %s\n", payment.getStatus().name());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
```
