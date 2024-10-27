package tn.esprit.devops_project.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Stock stock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stock = new Stock();
        stock.setIdStock(1L);
        stock.setTitle("Sample Stock");
    }

    @Test
    public void testAddStock() {
        // Arrange: Define behavior for the mocked repository
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        // Act: Call the method under test
        Stock createdStock = stockService.addStock(stock);

        // Assert: Check that the returned stock is as expected
        assertNotNull(createdStock);
        assertEquals("Sample Stock", createdStock.getTitle());
        verify(stockRepository, times(1)).save(stock);
    }
}
