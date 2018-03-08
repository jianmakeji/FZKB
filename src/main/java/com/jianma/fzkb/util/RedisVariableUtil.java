package com.jianma.fzkb.util;

import java.util.HashMap;
import java.util.Map;

public class RedisVariableUtil {
	
	public final static String DIVISION_CHAR = ":";
	
	public final static String CATEGORY_PREFIX = "category"; //品类
	public final static String STYLE1_PREFIX = "style1";  //简单--复杂
	public final static String STYLE2_PREFIX = "style2";  //硬朗--圆润
	public final static String STYLE3_PREFIX = "style3";  //冷酷--温暖
	
	public final static String M_CATEGORY_PREFIX = "m_category"; //品类
	public final static String M_STYLE1_PREFIX = "m_style1";  //简单--复杂
	public final static String M_STYLE2_PREFIX = "m_style2";  //硬朗--圆润
	public final static String M_STYLE3_PREFIX = "m_style3";  //冷酷--温暖
	
	//种类key
	public static final String CATEGOTY_ARTWORK = "artwork"; //艺术品
	public static final String CATEGOTY_ARCHITECTURE = "architecture"; //建筑
	public static final String CATEGOTY_ANIMAL = "animal"; //动物
	public static final String CATEGOTY_PLANT = "plant"; //植物
	public static final String CATEGOTY_LANDSCAPE = "landscape"; //风景
	public static final String CATEGOTY_INNER_CLOTH = "innerCloth"; //内搭
	public static final String CATEGOTY_OUTTER_CLOTH = "outterCloth"; //外套
	public static final String CATEGOTY_PANTS = "pants"; //下装
	
	//风格---简单-复杂
	public static final String STYLE1_MINUS_3 = "s1:-3";
	public static final String STYLE1_MINUS_TWO_POINT_FIVE = "s1:-2.5";
	public static final String STYLE1_MINUS_TWO = "s1:-2";
	public static final String STYLE1_MINUS_ONE_POINT_FIVE = "s1:-1.5";
	public static final String STYLE1_MINUS_ONE = "s1:-1";
	public static final String STYLE1_MINUS_ZERO_POINT_FIVE = "s1:-0.5";
	public static final String STYLE1_ZERO = "s1:0";
	public static final String STYLE1_ZERO_POINT_FIVE = "s1:0.5";
	public static final String STYLE1_ONE = "s1:1";
	public static final String STYLE1_ONE_POINT_FIVE = "s1:1.5";
	public static final String STYLE1_TWO = "s1:2";
	public static final String STYLE1_TWO_POINT_FIVE = "s1:2.5";
	public static final String STYLE1_THREE = "s1:3";
	
	//风格---硬朗-圆润
	public static final String STYLE2_MINUS_3 = "s2:-3";
	public static final String STYLE2_MINUS_TWO_POINT_FIVE = "s2:-2.5";
	public static final String STYLE2_MINUS_TWO = "s2:-2";
	public static final String STYLE2_MINUS_ONE_POINT_FIVE = "s2:-1.5";
	public static final String STYLE2_MINUS_ONE = "s2:-3";
	public static final String STYLE2_MINUS_ZERO_POINT_FIVE = "s2:-0.5";
	public static final String STYLE2_ZERO = "s2:0";
	public static final String STYLE2_ZERO_POINT_FIVE = "s2:0.5";
	public static final String STYLE2_ONE = "s2:1";
	public static final String STYLE2_ONE_POINT_FIVE = "s2:1.5";
	public static final String STYLE2_TWO = "s2:2";
	public static final String STYLE2_TWO_POINT_FIVE = "s2:2.5";
	public static final String STYLE2_THREE = "s2:3";
	
	//风格---冷酷-温暖
	public static final String STYLE3_MINUS_3 = "s3:-3";
	public static final String STYLE3_MINUS_TWO_POINT_FIVE = "s3:-2.5";
	public static final String STYLE3_MINUS_TWO = "s3:-2";
	public static final String STYLE3_MINUS_ONE_POINT_FIVE = "s3:-1.5";
	public static final String STYLE3_MINUS_ONE = "s3:-3";
	public static final String STYLE3_MINUS_ZERO_POINT_FIVE = "s3:-0.5";
	public static final String STYLE3_ZERO = "s3:0";
	public static final String STYLE3_ZERO_POINT_FIVE = "s3:0.5";
	public static final String STYLE3_ONE = "s3:1";
	public static final String STYLE3_ONE_POINT_FIVE = "s3:1.5";
	public static final String STYLE3_TWO = "s3:2";
	public static final String STYLE3_TWO_POINT_FIVE = "s3:2.5";
	public static final String STYLE3_THREE = "s3:3";
	
	
	public static final String MATERIAL_ID_LIST = "material:id:list";
	public static final String MATCH_ID_LIST = "match:id:list";
	
	public static final String MATERIAL_DATA_HASH = "material:data";
	public static final String MATCH_DATA_HASH = "match:data";
	
	public static final HashMap<String,String> categoryMap = new HashMap<String,String>();
	static {
		categoryMap.put("艺术品", "artwork");
		categoryMap.put("建筑", "architecture");
		categoryMap.put("动物", "animal");
		categoryMap.put("植物", "plant");
		categoryMap.put("风景", "landscape");
		categoryMap.put("内搭", "innerCloth");
		categoryMap.put("外套", "outterCloth");
		categoryMap.put("下装", "pants");
	}
	
	public static final HashMap<String,String> m_categoryMap = new HashMap<String,String>();
	static {
		m_categoryMap.put("艺术品", "m_artwork");
		m_categoryMap.put("建筑", "m_architecture");
		m_categoryMap.put("动物", "m_animal");
		m_categoryMap.put("植物", "m_plant");
		m_categoryMap.put("风景", "m_landscape");
		m_categoryMap.put("内搭", "m_innerCloth");
		m_categoryMap.put("外套", "m_outterCloth");
		m_categoryMap.put("下装", "m_pants");
	}
}
