package com.capstone.util;

public class Constants {
	/**정상적인 통신*/
	public final static String MSG_CODE_200 = "OK";
	/**INSERT ERROR*/
	public final static String MSG_CODE_101 = "INSERT_ERROR";
	/**SELECT ERROR*/
	public final static String MSG_CODE_103 = "SELECT_ERROR";
	/**UPDATE ERROR*/
	public final static String MSG_CODE_104 = "UPDATE_ERROR";
	/**DELETE ERROR*/
	public final static String MSG_CODE_105 = "DELECT_ERROR";
	/**DB ERROR*/
	public final static String MSG_CODE_106 = "DB_ERROR";
	/**TOKEN ERROR*/
	public final static String MSG_CODE_107 = "TOKEN_ERROR";
	/**TTL ERROR*/
	public final static String MSG_CODE_108 = "TTL_ERROR";
	/**REQUEST BODY ERROR 요청시 Body에 특정 값이 없음(ex user에 id가 빠졌다거나.. 
	 * json user 객체를 object user객체로 매핑하는과정에서 에러가 생길 수 있음)*/
	public final static String MSG_CODE_109 = "REQUEST_ERROR";
	/**HEADER TOKEN VALUE ERROR*/
	public final static String MSG_CODE_110 = "HEADER_ERROR";
	/**FILE_CREATE_ERROR*/
	public final static String MSG_CODE_111 = "FILE_CREATE_ERROR";
	/**USER_POINT_UPDATE_ERROR*/
	public final static String MSG_CODE_112 = "USER_POINT_UPDATE_ERROR";
	/**lat_lon_ERROR*/
	public final static String MSG_CODE_113 = "NO_VALUE_POSITION";
	/**INSERT후 변경된 값이 없음*/
	public final static String MSG_CODE_301 = "NO_INSERT";
	/**SELECT후 발견된 값이 없음*/
	public final static String MSG_CODE_303 = "NO_SELECT";
	/**UPDATE후 변경된 값이 없음*/
	public final static String MSG_CODE_304 = "NO_UPDATE";
	/**DELETE후 변경된 값이 없음*/
	public final static String MSG_CODE_305 = "NO_DELETE";
	/**NO FILE*/
	public final static String MSG_CODE_306 = "NO_FILE";
	/**TOKEN CREATE ERROR*/
	public final static String MSG_CODE_307 = "TOKEN_CREATE_ERROR";
	
	/**INSERT ERROR*/
	public final static String ERROR_CODE_1 = "ERROR_CODE_1";
	/**SELECT ERROR*/
	public final static String ERROR_CODE_2 = "ERROR_CODE_2";
	/**UPDATE ERROR*/
	public final static String ERROR_CODE_3 = "ERROR_CODE_3";
	/**DELETE ERROR*/
	public final static String ERROR_CODE_4 = "ERROR_CODE_4";
	/**AUTH ERROR*/
	public final static String ERROR_CODE_5 = "ERROR_CODE_5";
	/**REQUEST BODY ERROR*/
	public final static String ERROR_CODE_6 = "ERROR_CODE_6";
	/**HEADER TOKEN VALUE ERROR*/
	public final static String ERROR_CODE_7 = "ERROR_CODE_7";
	/**HEADER TOKEN VALUE ERROR*/
	public final static String ERROR_CODE_8 = "ERROR_CODE_8";
	/**DB에 정상적으로 insert함.*/
	public final static int DB_RES_CODE_1 = 1;
	/**DB에 정상적으로 insert하지 못함.*/
	public final static int DB_RES_CODE_2 = 2;
	/**DB에 select 결과가 있음.*/
	public final static int DB_RES_CODE_3 = 3;
	/**DB에 select 결과가 없음.*/
	public final static int DB_RES_CODE_4 = 4;
	/**DB에 정상적으로 update함.*/
	public final static int DB_RES_CODE_5 = 5;
	/**DB에 정상적으로 update하지못함.*/
	public final static int DB_RES_CODE_6 = 6;
	/**DB에 정상적으로 delete함.*/
	public final static int DB_RES_CODE_7 = 7;
	/**DB에 정상적으로 delete하지못함.*/
	public final static int DB_RES_CODE_8 = 8;
	/**DB 오류*/
	public final static int DB_RES_CODE_9 = 6;
	
	
	/**가까운 클라이언트 출력 갯수*/
	public final static int NEAR_CLIENT_CNT = 5;
	
	public final static String SERVER_FILE_PATH = "http://printaw.com/resources/file/";
}
