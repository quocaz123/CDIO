package com.example.CDIO.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.CDIO.domain.Item;
import com.example.CDIO.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item handleCreateItem(Item item) {
        return this.itemRepository.save(item);
    }

    public Item fetchItemById(long id) {
        Optional<Item> iOptional = this.itemRepository.findById(id);
        if (iOptional.isPresent()) {
            return iOptional.get();
        }
        return null;
    }

    public List<Item> fetchAllItem() {
        return this.itemRepository.findAll();
    }

    public Item handleUpdateItem(Item item) {
        Item currentItem = this.fetchItemById(item.getId());
        if (currentItem != null) {
            currentItem.setName(item.getName());
            currentItem.setPrice(item.getPrice());
            currentItem.setDescription(item.getDescription());

            currentItem = this.itemRepository.save(currentItem);
        }
        return currentItem;
    }

    public void handleDeleteItem(long id) {
        this.itemRepository.deleteById(id);
    }

}
