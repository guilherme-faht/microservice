package br.com.faht.emiteai.order.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faht.emiteai.order.model.dto.OrderDto;
import br.com.faht.emiteai.order.model.entity.Order;
import br.com.faht.emiteai.order.report.csv.OrderReportWriter;
import br.com.faht.emiteai.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private final OrderService orderService;
	private final OrderReportWriter orderReportWriter;
	
	@Autowired
	public OrderController(OrderService orderService, OrderReportWriter orderReportWriter) {
		this.orderService = orderService;
		this.orderReportWriter = orderReportWriter;
	}
	
	@PostMapping("/insert")
	public ResponseEntity<OrderDto> insertOrder(@RequestBody @Valid OrderDto orderDto) {
		Order order = orderService.insertOrder(Order.fromOrderDto(orderDto));
		return ResponseEntity.ok(OrderDto.fromOrder(order));
	}
	
	@GetMapping("/report")
	public void report(HttpServletResponse response) throws IOException {
		List<Order> orders = orderService.findAllByTotalGreaterThanEqual(500.00);	
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment;filename=report.csv");
		orderReportWriter.write(response.getWriter(), orders);
	}
}
