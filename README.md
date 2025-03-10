# PruebaAppInterrapidisimo

## Skills Used

- ✅ Clean Architecture  
- ✅ Dependency Injection  
- ✅ SOLID Principles  
- ✅ Local Database  
- ✅ State Management with MVVM  
- ✅ Sealed Classes  
- ✅ Unit Testing 


## Project Build & Dependencies

### Build Setup
This project is built using **Kotlin** and follows **modern Android development practices**. Below are the key build configurations.

### **Requirements**
- **Minimum SDK**: 24
- **Target SDK**: 35
- **Compile SDK**: 35
- **Java Version**: 11

### **Build Variants**
| Variant | Minify Enabled | Debuggable | BASE_URL |
|---------|--------------|------------|------------------------------|
| `debug` | ❌ No | ✅ Yes | `https://testing.interrapidisimo.co:8088` |
| `release` | ✅ Yes | ❌ No | `https://testing.interrapidisimo.co:8088` |


| Library                     | Version | Purpose                                |
|-----------------------------|---------|----------------------------------------|
| Navigation Component        | 2.7.1   | Manage navigation between fragments   |
| Retrofit                   | 2.9.0   | REST API calls                        |
| Dagger Hilt                | 2.48    | Dependency Injection                   |
| Room Database              | 2.6.1   | Local database storage                 |
| OkHttp Logging Interceptor | 4.3.1   | Log network requests/responses         |
| Material Components        | latest  | UI components                          |

## Testing

The application was tested successfully on the following devices:

| Device                  | Android Version | Test Result |
|-------------------------|----------------|-------------|
| Samsung Galaxy S21      | Android 13     | ✅ Passed   |
| Oppo Reno 11           | Android 14     | ✅ Passed   |
| Samsung Galaxy S8       | Android 9      | ✅ Passed   |

All tests were correct, and the app performed as expected on each device.


