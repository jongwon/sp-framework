package com.sp.util;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

import static java.lang.String.format;

/**
 * 테스트 클래스를 위한 것임
 *
 */
public class AbstractTestClass {

	protected static FileSystemXmlApplicationContext ctx;

	/**
	 * initialize test class
	 *
	 * @param type dev / test / product
	 */
	protected static void init(String type) {
		ctx = new FileSystemXmlApplicationContext(
				format("file://%s/.soreply/application-%s.xml",
						System.getProperty("user.home"), type));
	}

	/**
	 * initialize test class
	 *
	 * @param alias  project alias
	 * @param type  dev / test / product
	 */
	protected static void init(String alias, String type) {
		ctx = new FileSystemXmlApplicationContext(
				format("file://%s/.%s/application-%s.xml",
						System.getProperty("user.home"), alias, type));
	}

}