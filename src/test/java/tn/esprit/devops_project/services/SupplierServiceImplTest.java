package tn.esprit.devops_project.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.Optional;

public class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        supplier = new Supplier();
        supplier.setIdSupplier(1L); // Set ID
        supplier.setCode("SUP001"); // Set code
        supplier.setLabel("Sample Supplier"); // Set label
    }

    @Test
    public void testAddSupplier() {

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);


        Supplier createdSupplier = supplierService.addSupplier(supplier);


        assertNotNull(createdSupplier);
        assertEquals("SUP001", createdSupplier.getCode());
        assertEquals("Sample Supplier", createdSupplier.getLabel());
        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    public void testRetrieveSupplier() {

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));


        Supplier retrievedSupplier = supplierService.retrieveSupplier(1L);


        assertNotNull(retrievedSupplier);
        assertEquals("SUP001", retrievedSupplier.getCode()); // Verify code
        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveSupplierNotFound() {

        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            supplierService.retrieveSupplier(1L);
        });
    }
}
