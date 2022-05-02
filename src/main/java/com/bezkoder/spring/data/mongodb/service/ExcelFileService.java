package com.bezkoder.spring.data.mongodb.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.bezkoder.spring.data.mongodb.model.Contact;

public interface ExcelFileService {
	
	ByteArrayInputStream export(List<Contact> contacts);
	//ByteArrayInputStream export2(List<Contact> contacts);
	
}