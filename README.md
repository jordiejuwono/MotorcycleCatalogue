# Motorcycle Catalogue
Application to show motorcycles list and buy motorcycles

(Usually for best practice and security purpose we need to hide and ignore to push google-services.json that we get from Firebase because it's dangerous to push it to Version Control (because it contains alot of privacy and dangerous files to upload).
But for the test and to make it easier to run the application I have included google-services.json to the BitBucket)

To run the application, you must use Android Studio and clone this repository:
- Clone the repository using git clone (git clone https://[your_user_name]@bitbucket.org/motorcyclecatalogue/motorcyclecatalogue.git or you can just download the zip file
- Open the application / repository using Android Studio
- You can run using emulator or with Android phone (ADB must be enabled first), you can also build apk to test the application (Build - Build Bundle(s) / APK(s) - Build APK(s))

## **Motorcycle Catalogue Feature Specs**
### **Story: As a User, I can Register**

### **Example Narrative**
```
As a customer I want to be able to register using email and password
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer register using email and password
When the customer enter invalid email and password
Then the app should notify the validation error for email or password
```

### **Story: As a User, I can Login**

### **Example Narrative**
```
As a customer I want to be able to login using email and password that I registered
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer login using email and password
When the customer enter invalid email and password
Then the app should show the error
```

### **Story: As a User, I can edit my profile including my profile picture**

### **Story: As a User, I can see motorcycle catalogue with motorcycle thumbnail**

### **Story: As a User, I can see the details of motorcycle**

### **Story: As a User, I can see motorcycle gallery**

### **Story: As a User, I can order a motorcycle product (with notification)**

### **Story: As a User, I can see my orders**

### **Story: As a User, I can cancel my orders (with notification)**
