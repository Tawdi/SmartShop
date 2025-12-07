package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.Product;
import io.github.tawdi.smartshop.domain.repository.OrderItemRepository;
import io.github.tawdi.smartshop.domain.repository.ProductRepository;
import io.github.tawdi.smartshop.dto.product.ProductRequestDTO;
import io.github.tawdi.smartshop.dto.product.ProductResponseDTO;
import io.github.tawdi.smartshop.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.mapper.ProductMapper;
import io.github.tawdi.smartshop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProductServiceImpl extends StringCrudServiceImpl<Product, ProductRequestDTO, ProductResponseDTO> implements ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, OrderItemRepository orderItemRepository) {
        super(repository, mapper);
        this.productRepository = repository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional
    public ProductResponseDTO save(ProductRequestDTO requestDto) {
        Product entity = mapper.toEntity(requestDto);
        entity.setReference(generateUniqueReference());
        Product savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    private String generateUniqueReference() {
        String reference;
        do {
            int number = ThreadLocalRandom.current().nextInt(1_000_000);
            reference = String.format("PR-%06d", number);
        } while (productRepository.existsByReference(reference));
        return reference;
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Product product = productRepository.findByIdAndDeleted(id, false).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        boolean isUsedInOrders = orderItemRepository.existsByProductId(id);

        if (isUsedInOrders) {
            product.setDeleted(true);
            productRepository.save(product);
        } else {
            productRepository.delete(product);
        }
    }
}
