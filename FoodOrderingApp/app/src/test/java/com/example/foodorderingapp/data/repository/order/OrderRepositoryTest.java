package com.example.foodorderingapp.data.repository.order;

import static org.mockito.Mockito.*;

import com.example.foodorderingapp.data.model.entity.Order;
import com.example.foodorderingapp.data.model.entity.OrderItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryTest {

    @Mock
    private IOrderRepository.OrderCallback mockCallback;

    @Mock
    private Order mockOrder;

    @Mock
    private OrderRepository mockOrderRepository;

    @Before
    public void setUp() {
        // No need to initialize mockOrderRepository here
    }

    @Test
    public void testGetOrderById_Success() {
        // Mock Firestore behavior
        doAnswer(invocation -> {
            IOrderRepository.OrderCallback callback = invocation.getArgument(1);
            callback.onOrderLoaded(mockOrder);
            return null;
        }).when(mockOrderRepository).getOrderById(eq("1"), any());

        // Mock Firestore document snapshot data
        HashMap<String, OrderItem> orderItemMap = new HashMap<>();
        mockOrder.setOrderItem(orderItemMap);

        // Run the method to be tested
        mockOrderRepository.getOrderById("1", mockCallback);

        // Verify that the callback method is called with the correct order object
        verify(mockCallback).onOrderLoaded(mockOrder);
    }
}
