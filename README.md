##Project Title : Student Management System
##Group members:
Fatima Memon
CMS_ID : 023-25-0034

Purpose: The primary purpose of a Student Management System (SMS) database is to centralize, secure, and streamline the storage and management of student-related data, transforming manual, paper-based processes into an efficient digital workflow. It serves as a single source of truth for academic, personal, and administrative records, facilitating better communication and decision-making for stakeholders

##TO run: Download java SQL connector

##MySQL requirements:

create database finalPro;

create table Students(ID int Primary Key, NAME varchar(20),DEPARTMENT varchar(20));

create table StudentFee(ID int,feePaid int, feeRem int,FOREIGN KEY (ID) REFERENCES Students(ID));


##YOUTUBE URL:
https://youtu.be/zWESuHBYQps
https://youtu.be/zWESuHBYQps


##GITHUB URL:
https://github.com/fatimamemon1/SecSemester
