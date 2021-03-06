package com.app.sales.core.business.product;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.sales.core.IStrategy;
import com.app.sales.core.application.Result;
import com.app.sales.core.dao.Impl.ProductDAO;
import com.app.sales.domain.Product;

@Component
public class FindByProduct implements IStrategy<Product>{
	
	@Autowired
	private  ProductDAO productDAO ;

	@Override
	public Result<Product> process(Product aEntity, Result<Product> response) {
     
		Optional<Product> productOptional = Optional.ofNullable(aEntity);
		if (!productOptional.isPresent()) {
			response.setMsg("Digite uma busca válida");
			return response;
		}          
	               
		Optional<Product> produOptional = Optional.ofNullable(aEntity);
        if(produOptional.isPresent() && produOptional.get().getId() != null) {
			Optional<Product> producitId = productDAO.findById(aEntity.getId());
			if(producitId.isPresent()) {
				response.add(producitId.get());
				return response;
			}else {
				response.setMsg("Não foi encontrado cadastrado desse Produto");
				return response;
			}
        }
		return null;
      
    }

}


