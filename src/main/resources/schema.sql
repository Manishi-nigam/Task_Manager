-- =========================================================================
-- Spring Boot Task Management REST API - Database Initialization Script
-- =========================================================================
-- Database Name: task_management_db
--
-- NOTE: 
-- 1. This file contains the database creation query.
-- 2. Table creation (tasks) is handled automatically by Hibernate at runtime via:
--    spring.jpa.hibernate.ddl-auto=update
-- 3. In PostgreSQL, CREATE DATABASE must be executed as a top-level command
--    (outside a transaction block).
-- =========================================================================

-- Create the database (execute this script against the 'postgres' default database)
CREATE DATABASE task_management_db;
