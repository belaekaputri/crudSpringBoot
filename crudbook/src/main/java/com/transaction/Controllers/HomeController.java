package com.transaction.Controllers;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.transaction.entity.Product;
import com.transaction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.PostMapping;

@Controller  
@RequestMapping("/") 


public class HomeController {
    
    @Autowired 
   
    private ProductService productService;
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeController.class.getName());

    @GetMapping 
    public String welcome(Model model){ 

        String messages = "Welcome"; 
        model.addAttribute("msg", messages);
        model.addAttribute("products", productService.findAll());
        return "index"; 
    }
    
    @GetMapping("/add")
    public String add(Model model){
    model.addAttribute("product", new Product()); 
    return ("/add"); 
    }

    @PostMapping("/save")
    public String save( @Valid Product product, Model model,BindingResult errors){
        LOGGER.info(product.toString());
        if (errors.hasErrors())
			return "/add";
		LOGGER.info(errors.toString());
		LOGGER.info("" + errors.hasErrors());
		LOGGER.info("" + errors.hasGlobalErrors());
        try {
            productService.addProduct(product); 
            return "redirect:/";
		} catch (DataAccessException e) {
		 	errors.reject("error.object", e.getMessage());
			LOGGER.error(e.getMessage());
			return "/add";
		}
     
    }
   
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
       productService.deleteById(id);
       return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
       model.addAttribute("product",productService.findById(id));

       return "edit"; 
    }

    @PostMapping("/update")
    public String update(Product product, Model model){
       productService.updateProduct(product);
       
        return "redirect:/";
    }
    
    
}
