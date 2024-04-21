package com.transaction.repo;
import org.springframework.data.repository.CrudRepository;
import com.transaction.entity.Product;


public interface ProductRepo extends CrudRepository<Product,Long> {
    
}
