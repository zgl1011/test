package com.zgl.tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonDiff {
	public static final String templetJson ="{"
			+ "\"code\": \"0\","
			+ "\"msg\": \"ok\","
			+ "\"time\": \"2016-10-13 16:13:48\","
			+ "\"data\": {"
			+ "\"count\": 1,"
			+ "\"list\": [{"
			+ "\"pid\": \"��Ʒid\","
			+ "\"title\": \"��Ʒ����\","
			+ "\"price\": \"�۸�\","
			+ "\"desc\": \"��Ʒ����\"}]"
			+ "}}";	
	
	
	public static final String testJson="{"
			+ "\"code\": \"0\","
			+ "\"msg\": \"ok\","
		//	+"\"test\":\"hahhahahah\","
			+ "\"time\": \"2016-10-13 16:13:48\","
			+ "\"data\": {"
			+ "\"count\": 1,"
			+ "\"list\": [{"
			+ "\"pid\": \"edb1eb3e82964efba7117fa7feed2f53\","
			+ "\"title\": \"ţB����\","
			+ "\"price\": \"12.00\","
			+"\"product_item\":[{"
			+"\"id\":123"
			+"},"
			+ "{\"id\":234"
			+ "}],"
			+ "\"desc\": \"�����óԵ�����������������쵼�ߡ�\"}]"
			+ "}}";	
	
	public static void main(String[] args){
//		System.out.println(testJson);
//		ArrayList<String> jsonKeys = getJsonKeys(null, JSONObject.parseObject(testJson));
//		
//		System.out.println("key������"+jsonKeys.size());
//		
//		System.out.println("key��ϸ��"+jsonKeys);
		
		//��ʼ�Ա�
		compare(testJson, templetJson);
	}
	
	/**
	 * �ȶ�json��ģ�������
	 * @param json
	 * @param templet
	 * @return
	 */
	public static boolean compare(String json,String templet){
		ArrayList<String> jsonKeyList = getJsonKeys(null, JSONObject.parseObject(json));
		ArrayList<String> templetKeyList = getJsonKeys(null, JSONObject.parseObject(templet));
		int sizeDiff = jsonKeyList.size()-templetKeyList.size();
		StringBuilder builder = new StringBuilder();
		boolean isContain = true;
		for (String string : jsonKeyList) {
			if(!templetKeyList.contains(string)){
				isContain = false;
				builder.append(string).append(",");
			}
		}
		if (sizeDiff!=0) {
			System.out.println("MSG: ���Ȳ�һ��");
		}
		
		if (!isContain) {
			System.out.println("MSG: ����������  "+builder.toString());
		}	
		
		if (sizeDiff==0&&isContain) {
			System.out.println("MSG: json format is right!");
		}
		return sizeDiff==0||isContain;
	}
	
	
	/**
	 * ��ȡjson���е�key�ļ���
	 * @param head
	 * @param jsonObj
	 * @return
	 */
	public static ArrayList<String> getJsonKeys(String head,JSONObject jsonObj){
		ArrayList<String> keys = new ArrayList<String>();
		Set<Entry<String, Object>> set =  jsonObj.entrySet();
		Iterator<Entry<String, Object>> iter = set.iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			
			keys.add(head==null?key:head+key);
			//�ж�value����
			if (value instanceof JSONObject) {
				ArrayList<String> list = getJsonKeys(head==null?key+"-":head+key+"-", (JSONObject) value);
				keys.addAll(list);
			}else if (value instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) value;
				if (jsonArray.size()!=0) {
					Object obj = jsonArray.get(0);
						
						//��array�е����ͽ����ж�
						if (obj instanceof JSONObject) {
							ArrayList<String> list = getJsonKeys(head==null?key+"-":head+key+"-", (JSONObject)jsonArray.get(0));
							keys.addAll(list);
						}else{
							keys.add(head==null?key:head+key);
						}
					}
				
			}
		}
		return keys;
	}
        
}
