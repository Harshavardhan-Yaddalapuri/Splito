# Detailed Database Schema for Splito

## Core User Management

### Table: users
- id SERIAL PRIMARY KEY
- email VARCHAR(255) UNIQUE NOT NULL
- password_hash VARCHAR(255) NOT NULL
- name VARCHAR(100)
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- is_verified BOOLEAN DEFAULT FALSE
- last_login TIMESTAMP WITH TIME ZONE
- profile_picture_url VARCHAR(255)
- phone_number VARCHAR(20)
- date_of_birth DATE
- gender VARCHAR(10)

### Table: user_roles
- id SERIAL PRIMARY KEY
- name VARCHAR(50) UNIQUE NOT NULL

### Table: user_role_mappings
- user_id INTEGER REFERENCES users(id)
- role_id INTEGER REFERENCES user_roles(id)
- PRIMARY KEY (user_id, role_id)

### Table: user_sessions
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- session_token VARCHAR(255) UNIQUE NOT NULL
- ip_address VARCHAR(45)
- user_agent TEXT
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- expires_at TIMESTAMP WITH TIME ZONE

### Table: password_reset_tokens
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- token VARCHAR(255) UNIQUE NOT NULL
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- expires_at TIMESTAMP WITH TIME ZONE

## User Settings and Preferences

### Table: user_settings
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id) UNIQUE
- default_currency VARCHAR(3) DEFAULT 'USD'
- language VARCHAR(5) DEFAULT 'en-US'
- theme VARCHAR(10) DEFAULT 'light'
- timezone VARCHAR(50) DEFAULT 'UTC'
- notification_preferences JSONB
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: notification_types
- id SERIAL PRIMARY KEY
- name VARCHAR(50) UNIQUE NOT NULL
- description TEXT

### Table: user_notification_settings
- user_id INTEGER REFERENCES users(id)
- notification_type_id INTEGER REFERENCES notification_types(id)
- is_enabled BOOLEAN DEFAULT TRUE
- PRIMARY KEY (user_id, notification_type_id)

## Categories and Tags

### Table: categories
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- name VARCHAR(50) NOT NULL
- color VARCHAR(7)
- icon VARCHAR(50)
- is_default BOOLEAN DEFAULT FALSE
- parent_category_id INTEGER REFERENCES categories(id)
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: tags
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- name VARCHAR(50) NOT NULL
- color VARCHAR(7)

## Transactions (Expenses and Incomes)

### Table: transactions
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- amount DECIMAL(12, 2) NOT NULL
- description TEXT
- date DATE NOT NULL
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- type VARCHAR(10) NOT NULL CHECK (type IN ('expense', 'income'))
- is_recurring BOOLEAN DEFAULT FALSE

### Table: transaction_categories
- transaction_id INTEGER REFERENCES transactions(id)
- category_id INTEGER REFERENCES categories(id)
- amount DECIMAL(12, 2) NOT NULL
- PRIMARY KEY (transaction_id, category_id)

### Table: transaction_tags
- transaction_id INTEGER REFERENCES transactions(id)
- tag_id INTEGER REFERENCES tags(id)
- PRIMARY KEY (transaction_id, tag_id)

### Table: payment_methods
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- name VARCHAR(50) NOT NULL
- type VARCHAR(20) NOT NULL -- e.g., 'cash', 'credit_card', 'bank_transfer'
- details JSONB -- For storing card last 4 digits, bank account info, etc.

### Table: transaction_payment_methods
- transaction_id INTEGER REFERENCES transactions(id)
- payment_method_id INTEGER REFERENCES payment_methods(id)
- amount DECIMAL(12, 2) NOT NULL
- PRIMARY KEY (transaction_id, payment_method_id)

## Recurring Transactions

### Table: recurring_transactions
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- amount DECIMAL(12, 2) NOT NULL
- description TEXT
- frequency VARCHAR(20) NOT NULL -- e.g., 'daily', 'weekly', 'monthly', 'yearly'
- start_date DATE NOT NULL
- end_date DATE
- last_occurrence DATE
- type VARCHAR(10) NOT NULL CHECK (type IN ('expense', 'income'))
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: recurring_transaction_categories
- recurring_transaction_id INTEGER REFERENCES recurring_transactions(id)
- category_id INTEGER REFERENCES categories(id)
- amount DECIMAL(12, 2) NOT NULL
- PRIMARY KEY (recurring_transaction_id, category_id)

## Budgets

### Table: budgets
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- name VARCHAR(100)
- amount DECIMAL(12, 2) NOT NULL
- start_date DATE NOT NULL
- end_date DATE NOT NULL
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: budget_categories
- budget_id INTEGER REFERENCES budgets(id)
- category_id INTEGER REFERENCES categories(id)
- amount DECIMAL(12, 2) NOT NULL
- PRIMARY KEY (budget_id, category_id)

## Attachments and Receipts

### Table: attachments
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- file_name VARCHAR(255) NOT NULL
- file_path VARCHAR(255) NOT NULL
- file_type VARCHAR(50)
- file_size INTEGER
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: transaction_attachments
- transaction_id INTEGER REFERENCES transactions(id)
- attachment_id INTEGER REFERENCES attachments(id)
- PRIMARY KEY (transaction_id, attachment_id)

## Reports and Analytics

### Table: saved_reports
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- name VARCHAR(100)
- type VARCHAR(50) -- e.g., 'expense', 'income', 'budget'
- parameters JSONB -- store report configuration
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- last_run_at TIMESTAMP WITH TIME ZONE

### Table: report_schedules
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- report_id INTEGER REFERENCES saved_reports(id)
- frequency VARCHAR(20) NOT NULL -- e.g., 'daily', 'weekly', 'monthly'
- next_run_date DATE
- is_active BOOLEAN DEFAULT TRUE

## Goals and Savings

### Table: financial_goals
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- name VARCHAR(100)
- target_amount DECIMAL(12, 2) NOT NULL
- current_amount DECIMAL(12, 2) DEFAULT 0
- start_date DATE NOT NULL
- target_date DATE
- is_completed BOOLEAN DEFAULT FALSE
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: goal_contributions
- id SERIAL PRIMARY KEY
- goal_id INTEGER REFERENCES financial_goals(id)
- amount DECIMAL(12, 2) NOT NULL
- date DATE NOT NULL
- transaction_id INTEGER REFERENCES transactions(id)

## Currency and Exchange Rates

### Table: currencies
- id SERIAL PRIMARY KEY
- code VARCHAR(3) UNIQUE NOT NULL
- name VARCHAR(50) NOT NULL
- symbol VARCHAR(5)

### Table: exchange_rates
- id SERIAL PRIMARY KEY
- from_currency_id INTEGER REFERENCES currencies(id)
- to_currency_id INTEGER REFERENCES currencies(id)
- rate DECIMAL(10, 6) NOT NULL
- date DATE NOT NULL
- UNIQUE (from_currency_id, to_currency_id, date)

## User Collaboration and Sharing

### Table: shared_expenses
- id SERIAL PRIMARY KEY
- expense_id INTEGER REFERENCES transactions(id)
- shared_by_user_id INTEGER REFERENCES users(id)
- shared_with_user_id INTEGER REFERENCES users(id)
- amount DECIMAL(12, 2) NOT NULL
- status VARCHAR(20) DEFAULT 'pending' -- e.g., 'pending', 'accepted', 'rejected', 'paid'
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: expense_groups
- id SERIAL PRIMARY KEY
- name VARCHAR(100) NOT NULL
- created_by_user_id INTEGER REFERENCES users(id)
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: expense_group_members
- group_id INTEGER REFERENCES expense_groups(id)
- user_id INTEGER REFERENCES users(id)
- joined_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- PRIMARY KEY (group_id, user_id)

### Table: group_expenses
- id SERIAL PRIMARY KEY
- group_id INTEGER REFERENCES expense_groups(id)
- expense_id INTEGER REFERENCES transactions(id)
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

## Notifications and Alerts

### Table: notifications
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- type VARCHAR(50) NOT NULL
- message TEXT NOT NULL
- is_read BOOLEAN DEFAULT FALSE
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

### Table: alerts
- id SERIAL PRIMARY KEY
- user_id INTEGER REFERENCES users(id)
- type VARCHAR(50) NOT NULL -- e.g., 'budget_exceeded', 'goal_reached'
- parameters JSONB
- is_active BOOLEAN DEFAULT TRUE
- created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
- updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

## Indexes

- CREATE INDEX idx_transactions_user_id ON transactions(user_id);
- CREATE INDEX idx_transactions_date ON transactions(date);
- CREATE INDEX idx_transactions_type ON transactions(type);
- CREATE INDEX idx_transaction_categories_transaction_id ON transaction_categories(transaction_id);
- CREATE INDEX idx_transaction_categories_category_id ON transaction_categories(category_id);
- CREATE INDEX idx_transaction_tags_transaction_id ON transaction_tags(transaction_id);
- CREATE INDEX idx_transaction_tags_tag_id ON transaction_tags(tag_id);
- CREATE INDEX idx_budgets_user_id ON budgets(user_id);
- CREATE INDEX idx_budgets_date_range ON budgets(start_date, end_date);
- CREATE INDEX idx_recurring_transactions_user_id ON recurring_transactions(user_id);
- CREATE INDEX idx_recurring_transactions_next_occurrence ON recurring_transactions(start_date);
- CREATE INDEX idx_attachments_user_id ON attachments(user_id);
- CREATE INDEX idx_financial_goals_user_id ON financial_goals(user_id);
- CREATE INDEX idx_shared_expenses_shared_by_user_id ON shared_expenses(shared_by_user_id);
- CREATE INDEX idx_shared_expenses_shared_with_user_id ON shared_expenses(shared_with_user_id);
- CREATE INDEX idx_notifications_user_id ON notifications(user_id);
- CREATE INDEX idx_alerts_user_id ON alerts(user_id);
