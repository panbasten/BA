package com.flywet.platform.bi.smart;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class PdmReader {
	private static SAXReader reader = new SAXReader();

	private static Document doc = null;

	private static String mddir = "d:/tmp/";

	private static String PRE = "";// 生成文件的文件名前缀 module-

	public static void main(String[] args) {

		String filename = "D:/VALUATION.pdm";

		createModelXml(filename, "system", false);

	}

	private static Element getPrimaryKeyOfId(String id) {

		List l = doc
				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table/c:Keys/o:Key");

		for (Object obj : l) {

			Element e = (Element) obj;

			if (id.equals(e.attributeValue("Id"))) {

				return e;

			}

		}

		return null;

	}

	private static String getColumnFieldOfId(String id) {

		List l = doc
				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table/c:Columns/o:Column");

		for (Object obj : l) {

			Element e = (Element) obj;

			if (id.equals(e.attributeValue("Id"))) {

				return e.element("Code").getTextTrim();

			}

		}

		return null;

	}

	private static boolean isPrimaryKey(Element e, String id) {

		boolean rtn = false;

		if (e == null) {

			return false;

		}

		if (id.equals(e.element("Key").attributeValue("Ref"))) {

			rtn = true;

		}

		return rtn;

	}

	private static List<Element> getReferences(String tableId) {

		List<Element> rtn = new ArrayList<Element>();

		List l = doc
				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:References/o:Reference");// 得到所有的表

		for (Object obj : l) {

			Element e = (Element) obj;

			if (tableId.equals(e.element("ChildTable").element("Table")
					.attributeValue("Ref"))) {

				rtn.add(e);

			}

		}

		return rtn;

	}

	private static Element getTableByPDMOfId(String tableId) {

		List l = doc
				.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table");

		for (Object obj : l) {

			Element e = (Element) obj;

			if (tableId.equals(e.attributeValue("Id"))) {

				return e;

			}

		}

		return null;

	}

	/**
	 * 
	 * 将 PowerDesginser 15 文件 （pdm 文件）生成gap model xml 文件 生成的文件格式如下：如 <model>
	 * 
	 * <table name="GAP_MENUROLE_ACCOUNT" label="GAP_MENUROLE_ACCOUNT">
	 * 
	 * <column field="SYSID" type="NUMBER" property="sysid" label="SYSID"/>
	 * 
	 * <column field="ROLE_ID" type="NUMBER" property="role_id"
	 * 
	 * label="ROLE_ID"/> <column field="ACCOUNT_ID" type="NUMBER"
	 * 
	 * property="account_id" desc="ACCOUNT_ID"/> <unique
	 * 
	 * name="U_MENUROLE_ACCOUNT_ROLE_ID_ACCOUNT_ID" field="ROLE_ID,ACCOUNT_ID"/>
	 * 
	 * <primary name="PK_MENUROLE_ACCOUNT" field="SYSID"/> <foreign
	 * 
	 * name="FK_ROLE_ACCOUNT_ROLE_ID" field="ROLE_ID" reference="GAP_MENUROLE"
	 * 
	 * referField="SYSID"/> <foreign name="FK_ROLE_ACCOUNT_ACCOUNT_ID"
	 * 
	 * field="ACCOUNT_ID" reference="GAP_ACCOUNTINFO" referField="SYSID"/>
	 * 
	 * </table>
	 * 
	 * </model>
	 * 
	 * 
	 * 
	 * @param filename
	 * 
	 *            要解析的pdm文件
	 * 
	 * @param createfile
	 * 
	 *            　paginateFile 为 false 时，所生成的文件名字,当 paginateFile = true
	 * 
	 *            时，createfile 为 null
	 * 
	 * @param paginateFile
	 * 
	 *            是否根据表生成对应的文件（一个表生成一个文件，文件名为表的名字 + model.xml) true 表示一个表生成一个
	 * 
	 *            model.xml 文件，false 表示，将pdm生成一个大文件
	 */

	public static void createModelXml(String filename, String createfile,
			boolean paginateFile) {

		try {

			doc = reader.read(new FileReader(filename));

			List l = doc
					.selectNodes("Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table");// 得到所有的表

			Document wdoc = null;

			XMLWriter writer = null;

			Element root = null;

			if (paginateFile == false) {

				wdoc = DocumentHelper.createDocument();

				// root = wdoc.addElement("model","http://model.ehtsoft.com");

				// root.addAttribute("xmlns:xsi",

				// "http://www.w3.org/2001/XMLSchema-instance");

				// root.addAttribute("xsi:schemaLocation",

				// "http://model.ehtsoft.com schema.xsd");

				root = wdoc.addElement("model");

				root.addAttribute("xmlns:xsi",
						"http://www.w3.org/2001/XMLSchema-instance");

				root.addAttribute("xsi:noNamespaceSchemaLocation", "schema.xsd");

			}

			for (Object o : l) {

				Element pdmtable = (Element) o;

				if (paginateFile == true) {

					wdoc = DocumentHelper.createDocument();

					// root =

					// wdoc.addElement("model","http://model.ehtsoft.com");

					// root.addAttribute("xmlns:xsi",

					// "http://www.w3.org/2001/XMLSchema-instance");

					// root.addAttribute("xsi:schemaLocation",

					// "http://model.ehtsoft.com schema.xsd");

					root = wdoc.addElement("model");

					root.addAttribute("xmlns:xsi",
							"http://www.w3.org/2001/XMLSchema-instance");

					root.addAttribute("xsi:noNamespaceSchemaLocation",
							"schema.xsd");

				}

				root.addComment(pdmtable.element("Name").getTextTrim() + " - "
						+ pdmtable.elementText("Comment"));

				Element table = root.addElement("table");

				String xmlfilename = pdmtable.element("Code").getTextTrim();

				try {

					table.addAttribute("name", pdmtable.element("Code")
							.getTextTrim());// table

					// name

					table.addAttribute("label",
							pdmtable.element("Comment") != null ? pdmtable
									.element("Comment")

									.getTextTrim() : ""); // table description ：
															// Name

					Element columns = pdmtable.element("Columns");

					if (columns == null) {

						throw new RuntimeException(pdmtable.element("Code")
								.getTextTrim() + "表在PDM中设置错误");

					}

					for (Object obj : columns.elements()) {

						Element pdmcolumn = (Element) obj;

						Element col = table.addElement("column");

						col.addAttribute("field", pdmcolumn.element("Code")
								.getTextTrim());

						col.addAttribute("type", getTypeString(pdmcolumn
								.element("DataType").getTextTrim()));

						if (pdmcolumn.element("Length") != null) {

							col.addAttribute("length",
									pdmcolumn.element("Length").getTextTrim());

						}

						if (null != pdmcolumn.element("Precision")) {

							col.addAttribute("precision",
									pdmcolumn.element("Precision")
											.getTextTrim());

						}

						col.addAttribute("property", pdmcolumn.element("Code")
								.getTextTrim().toLowerCase());

						col.addAttribute("label", pdmcolumn.element("Name")
								.getTextTrim());

						if (pdmcolumn.element("Mandatory") != null

								&& "1".equals(pdmcolumn.element("Mandatory")
										.getTextTrim())) {

							col.addAttribute("required", "true");

						}

						if (pdmcolumn.element("Comment") != null) {

							col.addAttribute("comment",
									pdmcolumn.element("Comment").getTextTrim());

						}

					}

					Element key = pdmtable.element("Keys");

					if (key != null) {

						List keys = key.elements();

						for (Object obj : keys) {

							Element pdmkey = (Element) obj;

							if (!isPrimaryKey(pdmtable.element("PrimaryKey"),
									pdmkey.attributeValue("Id"))) {

								Element unique = table.addElement("unique");

								unique.addAttribute("name",
										pdmkey.element("Code").getTextTrim());

								Element fields = pdmkey.element("Key.Columns");

								List cols = fields.elements("Column");

								String uniqueField = "";

								for (Object c : cols) {

									uniqueField += getColumnFieldOfId(((Element) c)
											.attributeValue("Ref")) + ",";

								}

								if (uniqueField.length() > 0) {

									uniqueField = uniqueField.substring(0,
											uniqueField.length() - 1);

								}

								unique.addAttribute("field", uniqueField);

							}

						}

					}

					Element primarykey = pdmtable.element("PrimaryKey");

					if (primarykey != null) {

						Element pkfields = getPrimaryKeyOfId(primarykey
								.element("Key").attributeValue("Ref"));

						Element fields = pkfields.element("Key.Columns");

						if (fields != null) {

							Element primary = table.addElement("primary");

							primary.addAttribute("name",
									pkfields.element("Code").getTextTrim());

							List cols = fields.elements("Column");

							String pkField = "";

							for (Object c : cols) {

								pkField += getColumnFieldOfId(((Element) c)
										.attributeValue("Ref")) + ",";

							}

							if (pkField.length() > 0) {

								pkField = pkField.substring(0,
										pkField.length() - 1);

							}

							primary.addAttribute("field", pkField);

						} else {

							System.out.println("注意：表 " + xmlfilename + " 没有主键");

							System.out.println("文件 filename 中，表 " + xmlfilename
									+ "设计的有问题，请检查该表");

						}

					}

					List<Element> refertable = getReferences(pdmtable
							.attributeValue("Id"));

					if (refertable != null && refertable.size() > 0) {// foreign

						// key

						for (Element e : refertable) {

							Element foreign = table.addElement("foreign");

							// foreign name

							foreign.addAttribute("name",
									e.element("ForeignKeyConstraintName")
											.getTextTrim());

							// foreign column field code

							String selffield = e.element("Joins")
									.element("ReferenceJoin")
									.element("Object2")

									.element("Column").attributeValue("Ref");

							foreign.addAttribute("field",
									getColumnFieldOfId(selffield));

							// foreign reference table name

							Element tableelement = getTableByPDMOfId(e
									.element("ParentTable").element("Table")

									.attributeValue("Ref"));

							foreign.addAttribute("reference", tableelement
									.element("Code").getTextTrim());

							// foreign reference table's column field code

							String referfiled = e.element("Joins")
									.element("ReferenceJoin")
									.element("Object1")

									.element("Column").attributeValue("Ref");

							foreign.addAttribute("referField",
									getColumnFieldOfId(referfiled));

						}

					}

					/**
					 * 
					 * 生成 XML 文件 分别生成对应的表的xml中
					 */

					if (paginateFile == true) {

						writer = new XMLWriter(new FileWriter(mddir + PRE
								+ xmlfilename.toLowerCase() + "-model.xml"),

						new OutputFormat("  ", true));

						writer.write(wdoc);

						writer.flush();

						writer.close();

					}

				} catch (Exception ex) {

					ex.printStackTrace();

				}

			}

			/**
			 * 
			 * 生成 XML 文件 生成一个文件中
			 */

			if (paginateFile == false) {

				writer = new XMLWriter(new FileWriter(mddir + PRE + createfile
						+ "-model.xml"), new OutputFormat("  ",

				true));

				writer.write(wdoc);

				writer.flush();

				writer.close();

			}

			System.out.println("完成");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * 
	 * <xs:enumeration value="string"/> <xs:enumeration value="boolean"/>
	 * 
	 * <xs:enumeration value="long"/> <xs:enumeration value="integer"/>
	 * 
	 * <xs:enumeration value="float"/> <xs:enumeration value="double"/>
	 * 
	 * <xs:enumeration value="date"/> <xs:enumeration value="binary"/>
	 * 
	 * 
	 * 
	 * @param value
	 * 
	 * @return
	 */

	private static String getTypeString(String value) {

		if (value != null) {

			if (value.toUpperCase().contains("CHAR")
					|| value.toUpperCase().contains("CLOB")) {

				return "string";

			}

			if (value.toUpperCase().contains("TINYINT")
					|| value.toUpperCase().contains("BOOL")) {

				return "boolean";

			}

			if (value.toUpperCase().contains("BIGINT")) {

				return "long";

			}

			if (value.toUpperCase().contains("FLOAT")) {

				return "float";

			}

			if (value.toUpperCase().contains("DOUBLE")
					|| value.toUpperCase().matches("DECIMAL\\(\\d+,[1-9]\\)")

					|| value.toUpperCase().matches("NUMERIC\\(\\d+,[1-9]\\)")

					|| value.toUpperCase().matches("NUMBER\\(\\d+,[1-9]\\)")) {

				return "double";

			}

			if (value.toUpperCase().contains("TIME")
					|| value.toUpperCase().contains("DATE")) {

				return "date";

			}

			if (value.toUpperCase().contains("BLOB")
					|| value.toUpperCase().contains("BINARY")) {

				return "binary";

			}

			// 放在最后

			if (!value.toUpperCase().matches("DECIMAL\\(\\d+,[1-9]\\)")

			&& !value.toUpperCase().matches("NUMERIC\\(\\d+,[1-9]\\)")

			&& !value.toUpperCase().matches("NUMBER\\(\\d+,[1-9]\\)")
					|| value.toUpperCase().contains("INT")

					|| value.toUpperCase().contains("YEAR")) {

				return "integer";

			}

		}

		return "string";

	}
}
