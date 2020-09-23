package br.com.faht.emiteai.order.report.csv;

import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import br.com.faht.emiteai.order.model.entity.Order;

@Component
public class OrderReportWriter {

	public void write(PrintWriter writer, List<Order> orders) {

		try {

			ColumnPositionMappingStrategy<Order> mapStrategy = new ColumnPositionMappingStrategy<>();
			mapStrategy.setType(Order.class);

			String[] columns = new String[] { "orderNumber", "total" };
			mapStrategy.setColumnMapping(columns);

			StatefulBeanToCsv<Order> btcsv = new StatefulBeanToCsvBuilder<Order>(writer)
					.withQuotechar(NO_QUOTE_CHARACTER).withMappingStrategy(mapStrategy).withSeparator(';')
					.build();

			btcsv.write(orders);

		} catch (CsvException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
