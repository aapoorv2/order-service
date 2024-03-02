package com.swiggy.order.services;

import com.swiggy.order.models.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogService {
    @Value("${catalog.service.url}")
    private String catalogServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ItemDTO getItemById(Long restaurantId, Long itemId) {
        String url = catalogServiceUrl + "/restaurants/" + restaurantId + "/items/" + itemId;
        return restTemplate.getForObject(url, ItemDTO.class);
    }
}
