package com.atinbo.support.core.security;

public class RedisUtilsTest {

//    public static List<User> buildTestData() {
//        User a = new User();
//        a.setName("a");
//        a.setAge(1);
//        User b = new User();
//        b.setName("b");
//        b.setAge(2);
//
//        List<User> list = new ArrayList<User>();
//        list.add(a);
//        list.add(b);
//        return list;
//    }
//
//    @Test
//	public void setGetListTest() throws Exception {
//        List<User> list = buildTestData();
//        RedisUtils.setList("test:userlist", 60*5, list);
//
//        List<User> resultList = RedisUtils.getList("test:userlist");
//        for (User current : resultList) {
//            System.out.println(String.format("data[%s, %d]", current.getName(), current.getAge()));
//        }
//    }
//
//	@Test
//	public void setGetByJson() throws Exception {
//        List<User> list = buildTestData();
//        RedisUtils.setByJson("test:userlist:json", list);
//
//		List<User> resultList = RedisUtils.getByJson("test:userlist:json", new TypeReference<List<User>>(){});
//        for (User current : resultList) {
//            System.out.println(String.format("data[%s, %d]", current.getName(), current.getAge()));
//        }
//	}
//
//	static class User implements Serializable {
//
//		String name;
//        int age;
//
//		public String getName() {
//            return name;
//        }
//        public void setName(String name) {
//            this.name = name;
//        }
//        public int getAge() {
//			return age;
//		}
//		public void setAge(int age) {
//			this.age = age;
//		}
//    }
}
