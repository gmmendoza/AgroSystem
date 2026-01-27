-- Database initialization script
-- This file is automatically executed when PostgreSQL container starts

-- Create schema if needed
CREATE SCHEMA IF NOT EXISTS public;

-- Grant permissions
GRANT ALL PRIVILEGES ON SCHEMA public TO postgres;
