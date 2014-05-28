package uap.impl.bq.chart.define;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import uap.bq.chart.generator.parser.DataParser;
import uap.impl.bq.chart.repository.RepositoryFactory;
import uap.impl.bq.chart.service.ChartThemeServiceImpl;
import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.impl.bq.chart.util.DefineUtil;
import uap.itf.bq.chart.IChartThemeService;
import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.define.APIDefine;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.ChartDefineDigest;
import uap.vo.bq.chart.define.EventDefine;
import uap.vo.bq.chart.define.Icon;
import uap.vo.bq.chart.define.Input;
import uap.vo.bq.chart.define.Output;
import uap.vo.bq.chart.define.PropertyDefine;
import uap.vo.bq.chart.define.PropertyDefine.PropertyDefineInfo;
import uap.vo.bq.chart.define.PropertyGroupDefine;
import uap.vo.bq.chart.define.PropertyGroupDefine.PropertyGroupDefineInfo;

import com.ufida.iufo.pub.tools.AppDebug;

public class ChartDefineFactory {

	/**
	 * ��˽�г�Ա����������
	 */
	private Map<String, ChartDefine> chartDefineMap = new TreeMap<String, ChartDefine>(new ChartIndexComparator());
	private List<ChartDefineDigest> chartDefineDigests = null;
	private static ChartDefineFactory instance = new ChartDefineFactory();
	private boolean hasInited = false;
	private Map<String, Object> swapMap = null;

	//����ͳ��ͼ����
	private Map<String, Integer> indexMapTemp = new HashMap<String, Integer>(); //ͳ��ͼ��index����

	private class ChartIndexComparator implements Comparator<String> {
		public int compare(String key1, String key2) {
			if (key1.equals(key2))
				return 0;
			int index1 = indexMapTemp.get(key1);
			int index2 = indexMapTemp.get(key2);
			return index1 - index2;
		}
	}

	//
	/**
	 * ��ȡ����ʵ���ӿ�
	 *
	 * @return ChartDefineFactory �������
	 * @throws IOException
	 * @throws FormatException
	 */
	public static ChartDefineFactory getInstance() {
		return instance;
	}

	//
	/**
	 * ��ȡָ��code��ͳ��ͼ��������
	 *
	 * @param chartDefineCode
	 * @return ChartDefine����
	 * @throws IOException
	 * @throws FormatException
	 */
	public ChartDefine getChartDefine(String chartDefineCode) throws FormatException, IOException {
		if (!hasInited) {
			initialize();
		}
		return chartDefineMap.get(chartDefineCode);
	}

	/**
	 * ��ȡ���ͳ��ͼ��������
	 *
	 * @param chartDefineCodes
	 * @return ChartDefine��������
	 * @throws IOException
	 * @throws FormatException
	 */
	public ChartDefine[] getChartDefines(String[] chartDefineCodes) throws FormatException, IOException {
		if (!hasInited) {
			initialize();
		}
		ArrayList<ChartDefine> objChartDefineList = new ArrayList<ChartDefine>();
		for (int index = 0; index < chartDefineCodes.length; ++index) {
			ChartDefine obj = chartDefineMap.get(chartDefineCodes[index]);
			if (obj != null) {
				objChartDefineList.add(obj);
			}
		}

		return (ChartDefine[]) objChartDefineList.toArray(new ChartDefine[objChartDefineList.size()]);
	}

	/**
	 * ��ȡ��Ҫ��Ϣ
	 *
	 * @return ChartDefineDigest ��������
	 * @throws IOException
	 * @throws FormatException
	 */
	public ChartDefineDigest[] getChartDefineDigests() throws FormatException, IOException {
		if (!hasInited) {
			initialize();
		}
		return (ChartDefineDigest[]) chartDefineDigests.toArray(new ChartDefineDigest[chartDefineDigests.size()]);
	}

	/**
	 * ��ʼ��ͳ��ͼ�����๤��
	 *
	 * @return true,��ʾ��ʼ�����ݳɹ�������ʧ��
	 * @throws IOException
	 * @throws FormatException
	 */
	public boolean initialize() throws FormatException, IOException {
		try {
			hasInited = true;
			// �ȶ�ȡ��������
			IChartThemeService chartThemeSvr = new ChartThemeServiceImpl();// NCLocator.getInstance().lookup(IChartThemeService.class);
			if (null != chartThemeSvr) {
				chartThemeSvr.updateChartThemes();
			}
			// Initialize the memery vaiables .
			// Construct the chartdefines from xml .
			String root = BackEndServerInfoUtil.getServerRootPath();
			boolean flag = generatorChartDefines(root + "/chartDefines");
			generatorChartDefineDigests();
			return flag;
		} catch (Exception e) {
			throw new BusinessRuntimeException("init chart factory failed", e);
		}
	}

	private void generatorChartDefineDigests() {
		chartDefineDigests = new ArrayList<ChartDefineDigest>();
		for (String key : chartDefineMap.keySet())
			chartDefineDigests.add(getChartDefineDigest(chartDefineMap.get(key)));
	}

	/**
	 * �����ļ�·������ͳ��ͼ������������
	 *
	 * @param filePath
	 * @return true,��ʾִ�гɹ�����֮��ʧ��
	 * @throws FormatException
	 * @throws IOException
	 */
	private boolean generatorChartDefines(String filePath) throws FormatException, IOException {
		try {
			File file = new File(filePath);
			if (!file.isDirectory()) {
				return false;
			}
			// Search the Special Directory.
			File[] fileList = file.listFiles();
			for (File xmlFile : fileList) {
				if (!xmlFile.isDirectory()) {
					try {
						ChartDefine objChartDefine = generatorChartDefine(xmlFile);
						if (objChartDefine != null) {
							indexMapTemp.put(objChartDefine.getCode(), objChartDefine.getIndex());

							chartDefineMap.put(objChartDefine.getCode(), objChartDefine);
							//chartDefineDigests.add(getChartDefineDigest(objChartDefine));
						}
					} catch (FormatException e) {
						AppDebug.debug(e.getMessage());
						continue;
						//throw new FormatException("parser chartDefine.xml failed��" + e.getMessage() + ")!", e);
					} catch (Exception e) {
						AppDebug.debug(e.getMessage());
						continue;
						//throw new BusinessRuntimeException("unknown exception" + e.getMessage() + ")!",e);
					}
				} else if (xmlFile.isDirectory()) {
					generatorChartDefines(xmlFile.getAbsolutePath());
				}
			}
			return true;
		} catch (IOException e) {
			throw new IOException("open file failed", e);
		} catch (FormatException e) {
			throw new FormatException("parser chartDefine.xml failed", e);
		} catch (Exception e) {
			throw new BusinessRuntimeException("unknown exception", e);
		}
	}

	public ChartDefine generatorChartDefine(File xmlFile) throws FormatException, ClassNotFoundException {
		try {
			swapMap = new HashMap<String, Object>();
			String fileName = xmlFile.getName();
			if (!xmlFile.isDirectory() && fileName.endsWith("chartDefine.xml")) {
				ChartDefine objChartDefine = parserChartDefine(xmlFile);
				objChartDefine.setChartDefineFilePath(xmlFile.getAbsolutePath());
				swapMap = null;
				return objChartDefine;
			}
		} catch (FormatException e) {
			throw new FormatException("parser chartDefine.xml failed", e);
		} finally {
			swapMap = null;
		}
		return null;
	}

	/**
	 * ��ȡͳ��ͼ��Ҫ��Ϣ����
	 *
	 * @param objChartDefine
	 * @return ChartDefineDigest ����
	 */
	private ChartDefineDigest getChartDefineDigest(ChartDefine objChartDefine) {
		return new ChartDefineDigest(objChartDefine.getIndex(), objChartDefine.getType(), objChartDefine.getCode(),
				objChartDefine.getName(), objChartDefine.getCategory(), objChartDefine.getIcon());
	}

	/**
	 * ����chartDefine��xmlDom������ChartDefine����
	 *
	 * @param xmlFile
	 * @return ChartDefine����
	 * @throws FormatException
	 */
	private ChartDefine parserChartDefine(File xmlFile) throws FormatException, ClassNotFoundException {
		try {

			SAXReader saxReader = new SAXReader();
			Document xmlDoc = saxReader.read(xmlFile);

			if (xmlDoc == null) {
				return null;
			}

			return parserChartDefine(xmlDoc.getRootElement());
		} catch (DocumentException e) {

			throw new FormatException("create chartdefine, xml file load failed" + e.getMessage() + ")!", e);
		} catch (FormatException e) {

			throw new FormatException("create chartdefine node object failed(" + e.getMessage() + ")!", e);
		}
	}

	//
	/**
	 * parserchartDefine�ڵ㣬������ͳ��ͼ�ṹ�ڴ����
	 *
	 * @param elemChartDefine
	 * @return ChartDefine ����
	 */
	private ChartDefine parserChartDefine(Element elemChartDefine) throws FormatException, ClassNotFoundException {
		try {
			if (elemChartDefine == null) {
				return null;
			}

			// Define ChartDefine Class Object .
			ChartDefine.ChartDefineInfo objChartDefine = new ChartDefine.ChartDefineInfo();
			// Get Code �� Name and so on.
			
			
			objChartDefine.width =  DefineUtil.getAttributeValue(elemChartDefine, "width");			
			objChartDefine.height = DefineUtil.getAttributeValue(elemChartDefine, "height");
			
			String sIndex = DefineUtil.getAttributeValue(elemChartDefine, "index");
			if (sIndex != null && !sIndex.isEmpty()) {
				objChartDefine.index = Integer.parseInt(sIndex);
			}
			objChartDefine.code = DefineUtil.getAttributeValue(elemChartDefine, "code");//elemChartDefine.attribute("code").getText();

			objChartDefine.name = ChartUtil.FromLangRes(elemChartDefine, "name");
			//			objChartDefine.name = DefineUtil.getAttributeValue(elemChartDefine,"name");//elemChartDefine.attribute("name").getText();
			objChartDefine.category = ChartUtil.FromLangRes(elemChartDefine, "category");//elemChartDefine.attribute("category").getText();
			objChartDefine.type = ChartUtil.FromLangRes(elemChartDefine, "type");//elemChartDefine.attribute("type").getText();
			// Get PropertyGroups

			List<Element> elementList = elemChartDefine.elements();
			for (Element elemItem : elementList) {
				// Define PropertyGroupDefine Array .

				if (elemItem.getName().equals("propertyGroupDefines")) {

					List<PropertyGroupDefine> propGroupDefines = parserGroupDefines(elemItem);
					if (propGroupDefines != null) {
						objChartDefine.propertyGroupDefines = (PropertyGroupDefine[]) propGroupDefines
								.toArray(new PropertyGroupDefine[propGroupDefines.size()]);
						if (swapMap != null && swapMap.size() != 0) {
							objChartDefine.mapCustomProperty = new HashMap<String, Object>();
							objChartDefine.mapCustomProperty.putAll(swapMap);
						}

					}

				}

				// Read Icon Information .
				if (elemItem.getName().equals("icon")) {

					objChartDefine.icon = parserIconDefine(elemItem, objChartDefine.code);

				}

				if (elemItem.getName().equals("apiDefines")) {
					List<APIDefine> apiList = parserAPIDefines(elemItem);
					if (apiList != null) {
						objChartDefine.apiDefines = (APIDefine[]) apiList.toArray(new APIDefine[apiList.size()]);
					}

				}

				if (elemItem.getName().equals("eventDefines")) {
					List<EventDefine> evtList = parserEventDefines(elemItem);
					if (evtList != null) {
						objChartDefine.eventDefines = (EventDefine[]) evtList.toArray(new EventDefine[evtList.size()]);
					}

				}

				// defalt setting data.
				if (elemItem.getName().equals("defaultSetting")) {

					try {
						DataParser parser = DataParser.getInstance(objChartDefine.code);
						if (parser == null) {
							objChartDefine.defaultSetting = null;
							AppDebug.debug(objChartDefine.code + " chart : defaultSetting = null !");
						} else
							objChartDefine.defaultSetting = parser.parserDefaultSetting(elemItem);
					} catch (ClassNotFoundException e) {
						AppDebug.debug("##(ClassNotFoundException):" + e.getMessage());
						throw e;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}
				
				// no data defalt setting data.
				if (elemItem.getName().equals("nodatadefaultSetting")) {

					try {
						DataParser parser = DataParser.getInstance(objChartDefine.code);
						if (parser == null) {
							objChartDefine.noDataDefaultSetting = null;
							AppDebug.debug(objChartDefine.code + " chart : nodatadefaultSetting = null !");
						} else
							objChartDefine.noDataDefaultSetting = parser.parserDefaultSetting(elemItem);
					} catch (ClassNotFoundException e) {
						AppDebug.debug("##(ClassNotFoundException):" + e.getMessage());
						throw e;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}
				
				
			}

			return new ChartDefine(objChartDefine);
		} catch (FormatException e) {

			throw new BusinessRuntimeException("parser chartdefine node object failed" + e.getMessage() + ")!", e);
		}
	}

	/**
	 * parser propertyGroupDefines �ڵ�,�������������ڴ�ṹ�б�
	 *
	 * @param elemGroupDefines
	 * @return List<PropertyGroupDefine> �������ڴ�ṹ������
	 * @throws FormatException
	 */
	private List<PropertyGroupDefine> parserGroupDefines(Element elemGroupDefines) throws FormatException {
		try {
			String refGroupDefines = DefineUtil.getAttributeValue(elemGroupDefines, "includeGroups");

			List<PropertyGroupDefine> propGroupDefines = new ArrayList<PropertyGroupDefine>();
			List<Element> propertyGroupList = elemGroupDefines.elements();
			for (Element elemItem : propertyGroupList) {
				PropertyGroupDefine chtGroup = null;
				if (elemItem.getName().equals("propertyGroupDefine")) {
					String code = DefineUtil.getAttributeValue(elemItem, "code");
					if (refGroupDefines.indexOf(code) >= 0) {
						PropertyGroupDefine cmmGroup = RepositoryFactory.getInstance().getPropertyRepository()
								.getPropertyGroupDefine(code);
						if (cmmGroup != null) {
							chtGroup = mergePropertyGroupDefine(cmmGroup, elemItem);
						}
					} else {
						chtGroup = DefineUtil.parserGroupDefine(elemItem);
					}

				} else if (elemItem.getName().equals("styleGroupDefine")) {
					chtGroup = DefineUtil.parserStyleGroupDefine(elemItem);

				} else if (elemItem.getName().equals("dataBindingGroupDefine")) {
					chtGroup = DefineUtil.parserDataBindingGroupDefine(elemItem);
				}

				if (chtGroup != null) {
					/* �ּ��û��Զ������� */
					pickupCustomProperty(chtGroup);
					propGroupDefines.add(chtGroup);
				}
			}
			return propGroupDefines;
		} catch (FormatException e) {
			throw new FormatException("parser propertyGroupDefine node object failed" + e.getMessage() + ")!", e);
		} catch (Exception e) {
			throw new FormatException("parser propertyGroupDefine node object failed" + e.getMessage() + ")!", e);
		}
	}

	/**
	 * parser apis �ڵ�,����Api�����ڴ�ṹ�б�
	 *
	 * @param elemApiDefines
	 * @return List<APIDefine> api�ڴ涨�����б�
	 */
	private List<APIDefine> parserAPIDefines(Element elemApiDefines) throws FormatException {
		try {

			ArrayList<APIDefine> apiDefineList = new ArrayList<APIDefine>();
			String includeApis = DefineUtil.getAttributeValue(elemApiDefines, "includeApis");

			if (includeApis != null && !includeApis.isEmpty()) {
				includeApis = includeApis.trim();

				String[] objApiCodes = includeApis.split(",");

				// APIDefine objApiDefine = RepositoryFactory.
				// TODO: �ȴ�ͨ�òֿ��л�ȡ�����õ�API
				for (int index = 0; index < objApiCodes.length; ++index) {
					// RepositoryFactory.getPropertyRepository();
				}
			}
			// TODO: ��chartDefine�б����api�����ȡ��ʵ������
			List<Element> apiList = elemApiDefines.elements();
			for (Element elemApi : apiList) {
				String code = DefineUtil.getAttributeValue(elemApi, "code");
				String nameid = DefineUtil.getAttributeValue(elemApi, "name");
				nameid = nameid.replace("{", "").replace("}", "");
				String name = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0", nameid);
				//				String name = DefineUtil.getAttributeValue(elemApi, "name");
				// parserInput�ڵ�
				List<Input> inputList = new ArrayList<Input>();
				Output output = null;
				List<Element> elemList = elemApi.elements();
				for (Element elemItem : elemList) {
					if (elemItem.getName().equals("input")) {
						inputList.add(new Input(DefineUtil.getAttributeValue(elemItem, "code"), DefineUtil
								.getAttributeValue(elemItem, "name"), DefineUtil.getAttributeValue(elemItem,
								"typeClassName")));
					} else if (elemItem.getName().equals("output")) {
						output = new Output(DefineUtil.getAttributeValue(elemItem, "typeClassName"));
					}

				}
				apiDefineList.add(new APIDefine(code, name, (Input[]) inputList.toArray(new Input[inputList.size()]),
						output));
			}
			return apiDefineList;
		} catch (Exception e) {

			throw new FormatException("parser APIs node object failed" + e.getMessage() + ")!", e);
		}
	}

	/**
	 * parserEvents�ڵ�,�����¼��ڴ�ṹ�б�
	 *
	 * @param elemEvtDefines
	 * @return List<EventDefine> �¼��ڴ�ṹ�б�
	 */
	private List<EventDefine> parserEventDefines(Element elemEvtDefines) throws FormatException {
		ArrayList<EventDefine> eventDefineList = new ArrayList<EventDefine>();
		List<Element> eventList = elemEvtDefines.elements();
		for (Element elemEvent : eventList) {
			String code = DefineUtil.getAttributeValue(elemEvent, "code");
			String name = DefineUtil.getAttributeValue(elemEvent, "name");

			// parserInput�ڵ�
			List<Input> inputArrayList = new ArrayList<Input>();
			List<Element> inputList = elemEvent.elements();
			for (Element elemInput : inputList) {
				inputArrayList
						.add(new Input(DefineUtil.getAttributeValue(elemInput, "code"), DefineUtil.getAttributeValue(
								elemInput, "name"), DefineUtil.getAttributeValue(elemInput, "typeClassName")));
			}
			eventDefineList.add(new EventDefine(code, name, (Input[]) inputArrayList.toArray(new Input[inputArrayList
					.size()])));
		}
		return eventDefineList;
	}

	/**
	 * parser icon �ڵ�,������ͼ���ڴ�ṹ
	 *
	 * @param elemIcon
	 * @param chartDefineCode
	 * @return Icon �ڴ�ṹ����
	 */
	private Icon parserIconDefine(Element elemIcon, String chartDefineCode) {
		Icon icon = null;
		try {

			if (elemIcon != null) {
				Icon.IconInfo objIconInfo = new Icon.IconInfo();
				List<Element> iconList = elemIcon.elements();
				for (Element elemItem : iconList) {
					if (elemItem.getName().equals("littleIcon")) {
						objIconInfo.littleIcon = elemItem.getText();
					} else if (elemItem.getName().equals("bigIcon")) {
						objIconInfo.bigIcon = elemItem.getText();
					} else if (elemItem.getName().equals("mouseOverIcon")) {
						objIconInfo.mouseOverIcon = elemItem.getText();
					} else if (elemItem.getName().equals("pressedIcon")) {
						objIconInfo.pressedIcon = elemItem.getText();
					}
				}

				objIconInfo.chartDefineCode = chartDefineCode;

				icon = new Icon(objIconInfo);
			}
		} catch (Exception exception) {

			throw new BusinessRuntimeException("parser icon object failed" + exception.getMessage() + ")!");
		}
		return icon;
	}

	/**
	 * ����propertyGroup�ڵ��е�Property�����ֵ��ڴ滺�����
	 *
	 * @param elemPropertyGroup
	 * @return Map<String, PropertyDefineInfo>���ֵ仺�����
	 */
	private Map<String, PropertyDefineInfo> groupXmlToDictionary(Element elemPropertyGroup) {
		try {
			HashMap<String, PropertyDefineInfo> dictionary = new HashMap<String, PropertyDefineInfo>();
			List<Element> elemPropertyList = elemPropertyGroup.elements();

			for (Element elemProperty : elemPropertyList) {
				PropertyDefineInfo objPropertyInfo = DefineUtil.parserPropertyDefineInfo(elemProperty);
				if (objPropertyInfo != null) {
					dictionary.put(objPropertyInfo.code, objPropertyInfo);
				}
			}

			return dictionary;
		} catch (Exception exception) {

			throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0",
					"00502004-0062"), exception);
		}
	}

	/**
	 * �ϲ�ָ��������������
	 *
	 * @param comm
	 * @param elemPropertyGroup
	 * @return PropertyGroupDefine,�ϲ�֮��������������
	 */
	private PropertyGroupDefine mergePropertyGroupDefine(PropertyGroupDefine comm, Element elemPropertyGroup) {
		try {
			// Get Basic Information .
			PropertyGroupDefine objPropertyGroupDefine = null;
			PropertyGroupDefine.PropertyGroupDefineInfo objPropertyGroupDefineInfo = new PropertyGroupDefineInfo();

			objPropertyGroupDefineInfo.code = comm.getCode();
			objPropertyGroupDefineInfo.name = comm.getName();
			objPropertyGroupDefineInfo.type = comm.getType();
			objPropertyGroupDefineInfo.editorDefine = comm.getEditorDefine();
			objPropertyGroupDefineInfo.allowRepeat = comm.isRepeatable();
			objPropertyGroupDefineInfo.relativeChange = comm.isRelativeChange();
			Map<String, PropertyDefineInfo> objDictionaty = groupXmlToDictionary(elemPropertyGroup);
			if (objDictionaty == null) {
				return objPropertyGroupDefine;
			}
			List<PropertyDefine> objPropertyDefineList = new ArrayList<PropertyDefine>();
			for (PropertyDefine objProperty : comm.getProperties()) {
				if (!DefineUtil.isHidePropertyDefine(objDictionaty, objProperty.getCode())) {
					objPropertyDefineList.add(objProperty);
				}
				objDictionaty.remove(objProperty.getCode());
			}

			// Take The new Property Of PropertyGroup in ChartDefine To Append
			// The
			// ArrayList .
			Iterator<PropertyDefineInfo> it = (Iterator<PropertyDefineInfo>) objDictionaty.values().iterator();
			while (it.hasNext()) {
				PropertyDefineInfo obj = (PropertyDefineInfo) it.next();
				objPropertyDefineList.add(new PropertyDefine(obj));
			}

			// Construct The New PropertyGroupDefine Object.
			if (objPropertyDefineList != null && objPropertyDefineList.size() > 0) {
				objPropertyGroupDefineInfo.properties = (PropertyDefine[]) objPropertyDefineList
						.toArray(new PropertyDefine[objPropertyDefineList.size()]);

				objPropertyGroupDefine = new PropertyGroupDefine(objPropertyGroupDefineInfo);
			}

			return objPropertyGroupDefine;
		} catch (Exception exception) {
			throw new BusinessRuntimeException("merge propertyGroup failed" + exception.getMessage() + ")!");
		}
	}

	protected void pickupCustomProperty(PropertyGroupDefine grp) {
		if (grp == null)
			return;
		if (grp.getProperties() != null)
			for (PropertyDefine df : grp.getProperties()) {
				if (!df.isGenerate()) {
					swapMap.put(df.getCode(), df);
				}
			}
	}

	// -----------------------------------------------
	/*
	 * public static void main(String[] args) { ChartDefine define; try { double
	 * src_t = System.currentTimeMillis(); define =
	 * ChartDefineFactory.getInstance().getChartDefine( "MSColumn2D");
	 * System.out.println("@@@@@@@ �ܹ���ʱ �� "+ (System.currentTimeMillis() -
	 * src_t)); // if (null != define) { // List<ChartDataset> datasets =
	 * define.getDefaultSetting() // .getDatasets(); // if (datasets.size() > 0)
	 * { // src_t = System.currentTimeMillis(); // ChartDataset dataset =
	 * datasets.get(0); //
	 * //$$System.out.println(dataset.toStringBuilder().toString()); //
	 * //$$System.out.println("��λ�ý�����\n"); // dataset.swap(1, 5); //
	 * //$$System.out.println(dataset.toStringBuilder().toString()); //
	 * //$$System.out.println("��λ�ý�����ʱ �� " // + (System.currentTimeMillis() -
	 * src_t)); // } // } } catch (FormatException e) {
	 *
	 * e.printStackTrace(); } catch (IOException e) {
	 *
	 * e.printStackTrace(); }
	 *
	 * }//
	 */

	//	public static void main (String[] args) throws FormatException, IOException{
	//		ChartDefineFactory i1 = ChartDefineFactory.getInstance();
	//		ChartDefineFactory i2 = ChartDefineFactory.getInstance();
	//		ChartDefineFactory i3 = ChartDefineFactory.getInstance();
	//		AppDebug.debug("instance1 =" + i1 + ", instance2 = " + i2 + ", instance3 = " + i3);
	//	}
}