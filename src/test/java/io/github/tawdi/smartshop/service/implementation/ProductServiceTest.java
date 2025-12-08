package io.github.tawdi.smartshop.service.implementation;

import io.github.tawdi.smartshop.domain.entity.Product;
import io.github.tawdi.smartshop.domain.repository.OrderItemRepository;
import io.github.tawdi.smartshop.domain.repository.ProductRepository;
import io.github.tawdi.smartshop.dto.product.ProductRequestDTO;
import io.github.tawdi.smartshop.dto.product.ProductResponseDTO;
import io.github.tawdi.smartshop.exception.ResourceNotFoundException;
import io.github.tawdi.smartshop.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private static final String EXISTING_ID = "prod-001";
    private static final String NON_EXISTING_ID = "prod-999";

    private ProductRequestDTO validRequestDTO;
    private Product productEntity;
    private Product savedProductWithReference;

    @BeforeEach
    void setUp() {
        validRequestDTO = ProductRequestDTO.builder()
                .name("Souris Logitech MX Master 3")
                .description("Souris ergonomique sans fil")
                .priceHT(new BigDecimal("1200.00"))
                .stock(75)
                .build();

        productEntity = new Product();
        productEntity.setId(EXISTING_ID);
        productEntity.setName("Souris Logitech MX Master 3");
        productEntity.setPriceHT(new BigDecimal("1200.00"));
        productEntity.setStock(75);
        productEntity.setDeleted(false);

        savedProductWithReference = new Product();
        savedProductWithReference.setReference("PR-048512");
        savedProductWithReference.setId(EXISTING_ID);
        savedProductWithReference.setName("Souris Logitech MX Master 3");
        savedProductWithReference.setPriceHT(new BigDecimal("1200.00"));
        savedProductWithReference.setStock(75);
        savedProductWithReference.setDeleted(false);
    }

    @Test
    void shouldGenerateUniqueReferenceAndSaveProduct(){

        when(productMapper.toEntity(validRequestDTO)).thenReturn(productEntity);
        when(productRepository.existsByReference(anyString())).thenReturn(false);
        when(productRepository.save(productEntity)).thenReturn(savedProductWithReference);
        when(productMapper.toDto(any(Product.class))).thenReturn(
                ProductResponseDTO.builder()
                        .id(EXISTING_ID)
                        .reference("PR-048512")
                        .name("Souris Logitech MX Master 3")
                        .priceHT(new BigDecimal("1200.00"))
                        .stock(75)
                        .build()
        );


        ProductResponseDTO result = productService.save(validRequestDTO);


        assertNotNull(result);
        assertTrue(result.getReference().matches("^PR-[0-9]{6}$"));
        assertEquals("Souris Logitech MX Master 3", result.getName());
        verify(productRepository, atLeastOnce()).existsByReference(anyString());
    }

    @Test
    void shouldSoftDeleteWhenProductIsUsedInOrder() {
        when(productRepository.findByIdAndDeleted(EXISTING_ID, false))
                .thenReturn(Optional.of(productEntity));
        when(orderItemRepository.existsByProductId(EXISTING_ID)).thenReturn(true);

        productService.deleteById(EXISTING_ID);

        assertTrue(productEntity.getDeleted());
        verify(productRepository).save(productEntity);
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    void shouldHardDeleteWhenProductNotUsedInAnyOrder() {
        when(productRepository.findByIdAndDeleted(EXISTING_ID, false))
                .thenReturn(Optional.of(productEntity));
        when(orderItemRepository.existsByProductId(EXISTING_ID)).thenReturn(false);

        productService.deleteById(EXISTING_ID);

        verify(productRepository).delete(productEntity);
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    void shouldThrowNotFoundWhenProductNotFoundOrAlreadySoftDeleted() {
        when(productRepository.findByIdAndDeleted(NON_EXISTING_ID, false))
                .thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.deleteById(NON_EXISTING_ID)
        );

        assertEquals("Product not found with id: " + NON_EXISTING_ID, thrown.getMessage());
        verify(orderItemRepository, never()).existsByProductId(any());
    }
    @Test
    void shouldFindProductByIdUsingInheritedMethod() {
        when(productRepository.findById(EXISTING_ID)).thenReturn(Optional.of(savedProductWithReference));
        when(productMapper.toDto(savedProductWithReference)).thenReturn(
                ProductResponseDTO.builder()
                        .id(EXISTING_ID)
                        .reference("PR-048512")
                        .name("Souris Logitech MX Master 3")
                        .build()
        );

        ProductResponseDTO result = productService.findById(EXISTING_ID);

        assertEquals("PR-048512", result.getReference());
    }
}
