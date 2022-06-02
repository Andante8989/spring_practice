package com.ict.persistent;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardVO {
	
	private Long bno;
	private String title;
	private String writer;
	private Date regDate;
	private Date updateDate;
}
