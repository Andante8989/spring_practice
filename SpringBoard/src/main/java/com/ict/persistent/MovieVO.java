package com.ict.persistent;

import java.sql.Date;

import lombok.Data;

@Data
public class MovieVO {

	
	private int rnum;
	private String movieNm;
	private Date openDt;
	private int audiCnt;
	private int showCnt;
	private Date dataDate;
}

/*
create table movieTbl (
    rnum int primary key,
    movieNm varchar2(100) not null,
    openDt date not null,
    audiCnt int not null,
    showCnt int not null,
    dataDate date
);

*/