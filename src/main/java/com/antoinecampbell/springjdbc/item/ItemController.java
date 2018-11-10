package com.antoinecampbell.springjdbc.item;

import com.antoinecampbell.springjdbc.util.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Item>> getAllItems(@Valid Page.Params pageParams) {
        return ResponseEntity.ok(itemRepository.getAllItems(pageParams));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity insertItem(@RequestBody @Valid Item item) {
        long id = itemRepository.insertItem(item);
        return ResponseEntity.created(URI.create(String.format("/items/%d", id))).build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Item> getItem(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(itemRepository.getItem(id));
        } catch (EmptyResultDataAccessException e) {
            log.error("Item not found", e);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Item> update(@PathVariable("id") long id,
                                       @RequestBody @Valid Item item) {
        itemRepository.updateItem(id, item);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> deleteItem(@PathVariable("id") long id) {
        itemRepository.deleteItem(id);
        return ResponseEntity.ok().build();
    }
}
