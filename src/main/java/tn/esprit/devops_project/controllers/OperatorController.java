package tn.esprit.devops_project.controllers;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.services.Iservices.IOperatorService;

import java.util.List;

@RestController
@AllArgsConstructor
public class OperatorController {

    private static final Logger logger = LogManager.getLogger(OperatorController.class);
    private final IOperatorService operatorService;

    @GetMapping("/operator")
    public List<Operator> getOperators() {
        logger.info("Fetching all operators");
        List<Operator> operators = operatorService.retrieveAllOperators();
        logger.debug("Fetched operators: {}", operators);
        return operators;
    }

    @GetMapping("/operator/{operatorId}")
    public Operator retrieveOperator(@PathVariable Long operatorId) {
        logger.info("Fetching operator with ID: {}", operatorId);
        Operator operator = operatorService.retrieveOperator(operatorId);
        if (operator != null) {
            logger.debug("Operator found: {}", operator);
        } else {
            logger.warn("Operator with ID {} not found", operatorId);
        }
        return operator;
    }

    @PostMapping("/operator")
    public Operator addOperator(@RequestBody Operator operator) {
        logger.info("Adding new operator: {}", operator);
        Operator addedOperator = operatorService.addOperator(operator);
        logger.debug("Added operator: {}", addedOperator);
        return addedOperator;
    }

    @DeleteMapping("/operator/{operatorId}")
    public void removeOperator(@PathVariable Long operatorId) {
        logger.info("Deleting operator with ID: {}", operatorId);
        operatorService.deleteOperator(operatorId);
        logger.debug("Deleted operator with ID: {}", operatorId);
    }

    @PutMapping("/operator")
    public Operator modifyOperator(@RequestBody Operator operator) {
        logger.info("Updating operator: {}", operator);
        Operator updatedOperator = operatorService.updateOperator(operator);
        logger.debug("Updated operator: {}", updatedOperator);
        return updatedOperator;
    }
}
