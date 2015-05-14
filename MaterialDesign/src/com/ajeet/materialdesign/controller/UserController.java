package com.ajeet.materialdesign.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ajeet.materialdesign.db.DatabaseHelper;
import com.ajeet.materialdesign.model.UserDetail;
import com.ajeet.materialdesign.util.Common;

public class UserController {
	private Context context;
	
	public UserController(Context context) {
		this.context = context;
		
	}
	
	

	public long addNewUser(UserDetail user){
		SQLiteDatabase sqldb = new DatabaseHelper(context)
		.getWritableDatabase();
		long addUser;
		ContentValues values = new ContentValues();
		//values.put("UserId", user.getUserId());
		values.put("UserName", user.getUserName());
		values.put("Email", user.getEmail());
		values.put("MobileNumber", user.getMobile());
		values.put("CreatedDate",Common.getCurrentDateTime());
		values.put("LastUpdatedDate", Common.getCurrentDateTime());
		
		addUser = sqldb.insert(DatabaseHelper.TABLE_USER_DETAILS, null, values);
		sqldb.close();
		return addUser;
		
	}
	
}
