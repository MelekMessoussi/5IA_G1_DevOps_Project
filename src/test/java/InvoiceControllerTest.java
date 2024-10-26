package tn.esprit.devops_project.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.services.Iservices.IInvoiceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class InvoiceControllerTest {

    private InvoiceController invoiceController;
    private IInvoiceService invoiceService;

    @BeforeEach
    public void setUp() {
        invoiceService = Mockito.mock(IInvoiceService.class);
        invoiceController = new InvoiceController(invoiceService);
    }

    @Test
    public void testGetInvoices() {
        // Arrange
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(1L, new Date(), 100.0, "Supplier1"));
        invoices.add(new Invoice(2L, new Date(), 150.0, "Supplier2"));

        when(invoiceService.retrieveAllInvoices()).thenReturn(invoices);

        // Act
        List<Invoice> result = invoiceController.getInvoices();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Supplier1", result.get(0).getSupplierName());
        verify(invoiceService, times(1)).retrieveAllInvoices();
    }

    @Test
    public void testRetrieveInvoice() {
        // Arrange
        Invoice invoice = new Invoice(1L, new Date(), 100.0, "Supplier1");
        when(invoiceService.retrieveInvoice(1L)).thenReturn(invoice);

        // Act
        Invoice result = invoiceController.retrieveInvoice(1L);

        // Assert
        assertEquals("Supplier1", result.getSupplierName());
        verify(invoiceService, times(1)).retrieveInvoice(1L);
    }

    @Test
    public void testCancelInvoice() {
        // Arrange
        doNothing().when(invoiceService).cancelInvoice(anyLong());

        // Act
        invoiceController.cancelInvoice(1L);

        // Assert
        verify(invoiceService, times(1)).cancelInvoice(1L);
    }

    @Test
    public void testGetInvoicesBySupplier() {
        // Arrange
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(1L, new Date(), 100.0, "Supplier1"));
        when(invoiceService.getInvoicesBySupplier(1L)).thenReturn(invoices);

        // Act
        List<Invoice> result = invoiceController.getInvoicesBySupplier(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Supplier1", result.get(0).getSupplierName());
        verify(invoiceService, times(1)).getInvoicesBySupplier(1L);
    }

}