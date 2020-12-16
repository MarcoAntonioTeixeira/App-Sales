package com.app.vendas.core.business.order;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.vendas.core.IStrategy;
import com.app.vendas.core.application.Result;
import com.app.vendas.core.dao.Impl.OrderDAO;
import com.app.vendas.domain.Order;



@Component
public class UpdateOrderStatus implements IStrategy<Order> {

	@Autowired
	private OrderDAO orderDAO;

	@Override
	@Transactional
	public Result<Order> process(Order aEntity, Result<Order> response) {

		if (aEntity != null && aEntity.getId() != null) {

			Optional<Order> orderId = orderDAO.findById(aEntity.getId());

			if (orderId.isPresent()) {			
				orderId.get().setStatus(aEntity.getStatus());
				orderDAO.update(orderId.get());
				response.add(orderId.get());
				return response;
			}else 
				response.setMsg("Não foi possível atualizar o status do pdeido, envie um status válido." );
			    return response;
			}

		return null;
	}

}
