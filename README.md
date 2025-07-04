# E-commerce Backend API

A complete Spring Boot e-commerce backend system with 10 entities and 12 REST API endpoints. This project provides all the essential features needed to build a modern online shopping platform.

## 🚀 Project Overview

This is a **production-ready** Spring Boot e-commerce API system that contains exactly **12 APIs** and **10 database entities**. It's designed to be simple, secure, and scalable for building online shopping applications.

### Key Features
- ✅ User registration and authentication
- ✅ Product catalog management
- ✅ Shopping cart functionality
- ✅ Order processing system
- ✅ Payment processing framework
- ✅ Product review and rating system
- ✅ Admin panel for product management
- ✅ Role-based security (USER/ADMIN)

## 📋 Technical Specifications

| Component | Technology | Version |
|-----------|------------|---------|
| Framework | Spring Boot | 3.1.0 |
| Language | Java | 17+ |
| Database | MySQL | 8.0+ |
| Build Tool | Maven | 3.6+ |
| Security | Spring Security | 6.1.0 |

## 🗃️ Database Schema (10 Entities)

### Core Business Entities
1. **User** - Customer account management with roles
2. **Product** - Product catalog with pricing and inventory
3. **Category** - Product categorization system
4. **Cart** - Shopping cart session management
5. **CartItem** - Individual items within shopping carts

### Transaction Entities
6. **Order** - Order processing and lifecycle management
7. **OrderItem** - Detailed order item information
8. **Payment** - Payment processing with multiple methods

### Supporting Entities
9. **Address** - Customer shipping and billing addresses
10. **Review** - Product reviews and rating system

## 🔗 API Endpoints (12 Total)

### 👤 User Management (2 APIs)
- `POST /api/users/register` - Create new customer account
- `POST /api/users/login` - User authentication and login

### 📦 Product Management (3 APIs)
- `GET /api/products` - List all products with filtering
- `GET /api/products/{id}` - Get specific product details
- `POST /api/products` - Add new product (Admin only)

### 🛒 Shopping Cart (3 APIs)
- `POST /api/cart/add` - Add item to shopping cart
- `GET /api/cart/{userId}` - View user's current cart
- `DELETE /api/cart/item/{itemId}` - Remove item from cart

### 📋 Order Management (2 APIs)
- `POST /api/orders` - Create new order from cart
- `GET /api/orders/{userId}` - View user's order history

### 💳 Payment & Categories (2 APIs)
- `POST /api/payments` - Process payment transactions
- `GET /api/categories` - List all product categories

## 🛡️ Security Configuration

### In-Memory Authentication
- **Admin User**: `admin` / `password2` (ADMIN role)
- **Regular User**: `user` / `password1` (USER role)

### Route Protection
- **Public Access**: `/public/**`, `/swagger-ui/**`, `/v3/api-docs/**`
- **Admin Only**: `/admin/**` routes
- **User/Admin**: `/user/**` routes
- **Authentication Required**: All other endpoints

## ⚙️ Setup Instructions

### Prerequisites
```bash
✅ Java 17 or higher
✅ Maven 3.6+
✅ MySQL 8.0+
✅ IntelliJ IDEA or VS Code (recommended)
```

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd ecommerce-backend
```

### 2. Database Setup
```sql
-- Create MySQL database
CREATE DATABASE ecommerce_refined_db;

-- Optional: Create dedicated user
CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'ecommerce_password';
GRANT ALL PRIVILEGES ON ecommerce_refined_db.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure Database Connection
Update `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_refined_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password

# Server Configuration
server.port=8090
```

### 4. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Using IDE
# Run EcommerceApplication.java main method
```

### 5. Verify Installation
- **Application URL**: http://localhost:8090
- **API Documentation**: http://localhost:8090/swagger-ui.html
- **Health Check**: http://localhost:8090/actuator/health

## 📝 API Usage Examples

### Register New User
```bash
POST http://localhost:8090/api/users/register
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "password": "password123",
    "phone": "01700000000"
}
```

### Add Product (Admin)
```bash
POST http://localhost:8090/api/products
Content-Type: application/json
Authorization: Basic admin:password2

{
    "name": "iPhone 15 Pro",
    "description": "Latest Apple smartphone with A17 Pro chip",
    "price": 135000.00,
    "stockQuantity": 50,
    "categoryId": 1
}
```

### Add Item to Cart
```bash
POST http://localhost:8090/api/cart/add
Content-Type: application/json

{
    "userId": 1,
    "productId": 1,
    "quantity": 2
}
```

### Create Order
```bash
POST http://localhost:8090/api/orders
Content-Type: application/json

{
    "userId": 1,
    "shippingAddress": "123 Main Street, Dhaka, Bangladesh"
}
```

## 🏗️ Project Structure

```
ecommerce-backend/
├── src/main/java/com/ecommerce/
│   ├── entity/                     # 10 JPA Entity Classes
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── Payment.java
│   │   ├── Address.java
│   │   └── Review.java
│   ├── repository/                # JPA Repository Interfaces
│   ├── service/                   # Business Logic Layer
│   ├── controller/                # REST API Controllers
│   ├── config/                    # Configuration Classes
│   │   └── SecurityConfig.java    # Security Configuration
│   └── EcommerceApplication.java  # Main Application Class
├── src/main/resources/
│   └── application.properties     # Application Configuration
├── pom.xml                       # Maven Dependencies
└── README.md                     # This file
```

## 🔧 Development Features

### Hot Reload
```properties
# Enabled by default with Spring Boot DevTools
spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true
```

### Database Auto-Creation
```properties
# Tables are automatically created from JPA entities
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### API Documentation
- **Swagger UI**: http://localhost:8090/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8090/v3/api-docs

## 🧪 Testing

### Using Postman
1. Import the API collection (if available)
2. Set base URL to `http://localhost:8090`
3. Use basic authentication for protected endpoints:
   - Admin: `admin` / `password2`
   - User: `user` / `password1`

### Sample Test Flow
1. Register a new user
2. Login with the user credentials
3. Add some products (as admin)
4. Add products to cart
5. Create an order
6. Process payment

## 📊 Database Relationships

```
User (1) ←→ (N) Cart
User (1) ←→ (N) Order
User (1) ←→ (N) Address
User (1) ←→ (N) Review

Product (1) ←→ (N) CartItem
Product (1) ←→ (N) OrderItem
Product (1) ←→ (N) Review
Product (N) ←→ (1) Category

Cart (1) ←→ (N) CartItem
Order (1) ←→ (N) OrderItem
Order (1) ←→ (1) Payment
```

## 🚀 Deployment

### Production Configuration
```properties
# Production database settings
spring.profiles.active=prod
spring.datasource.url=jdbc:mysql://production-host:3306/ecommerce_db
spring.jpa.hibernate.ddl-auto=validate
```

### Build for Production
```bash
# Create executable JAR
mvn clean package

# Run production JAR
java -jar target/ecommerce-backend-1.0.0.jar
```

## 🔍 Troubleshooting

### Common Issues

**Database Connection Error**
```bash
# Check MySQL service is running
sudo systemctl status mysql

# Verify credentials in application.properties
```

**Port Already in Use**
```bash
# Change port in application.properties
server.port=8091
```

**CORS Issues**
```bash
# CORS is pre-configured for localhost
# Update SecurityConfig.java for production domains
```

## 📈 Performance Optimization

- **Connection Pooling**: HikariCP (default in Spring Boot)
- **JPA Optimization**: Lazy loading and query optimization
- **Caching**: Ready for Redis integration
- **Pagination**: Implemented in product listing

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact: afridibinhafiz9@gmail.com
- Documentation: [Project Wiki](wiki-link)

## 🎯 Future Enhancements

- [ ] JWT Token Authentication
- [ ] Email Notification System
- [ ] File Upload for Product Images
- [ ] Advanced Search and Filtering
- [ ] Inventory Management System
- [ ] Analytics and Reporting
- [ ] Multi-language Support
- [ ] Payment Gateway Integration

---

**Built with ❤️ using Spring Boot 3.1.0**
