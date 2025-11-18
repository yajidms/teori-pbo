package com.codeway.daoTemplate.sample;

import com.codeway.daoTemplate.dao.GenericDaoImpl;
//import com.codeway.daoTemplate.utils.TemplateLogger;
//import com.codeway.daoTemplate.utils.PooledDataSource;

public class UserDaoImpl extends GenericDaoImpl<Integer, User> {

	public UserDaoImpl() {
		super(User.class, new MyDataSource());

//		super(User.class, PooledDataSource.getInstance());
//		TemplateLogger.shouldLog =false;
	}
	
	public User getUserByMobileNo(String mobile) throws Exception {
		 return getSingleEntity("mobile=?", mobile);
	}

//	public List<User> searchUsersByName(String name) throws Exception {
//		return getList("name like ?", "%"+name+"%");
//	}
//	private static UserDaoImpl instance;
//	
//	public static UserDaoImpl getInstance() {
//		if(instance ==null){
//			synchronized (UserDaoImpl.class) {
//	            if(instance == null){
//	                instance = new UserDaoImpl();
//	            }
//	        }
//		}
//		return instance;
//	}
	
	public static void main(String[] args) throws Exception{

		UserDaoImpl userDao = new UserDaoImpl();
		String mobile = "999999998";

		User staleUser = userDao.getUserByMobileNo(mobile);
		if (staleUser != null) {
			userDao.remove(staleUser.getId());
		}
		System.out.println("== Create ==");
		User user = new User();
		user.setEmail("test@abc1.com");
		user.setName("User CRUD Demo");
		user.setMobile(mobile);
		userDao.save(user);
		System.out.println("Created: " + describe(user));

		System.out.println("== Read ==");
		User existing = userDao.getUserByMobileNo(mobile);
		System.out.println("Fetched: " + describe(existing));

		System.out.println("== Update ==");
		existing.setName("User CRUD Updated");
		existing.setEmail("updated@abc1.com");
		userDao.update(existing);
		User updated = userDao.get(existing.getId());
		System.out.println("After update: " + describe(updated));

		System.out.println("== Delete ==");
		userDao.remove(updated.getId());
		User deleted = userDao.get(updated.getId());
		System.out.println(deleted == null ? "User successfully deleted" : "Delete failed: " + describe(deleted));
	}

	private static String describe(User user) {
		if (user == null) {
			return "null";
		}
		return String.format("User{id=%s, name='%s', email='%s', mobile='%s'}",
				user.getId(), user.getName(), user.getEmail(), user.getMobile());
	}
}
