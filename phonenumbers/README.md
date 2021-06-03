Spring Boot Application
 Spring batch
 Spring MVC

API endpoint
 upload CSV = http://localhost:8080/csv/upload
 phone number list = http://localhost:8080/phonenumbers (optional search_type param to filter results as 'corrected', 'valid', 'invalid')

DB in memory H2
 schema-all.sql 

Phone number web form
 http://localhost:8080
 
The app assume the format 27123456789 as the 'valid' one, with 11 digits.
If the size is <> 11 it is considered 'invalid'
If the prefix is <> 27 and size = 11, the prefix is 'corrected'