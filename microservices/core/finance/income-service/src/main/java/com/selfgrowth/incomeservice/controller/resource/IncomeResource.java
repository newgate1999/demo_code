package com.selfgrowth.incomeservice.controller.resource;

import com.selfgrowth.domain.dto.IncomeDto;
import com.selfgrowth.incomeservice.controller.errors.BadRequestAlertException;
import com.selfgrowth.incomeservice.controller.web.HeaderUtil;
import com.selfgrowth.incomeservice.controller.web.PaginationUtil;
import com.selfgrowth.incomeservice.controller.web.ResponseUtil;
import com.selfgrowth.incomeservice.service.IncomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IncomeResource {
    private final Logger log = LoggerFactory.getLogger(IncomeResource.class);

    private static final String ENTITY_NAME = "agentArea";

    private final IncomeService incomeService;

    @Value("")
    private String applicationName;

    public IncomeResource(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/incomes")
    public ResponseEntity<List<IncomeDto>> createIncomes(@Valid @RequestBody List<IncomeDto> incomeDtoList) throws URISyntaxException {
        log.debug("REST request to save all income: {}", incomeDtoList);
        int index = 1;
        for (IncomeDto incomeDto: incomeDtoList) {
            if (incomeDto.getId() != null) {
                throw  new BadRequestAlertException("A new income" +  index + "cannot already have an ID", ENTITY_NAME, "idexists");
            }
            index++;
        }

        List<IncomeDto> result = incomeService.saveAll(incomeDtoList);
        return ResponseEntity.created(new URI("api/incomes")).body(result);
    }

    @PutMapping("/incomes")
    public ResponseEntity<List<IncomeDto>> updateIncomes(@Valid @RequestBody List<IncomeDto> incomeDtoList) throws URISyntaxException {
        log.debug("REST request to edit all income: {}", incomeDtoList);
        for (IncomeDto incomeDto: incomeDtoList) {
            if (incomeDto.getId() == null) {
                throw  new BadRequestAlertException("Invalid", ENTITY_NAME, "idnull");
            }
        }

        List<IncomeDto> result = incomeService.saveAll(incomeDtoList);
        return ResponseEntity.created(new URI("api/incomes")).body(result);
    }

    @GetMapping("/income/{id}")
    public ResponseEntity<IncomeDto> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Income : {}", id);
        Optional<IncomeDto> incomeDto = incomeService.findById(id);
        return ResponseUtil.wrapOrNotFound(incomeDto);
    }

    @GetMapping("/search/incomes")
    public ResponseEntity<List<IncomeDto>> search(@RequestParam MultiValueMap<String, String> queryParams, Pageable pageable) {
        log.debug("REST request to search for a page of Incomes for query {}", queryParams);
        Page<IncomeDto> page = incomeService.search(queryParams, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @DeleteMapping("/income/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        log.debug("REST request to delete Income : {}", id);
        incomeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
