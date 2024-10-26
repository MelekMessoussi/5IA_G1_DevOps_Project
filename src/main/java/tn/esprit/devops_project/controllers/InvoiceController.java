package tn.esprit.devops_project.controllers;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.services.Iservices.IInvoiceService;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class InvoiceController {

    private static final Logger logger = LogManager.getLogger(InvoiceController.class);

    IInvoiceService invoiceService;

    @GetMapping("/invoice")
    public List<Invoice> getInvoices() {
        logger.info("Fetching all invoices");
        List<Invoice> invoices = invoiceService.retrieveAllInvoices();
        logger.debug("Retrieved {} invoices", invoices.size());
        return invoices;
    }

    @GetMapping("/invoice/{invoiceId}")
    public Invoice retrieveInvoice(@PathVariable Long invoiceId) {
        logger.info("Fetching invoice with ID: {}", invoiceId);
        Invoice invoice = invoiceService.retrieveInvoice(invoiceId);
        if (invoice != null) {
            logger.debug("Invoice found: {}", invoice);
        } else {
            logger.warn("Invoice with ID: {} not found", invoiceId);
        }
        return invoice;
    }

    @PutMapping("/invoice/{invoiceId}")
    public void cancelInvoice(@PathVariable Long invoiceId) {
        logger.info("Attempting to cancel invoice with ID: {}", invoiceId);
        try {
            invoiceService.cancelInvoice(invoiceId);
            logger.info("Invoice with ID: {} canceled successfully", invoiceId);
        } catch (Exception e) {
            logger.error("Error canceling invoice with ID: {}", invoiceId, e);
        }
    }

    @GetMapping("/invoice/supplier/{supplierId}")
    public List<Invoice> getInvoicesBySupplier(@PathVariable Long supplierId) {
        logger.info("Fetching invoices for supplier with ID: {}", supplierId);
        List<Invoice> invoices = invoiceService.getInvoicesBySupplier(supplierId);
        logger.debug("Retrieved {} invoices for supplier ID: {}", invoices.size(), supplierId);
        return invoices;
    }

    @PutMapping(value = "/invoice/operator/{idOperator}/{idInvoice}")
    public void assignOperatorToInvoice(@PathVariable Long idOperator, @PathVariable Long idInvoice) {
        logger.info("Assigning operator ID: {} to invoice ID: {}", idOperator, idInvoice);
        try {
            invoiceService.assignOperatorToInvoice(idOperator, idInvoice);
            logger.info("Successfully assigned operator ID: {} to invoice ID: {}", idOperator, idInvoice);
        } catch (Exception e) {
            logger.error("Error assigning operator ID: {} to invoice ID: {}", idOperator, idInvoice, e);
        }
    }

    @GetMapping("/invoice/price/{startDate}/{endDate}")
    public float getTotalAmountInvoiceBetweenDates(@PathVariable Date startDate, @PathVariable Date endDate){
        logger.info("Calculating total amount of invoices between {} and {}", startDate, endDate);
        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);
        logger.info("Total amount between {} and {}: {}", startDate, endDate, totalAmount);
        return totalAmount;
    }
}


