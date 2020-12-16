package com.app.vendas.core.dao.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.app.vendas.core.repository.OrderItemRepository;
import com.app.vendas.domain.OrderItem;

@Repository("OrderItemDAO")
public class OrderItemDAO extends AbstractDAO<OrderItem, Integer> {

	@Autowired
	private OrderItemRepository repository;

	@Transactional
	public List<OrderItem> saveAllOrderItens(List<OrderItem> entity) {
		List<OrderItem> response = (List<OrderItem>) repository.saveAll(entity);
		return response;
	}

}
