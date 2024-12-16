package com.mangoe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class StreamTest {
	
	String json;
	List<Map<String, Object>> retrieveList;
	
	public StreamTest() throws Exception {
		json = "[{\"rnum\":1,\"bcryptPassword\":\"$2a$10$IW/qp7DSKvlp.yvvA16Tset7Nlxusg7mRGLsIAutYghvKM8x6vS7m\",\"password\":\"123123\",\"totalCount\":33,\"userName\":\"방진우\",\"userId\":\"opsqwe123\"}\r\n"
				+ ",{\"rnum\":2,\"password\":\"1234\",\"totalCount\":33,\"userName\":\"하은테스트\",\"userId\":\"haha\"}\r\n"
				+ ",{\"rnum\":3,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$izKRaBsjNra2FbMKKal.3..ZlSvVuADCYLmMGINJc0lEwpVtAy8Jq\",\"totalCount\":33,\"userName\":\"방진우라는이름\",\"userRole\":\"ADMIN\",\"userId\":\"admin\"}\r\n"
				+ ",{\"rnum\":4,\"password\":\"$2a$10$hgZDf2npubN3Svhe3.WKEugXK8J8vbW8XToa.37ZTHTtOm1stiS3a\",\"totalCount\":33,\"userName\":\"이하은이다\",\"userRole\":\"USER\",\"userId\":\"hihi\"}\r\n"
				+ ",{\"rnum\":5,\"password\":\"$2a$10$2RWJaJc2HQj6Y9YJxqVctuHgLCKameuTBbvmqAm4rfGje7828Dai.\",\"totalCount\":33,\"userName\":\"관리자다\",\"userRole\":\"ADMIN\",\"userId\":\"admin1\"}\r\n"
				+ ",{\"rnum\":6,\"password\":\"$2a$10$SNjBXPEBJrElOzT15Y4It.6jlStNfcXTDMe.Kge3U6AaeVxi5dRk6\",\"totalCount\":33,\"userName\":\"1234\",\"userRole\":\"ADMIN\",\"userId\":\"test4\"}\r\n"
				+ ",{\"rnum\":7,\"password\":\"$2a$10$iQW0xBwnh2IanZRAhyFuc..sHyt9nJVYBuxkQsPVDpAEu0WVypldm\",\"totalCount\":33,\"userName\":\"1234\",\"userRole\":\"USER\",\"userId\":\"test1\"}\r\n"
				+ ",{\"rnum\":8,\"password\":\"$2a$10$WSkxPlovVPINxQnwMBILReo8i0csr5WsCm6VRbyFQ7PcsOb1y0EPG\",\"totalCount\":33,\"userName\":\"테슽\",\"userRole\":\"ADMIN\",\"userId\":\"we\"}\r\n"
				+ ",{\"rnum\":9,\"password\":\"1111\",\"bcryptPassword\":\"test\",\"totalCount\":33,\"userName\":\"마켓\",\"userRole\":\"GUEST\",\"userId\":\"abc\"}\r\n"
				+ ",{\"rnum\":10,\"password\":\"$2a$10$JXPJr/z81usRCRryBNwQieHt8MxGfa3F6nWf7g6.UMZl4S2xvRPSG\",\"totalCount\":33,\"userName\":\"sd\",\"userRole\":\"USER\",\"userId\":\"qw\"}\r\n"
				+ ",{\"rnum\":11,\"password\":\"\",\"totalCount\":33,\"userName\":\"\",\"userRole\":\"\",\"userId\":\"\"}\r\n"
				+ ",{\"rnum\":12,\"password\":\"$2a$10$JJ/xDdpGB5S9LCryoWfFLuclxyuBs1qtylGneVEwK7MQHiStE0SMu\",\"totalCount\":33,\"userName\":\"k\",\"userRole\":\"ADMIN\",\"userId\":\"test\"}\r\n"
				+ ",{\"rnum\":13,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$gST/uYe/UA67LCmyL79sDe4MIaA.RRcSR04PKjMb4nk9Kpez76mKS\",\"totalCount\":33,\"userName\":\"방진우라는이름\",\"userRole\":\"ADMIN\",\"userId\":\"admin11\"}\r\n"
				+ ",{\"rnum\":14,\"password\":\"$2a$10$5/S3aONveL6dXOK8mho1HugmeoYW9jFK..A5vZOOIgsfRRiUdeQf6\",\"totalCount\":33,\"userName\":\"jw\",\"userRole\":\"ADMIN\",\"userId\":\"test0311\"}\r\n"
				+ ",{\"rnum\":15,\"password\":\"$2a$10$6LtdVDThoJOtHZWpycE5uunzB7TAWbqDPJYIoX3iNviHJDFkuWEXq\",\"totalCount\":33,\"userName\":\"test1111\",\"userRole\":\"USER\",\"userId\":\"test0315\"}\r\n"
				+ ",{\"rnum\":16,\"password\":\"$2a$10$55VGsPJ5zHsUHbf2i/dMU.5mcXUWeAM21jpHlCFIVPPTJH45Xhwk6\",\"totalCount\":33,\"userName\":\"관리자사용자\",\"userRole\":\"ADMIN,USER\",\"userId\":\"adminuser\"}\r\n"
				+ ",{\"rnum\":17,\"password\":\"1111s\",\"bcryptPassword\":\"123123\",\"totalCount\":33,\"userName\":\"test2222\",\"userRole\":\"GUEST\",\"userId\":\"test23232\"}\r\n"
				+ ",{\"rnum\":18,\"password\":\"1111\",\"bcryptPassword\":\"$2a$10$m9ISmGxczLABrbsX1E1ooOvnSUPUeVZWQeNzENOo9S9WQnf8nm5sm\",\"totalCount\":33,\"userName\":\"마켓\",\"userRole\":\"GUEST\",\"userId\":\"abc2323\"}\r\n"
				+ ",{\"rnum\":19,\"password\":\"123123123123\",\"bcryptPassword\":\"$2a$10$ypD/BeygW4IOCKXWCiN7geqIzO0QT0r95YnLzmpF28RCrzhkhk5Xq\",\"totalCount\":33,\"userName\":\"12312312\",\"userRole\":\"123123\",\"userId\":\"123\"}\r\n"
				+ ",{\"rnum\":20,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$D3r1t1XuNpB.8xLXfu8O7u4OctRPmdMd6fZYMclMYj3CBbc9SwVmm\",\"totalCount\":33,\"userName\":\"방진우라는이름\",\"userRole\":\"GUEST\",\"userId\":\"admin33\"}\r\n"
				+ ",{\"rnum\":21,\"password\":\"123123\",\"bcryptPassword\":\"$2a$10$hC9wdOECeNbYzmkt/9gcVOEYEEbUzKIFKs.28ju8vNtP7XZYnsRCu\",\"totalCount\":33,\"userName\":\"123123\",\"userRole\":\"123123123\",\"userId\":\"123123\"}\r\n"
				+ ",{\"rnum\":22,\"password\":\"12334567\",\"bcryptPassword\":\"$2a$10$ysxTAKY/0xUqOVefRMw7L.iTM5/aG9mYuld/f8dOrUftx4MoT3rSq\",\"totalCount\":33,\"userName\":\"asdfasdfasdf\",\"userRole\":\"USER\",\"userId\":\"user123435\"}\r\n"
				+ ",{\"rnum\":23,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$m9ISmGxczLABrbsX1E1ooOvnSUPUeVZWQeNzENOo9S9WQnf8nm5sm\",\"totalCount\":33,\"userName\":\"방진우라는이름\",\"userRole\":\"GUEST\",\"userId\":\"admin44\"}\r\n"
				+ ",{\"rnum\":24,\"password\":\"test001\",\"temporaryPw\":\"실제bcrypt_password는 test002\",\"bcryptPassword\":\"{bcrypt}$2a$10$P4W3JAHukfnDXV3LBfR3YuGiCf7CFDqXuKM.ggYIb3.x9WsARle72\",\"totalCount\":33,\"userName\":\"테스트\",\"userRole\":\"USER\",\"userId\":\"test001\"}\r\n"
				+ ",{\"rnum\":25,\"password\":\"$2a$10$k3Y3m8v8FzZKXTfl/9B5.OKdfoJDIN0DvRK5dlWWC06dXdXsSLWg6\",\"totalCount\":33,\"userName\":\"집\",\"userRole\":\"USER\",\"userId\":\"home\"}\r\n"
				+ ",{\"rnum\":26,\"bcryptPassword\":\"asdfasdfa\",\"password\":\"11111\",\"totalCount\":33,\"userName\":\"asdf\",\"userId\":\"helloworld\"}\r\n"
				+ ",{\"rnum\":27,\"password\":\"12345123\",\"bcryptPassword\":\"$2a$10$wGw63Tp.7kwFoBIIb7luK.WOcJ8VJLW9yl2wtWVN4w1S1HUXUQZ0u\",\"totalCount\":33,\"userName\":\"123123\",\"userRole\":\"ADMIN\",\"userId\":\"kjw_user123\"}\r\n"
				+ ",{\"rnum\":28,\"password\":\"$2a$10$mcRtYeP7S5jaEgLkcs.NbOfByOTO0TW9p624lcryfFUKV0BSzxELG\",\"totalCount\":33,\"userName\":\"1234\",\"userRole\":\"USER\",\"userId\":\"test3\"}\r\n"
				+ ",{\"rnum\":29,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$YkTWH86K4JUlVmNxJ752tuoCgsyxcBGuTNXRxsZBMsJyko7lDJJs2\",\"totalCount\":33,\"userName\":\"1234\",\"userRole\":\"ADMIN\",\"userId\":\"123124\"}\r\n"
				+ ",{\"rnum\":30,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$izKRaBsjNra2FbMKKal.3..ZlSvVuADCYLmMGINJc0lEwpVtAy8Jq\",\"totalCount\":33,\"userName\":\"방진우라는이름\",\"userRole\":\"GUEST\",\"userId\":\"admin22\"}\r\n"
				+ ",{\"rnum\":31,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$i1NONbJ732XLTG.23wUOrO2Rj3Ap7U6kE354j/9eqMdGcNwdgb.tW\",\"totalCount\":33,\"userName\":\"kjw_user\",\"userRole\":\"ROLE_USER\",\"userId\":\"kjw_user\"}\r\n"
				+ ",{\"rnum\":32,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$x4yvYbpUb4P5ad..F43mtex4V0dUq59C2xlW7fGpG8tk97WDIR5Km\",\"totalCount\":33,\"userName\":\"kjw_manager\",\"userRole\":\"ROLE_MANAGER\",\"userId\":\"kjw_manager\"}\r\n"
				+ ",{\"rnum\":33,\"password\":\"1234\",\"bcryptPassword\":\"$2a$10$UZ6gTndR0idQ2EHVFaus2.wOW2TIEzfDOx9XoZTA8Bo6YNuKc4oKe\",\"totalCount\":33,\"userName\":\"kjw_admin\",\"userRole\":\"ROLE_ADMIN\",\"userId\":\"kjw_admin\"}]";
		
		retrieveList = new ObjectMapper().readValue(json, new TypeReference<List<Map<String, Object>>>(){});
	}
	
	@Test
	@Disabled
	public void lambdaTest() {
		//lambda
		// 표현방식
		FunctionalInterfaceTest<Integer> test1 = (Integer i) -> { return i < 200 ; };
		FunctionalInterfaceTest<Integer> test2 = (i) -> { return i < 200 ; };
		FunctionalInterfaceTest<Integer> test3 = i -> i < 200 ;
		
		System.out.println(test3.test(100));
		
		FunctionalInterfaceTest2 test4 = () -> System.out.println("hello world!");
		test4.print();
	}

	@Test
	@Disabled
	public void PredicateTest() {
		//Functional Interface : Predicate
		Predicate<Integer> predicate1 = t -> t < 200 ; 
		
		System.out.println(
			predicate1.test(100)
		);
		
		assertThat(predicate1.test(100)).isEqualTo(true);
		retrieveList.stream().map(t -> Integer.parseInt(t.get("rnum").toString())).filter(predicate1).toList();
	}

	@Test
	@Disabled
	public void ConsumerTest() {
		
		List<String> list2 = new ArrayList<String>(Arrays.asList("1번", "2번", "3번"));
		for(String t :list2) {
			System.out.println(t);
		}
		
		//Functional Interface : Consumer
		Consumer<String> consumer = t -> System.out.println(t);
		list2.forEach(consumer);
	
	}

	@Test
	@Disabled
	public void SupplierTest() {
		//Functional Interface : Supplier
		Supplier<String> supplier1 = () -> "hello world!";
		
		System.out.println(
			supplier1.get()
		);
		
        Supplier<Integer> supplier2 = () -> new Random().nextInt(100);

        List<Integer> a = Stream.generate(supplier2).limit(5).toList();
        a.stream().forEach(t -> System.out.println(t));
	}
	
	@Test
	@Disabled
	public void FunctionTest() {
		//Functional Interface : Function
		Function<Integer, String> function1 = t -> t.toString();

		System.out.println( 
			"자료형 : "+function1.apply(1).getClass() + 
			", 값 : " + function1.apply(1)
		);
		List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1,2,3));
		List<String> list2 = list1.stream().map(function1).toList();
	}
	
	@Test
	@Disabled
	public void ComparatorTest() {
		//Functional Interface : Comparator
		Comparator<Integer> comparator1 = (o1, o2) -> o1.intValue() - o2.intValue();
		Comparator<Integer> comparator2 = (o1, o2) -> (o1.intValue() - o2.intValue()) *-1;
		
		List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1,3,2,4,5));
		
		list1.stream().forEach(t -> System.out.print(t+ " "));
		
		Collections.sort(list1, comparator1);
		System.out.println();
		list1.stream().forEach(t -> System.out.print(t+ " "));
		
		Collections.sort(list1, comparator2);
		System.out.println();
		list1.stream().forEach(t -> System.out.print(t+ " "));
	}

	@Test
	@Disabled
	public void streamMapTest() {
		
		List<String> userNameList1 = new ArrayList<>();
		for (Map<String, Object> map : retrieveList) {
			if(map.get("userName") != null && !userNameList1.contains(map.get("userName").toString())) {
				userNameList1.add(map.get("userName").toString());
			}
		}
		
		List<String> userNameList2 = retrieveList.stream().map(t -> t.get("userName").toString()).distinct().toList();
		System.out.println(userNameList2);
		
		assertThat(userNameList1).isEqualTo(userNameList2);
	}
	
	@Test
	@Disabled
	public void streamFilterTest() {
		
		List<Map<String, Object>> adminList = new ArrayList<>();
		for (Map<String, Object> map : retrieveList) {
			if(map.get("userRole") != null && map.get("userRole").equals("ADMIN")) {
				adminList.add(map);
			}
		}
		
		List<Map<String, Object>> adminList2 = retrieveList.stream().filter(t -> t.get("userRole") != null && t.get("userRole").equals("ADMIN")).collect(Collectors.toList());
		System.out.println(adminList2);
		
		assertThat(adminList).isEqualTo(adminList2);
		
				
		System.out.println(
				retrieveList.stream().map(t -> Integer.parseInt(t.get("rnum").toString())).takeWhile(t -> t < 10).toList()
				);
		
		System.out.println(
			retrieveList.stream().map(t -> Integer.parseInt(t.get("rnum").toString())).dropWhile(t -> t < 10).toList()
		);
	}

	@Test
	@Disabled
	public void streamForEachTest() {
		
		for (Map<String, Object> map : retrieveList) {
			if(map.get("userRole") == null) {
				map.put("userRole", "GUEST");
			}
		}
		
		retrieveList.stream().filter(t -> t.get("userRole")==null).forEach(t -> t.put("userRole", "GUEST"));
		System.out.println(retrieveList);
	}

	@Test
	@Disabled
	public void streamMaxMinTest() {
		
		int max = retrieveList.stream().mapToInt(t -> Integer.parseInt(t.get("rnum").toString())).max().orElse(0);
		int min = retrieveList.stream().mapToInt(t -> Integer.parseInt(t.get("rnum").toString())).min().orElse(0);
		System.out.println("max : "+ max +"  min : "+ min );
	}

	@Test
	public void streamMatchTest() {
		
		System.out.println(
			retrieveList.stream().filter(t -> t.get("userRole") != null).anyMatch(t -> t.get("userRole").equals("ADMIN"))
		);
		
		System.out.println(
			retrieveList.stream().filter(t -> t.get("userRole") != null).allMatch(t -> t.get("userRole").equals("ADMIN"))
		);
		
		System.out.println(
			retrieveList.stream().filter(t -> t.get("userRole") != null).noneMatch(t -> t.get("userRole").equals("ADMINasdasd"))
		);
	}
	
} 
