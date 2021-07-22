package com.qa.util;

import jxl.read.biff.BiffException;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class Dataprovider {
	@DataProvider(name = "dp")
	public static Iterator<Object[]> getDataByTestMethodName(Method method) throws BiffException, IOException {
	return new ExcelDataProvider(method.getName());
	}
}
