# Motorcycle Catalogue
Application to show motorcycles list and buy motorcycles

(Usually for best practice and security purpose we need to hide and ignore to push google-services.json <br />
that we get from Firebase because it's dangerous to push it to Version Control (because it contains alot of privacy and dangerous files to upload).<br />
But for the test and to make it easier to run the application I have included google-services.json to the BitBucket)<br />
<br />
To run the application, you must use Android Studio and clone this repository:<br />
- Clone the repository using git clone (git clone https://[your_user_name]@bitbucket.org/motorcyclecatalogue/motorcyclecatalogue.git or you can just download the zip file<br />
- Open the application / repository using Android Studio<br />
- You can run using emulator or with Android phone (ADB must be enabled first), you can also build apk to test the application (Build - Build Bundle(s) / APK(s) - Build APK(s))<br />

## **Tech Stack Used**
- Kotlin<br />
- Room for Local Database<br />
- Dependency Injection (Hilt)<br />
- Firebase (Authentication, Firestore, and Storage)<br />
- Flow<br />
- LiveData<br />
- Glide (for loading picture online)<br />

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
### **Example Narrative**
```
As a customer I want to be able to edit my profile data and profile picture
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer edit profile
The customer can edit profile data (fullname, phone number, address, virtual account) and profile picture
```

### **Story: As a User, I can see motorcycle catalogue with motorcycle thumbnail**
### **Example Narrative**
```
As a customer I want to be able to see motorcycle list with the thumbnail
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer has logged in
The customer can see motorcycle list with motorcycle name and price
```

### **Story: As a User, I can see the details of motorcycle**
### **Example Narrative**
```
As a customer I want to be able to look at motorcycle details on click
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer click one of the motorcycle list
The customer can look at the detail of motorcycle and also be able to order from the details
```
### **Story: As a User, I can see motorcycle gallery**
### **Example Narrative**
```
As a customer I want to be able to look at motorcycle gallery on detail page
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer click on the list
The customer can also look at motorcycle gallery on scroll
```

<img src="https://drive.google.com/uc?id=1VsQrsACxBwLcJBn3U_CZ0YI1sa4jHOC4" width="550" height="550">

### **Story: As a User, I can order a motorcycle product (with notification)**
### **Example Narrative**
```
As a customer I want to be able to order and notified that the order is successful
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer order the motorcycle
The customer can be notified when the order is successful and being processed
```

### **Story: As a User, I can see my orders**
### **Example Narrative**
```
As a customer I want to be able to look at the items that I ordered
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer has buy the motorcycle
The customer can look at the orders and order details
```

### **Story: As a User, I can cancel my orders (with notification)**
### **Example Narrative**
```
As a customer I want to be able to cancel the order before it was send to my address
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer has order the motorcycle
The customer can cancel the order if the order is still being processed
```
