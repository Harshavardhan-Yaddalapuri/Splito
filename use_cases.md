# Use Cases for Splito Web Application

## 1. User Registration Use Case

### Overview
- **Actor**: New User
- **Goal**: Create a new account in the Splito system

### Preconditions
- User has accessed the Splito website
- User does not have an existing account

### Main Flow
1. User initiates account creation
2. System presents registration form
3. User enters:
    - Email address
    - Password
    - Password confirmation
4. User agrees to terms of service
5. System validates input:
    - Email format
    - Password complexity
    - Email uniqueness
6. System creates user account
7. System generates verification token
8. System sends verification email
9. User verifies email
10. System activates account
11. System redirects to login page

### Alternative Flows
- Invalid email format
- Weak password
- Email already registered
- Verification token expiration

## 2. User Login Use Case

### Overview
- **Actor**: Registered User
- **Goal**: Authenticate and access the Splito system

### Preconditions
- User has a verified account
- User is on the login page

### Main Flow
1. User enters email and password
2. System validates credentials
3. System generates authentication token
4. System establishes user session
5. System redirects to user dashboard

### Alternative Flows
- Invalid credentials
- Account locked after multiple failed attempts
- Password reset required

## 3. Adding an Expense Use Case

### Overview
- **Actor**: Logged-in User
- **Goal**: Record a new expense in the system

### Preconditions
- User is authenticated
- User is on the expense entry page

### Main Flow
1. User selects "Add Expense"
2. User enters expense details:
    - Amount
    - Date
    - Category
    - Description
    - Payment method
3. Optional: Attach receipt image
4. System validates expense entry
5. System saves expense to database
6. System updates user dashboard
7. System provides success confirmation

### Alternative Flows
- Invalid expense amount
- Future date selected
- Category not found
- Receipt upload failure

## 4. Generating Expense Report Use Case

### Overview
- **Actor**: Logged-in User
- **Goal**: Generate a comprehensive expense report

### Preconditions
- User has recorded expenses
- User is on the reports section

### Main Flow
1. User selects report generation
2. User configures report parameters:
    - Date range
    - Categories
    - Report format
3. System queries expense database
4. System generates report
5. System provides report download
6. User downloads report

### Alternative Flows
- No expenses in selected range
- Report generation error
- Large data set processing

## 5. Setting Budget Use Case

### Overview
- **Actor**: Logged-in User
- **Goal**: Create a new budget in the system

### Preconditions
- User is authenticated
- User is on budget management page

### Main Flow
1. User initiates budget creation
2. User enters budget details:
    - Budget name
    - Amount
    - Time period
    - Start date
    - Optional: Category
3. System validates budget parameters
4. System saves budget
5. System updates dashboard
6. System provides success confirmation

### Alternative Flows
- Invalid budget amount
- Past start date selected
- Category creation needed

## 6. Spending Analytics Use Case

### Overview
- **Actor**: Logged-in User
- **Goal**: Analyze and understand spending patterns

### Preconditions
- User has recorded expenses
- User is on analytics page

### Main Flow
1. System loads default analytics view
2. User interacts with analytics:
    - Change time periods
    - Switch chart types
    - Filter by categories
3. System dynamically updates visualizations
4. User explores detailed expense information

### Alternative Flows
- No expenses recorded
- Large dataset processing
- Visualization rendering issues
