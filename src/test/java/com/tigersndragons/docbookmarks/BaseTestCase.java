package com.tigersndragons.docbookmarks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import javax.xml.bind.DatatypeConverter;

import com.tigersndragons.docbookmarks.model.Entity;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
		TestContextConfiguration.class
})
@WebAppConfiguration
//@ContextConfiguration(locations={"testContext.xml"})
public abstract class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected SessionFactory sessionFactory;
	
//	@Autowired
//	protected JdbcTemplate jdbcTemplate;
//	@Autowired
//	protected Unmarshaller marshaller;
	
	@After
	public void flushSession(){
		sessionFactory.getCurrentSession().flush();
	}

	protected void printResults(ResultRow row){
		printResults(Arrays.asList(row));
	}
	protected void printResults(List<ResultRow> rows){
		System.out.println("");
		ResultRowPrinter printer = new ResultRowPrinter(rows);
		printer.printAsTable();
		System.out.println("");
	}
	@SuppressWarnings("unchecked")
	protected <T extends Entity> void printResults(Class<T> clazz){
		List<T> results = sessionFactory.getCurrentSession().createCriteria(clazz).list();
		System.out.println("");
		System.out.println("All results of type: " + clazz);
		ResultRowPrinter printer = new ResultRowPrinter(getPropertyNameList(clazz));
		for(T obj:results){
			Map<String, Object> propertyValues = buildPropertyValueMap(obj);
			printer.addRow(new ResultRow(propertyValues));
		}
		printer.printAsTable();
		System.out.println("");
		//sessionFactory.getCurrentSession().clear();
	}
	
	protected Map<String, Object> buildPropertyValueMap(Object obj){
		Map<String, Object> valueMap = new HashMap<String, Object>();
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		for(PropertyDescriptor pd:wrapper.getPropertyDescriptors()){
			if(pd.getReadMethod() == null || pd.getWriteMethod() == null){
				continue;
			}
			valueMap.put(pd.getName(), wrapper.getPropertyValue(pd.getName()));
		}
		return valueMap;
	}
	
	protected <T> String[] getPropertyNameList(Class<T> clazz){
		BeanWrapper wrapper = new BeanWrapperImpl(clazz);
		List<String> propertyNameList = new LinkedList<String>();
		for(PropertyDescriptor pd:wrapper.getPropertyDescriptors()){
			if(pd.getReadMethod() == null || pd.getWriteMethod() == null){
				continue;
			}
			propertyNameList.add(pd.getName());
		}
		//order by Ids then hibernate order:
		for(String propName:sessionFactory.getClassMetadata(clazz).getPropertyNames()){
			propertyNameList.remove(propName);
			propertyNameList.add(propName);
		}
		return propertyNameList.toArray(new String[]{});
	}

	protected List<ResultRow> queryRows(String query, Object...args){
		flushSession(); //flush so hibernate objects are in DB ready to query
		List<ResultRow> dbRows = jdbcTemplate.query(query, new ResultRowRowMapper(), args);
		logger.debug("{}",dbRows);
		return dbRows;
	}
	
	protected void assertRowsEqual(ResultRow expected, List<ResultRow> dbRows) {
		assertRowsEqual(Arrays.asList(expected), dbRows);
	}
	protected void assertRowsEqual(List<ResultRow> expected, List<ResultRow> dbRows){
		assertEquals("Row counts do not match ", expected.size(), dbRows.size());
		for(int i=0; i<expected.size(); i++){
			assertRowsEqual(expected.get(i), dbRows.get(i), i);
		}
	}
	protected void assertRowsEqual(ResultRow expected, ResultRow actual){
		assertRowsEqual(expected, actual, 0);
	}
	protected void assertRowsEqual(ResultRow expected, ResultRow actual, int rowNum){
		for(String columnName:expected.getColumnNames()){
			assertColumnsEqual(expected, actual, columnName, rowNum);
		}
	}
	protected void assertColumnsEqual(ResultRow expected, ResultRow actual, String columnName, int rowNum){
		//TODO: fix this
		if (columnName.equals("CREATE_TIMESTAMP") || columnName.equals("UPDATE_TIMESTAMP"))
			return;
		Object expectedValue = expected.getValue(columnName);
		Object actualValue = actual.getValue(columnName);
		assertTrue("Column " + columnName + " not found in result row " + actual.getColumnNames(), actual.hasValue(columnName));
		assertEquals("Values do not match for column " + columnName + " at row " + rowNum, expectedValue, actualValue);
	}
	
	protected Date getEndOfTime(){
		return new Date();//DatatypeConverter.parseDate("2090-12-31").getTime();
	}
	
	private static class ResultRowRowMapper implements RowMapper<ResultRow> {
		public ResultRow mapRow(ResultSet rs, int arg1) throws SQLException {
			ResultRow resultRow = new ResultRow();
			for(int i=1; i<=rs.getMetaData().getColumnCount(); i++){
				String columnName = rs.getMetaData().getColumnLabel(i);
				Object value = rs.getObject(i);
				resultRow.addValue(columnName, value);
			}
			return resultRow;
		}
	}
	
	public void assertInDB(List<ResultRow> expected, List<ResultRow> result)
	{
		assertEquals("Expected rows: ", expected.size(), result.size());
		String value = assertRowsInDB(expected, result);
		assertNull(value, value);
	}
	private String assertRowsInDB(List<ResultRow> expected, List<ResultRow> result){
		for(ResultRow actual: result)
		{
			String value = hasColumnNames(expected, actual);
			if(value != null)
				return value;
			value = hasColumnValues(expected, actual);
			if(value != null)
				return value;
		}
		return null;
	}
	private String hasColumnNames(List<ResultRow> expected,ResultRow actual){
		for(String correctNames: expected.get(0).getColumnNames()){
			if(!actual.hasValue(correctNames)){
				return "Column " + correctNames + " not found in result row " + actual.getColumnNames();
			}
		}
		return null;
	}
	private String hasColumnValues(List<ResultRow> expected, ResultRow actual){
		int correctElements;
		String message = "";
		for(ResultRow row: expected){
			correctElements = 0;
			for(String colNam: row.getColumnNames())
			{
				if(actual.getValue(colNam).equals(row.getValue(colNam)))
					correctElements++;
				else
				{
					message += "Values do not match for column " + colNam +". Expected: " + row.getValue(colNam) + ". Actual: " + actual.getValue(colNam) + "\n";
					break;
				}
			}
			if(correctElements == row.getColumnNames().size())
				return null;
		}
		return message;
	}
	public void assertExpectedInDB(List<ResultRow> expected, List<ResultRow> result)
	{
		assertEquals("Expected rows: ", expected.size(), result.size());
		String value = assertExpectedRowsInDB(expected, result);
		assertNull(value, value);
	}
	private String assertExpectedRowsInDB(List<ResultRow> expected, List<ResultRow> result){
		int esize = expected.size();
		for (int i =0; i < esize; i++)
		{
			String value = hasExpectedColumnNames(expected.get(i), result.get(i));
			if(value != null)
				return value;
			value = hasExpectedColumnValues(expected.get(i), result.get(i));
			if(value != null)
				return value;
		}
		return null;
	}
	private String hasExpectedColumnNames(ResultRow expected,ResultRow actual){
		for(String correctNames: expected.getColumnNames()){
			if(!actual.hasValue(correctNames)){
				return "Column " + correctNames + " not found in result row " + actual.getColumnNames();
			}
		}
		return null;
	}
	private String hasExpectedColumnValues(ResultRow expected, ResultRow actual){
		int correctElements;
		String message = "";
		correctElements = 0;
		for(String colNam: expected.getColumnNames())
		{
			if(actual.getValue(colNam).equals(expected.getValue(colNam)))
				correctElements++;
			else
			{
				message += "Values do not match for column " + colNam +". Expected: " + expected.getValue(colNam) + ". Actual: " + actual.getValue(colNam) + "\n";
				break;
			}
		}
		if(correctElements == expected.getColumnNames().size())
			return null;
		return message;
	}	
}
