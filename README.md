# Motorcycle Catalogue
Application to show motorcycles list and buy motorcycles

![app_summary](https://drive.google.com/uc?id=1U0aiqeSK7e2qbh8u1lXi-BtXH9KzyszZ)

### **Note :**
(Usually for best practice and security purpose we need to hide and ignore to push google-services.json that we get from Firebase because it's dangerous to push it to Version Control (because it contains alot of privacy and dangerous files to upload). But for the test and to make it easier to run the application I have included google-services.json to the BitBucket).
<br />
### **To run the application, you must use Android Studio and clone this repository:**
- Clone the repository using git clone (git clone https://[your_user_name]@bitbucket.org/motorcyclecatalogue/motorcyclecatalogue.git or you can just download the zip file<br />
- Open the application / repository using Android Studio<br />
- You can run using emulator (sometimes the notification doesn't show up on emulator and require to wipe the emulator data) or connect with your Android phone (Developer mode must be enabled first), you can also build apk to test the application (Build - Build Bundle(s) / APK(s) - Build APK(s)) and download it on your phone<br />
- If you use build APK then the APK will be located at your project directory - app - build - outputs - apk - debug - app.debug.apk<br />

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
**App Image**<br />
![app_register](https://drive.google.com/uc?id=1ybGmf-0wd30WbLY0rVExNze0LbisVZqS)

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
**App Image**<br />
![app_login](https://drive.google.com/uc?id=1Xs2C-TCmlVyVMwkSzJAWZCYX0PGclTGb)

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
**App Image**<br />
![app_edit_profile](https://drive.google.com/uc?id=1f5miWM9svx-pTwqXDj7yjB4SoWFlT-Qa)

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
**App Image**<br />
![app_motorcycle_list](https://drive.google.com/uc?id=1c6Tmdc0mxJdFn5Wf7M3En8kNXF03r3Bn)

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
**App Image**<br />
![app_details](https://drive.google.com/uc?id=1B9wo7UVidKhbUKxq__9D1sMedkYUhllu)

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
**App Image**<br />
![app_gallery](https://drive.google.com/uc?id=1VMwemFKXMvcY_G2vPkyeOb_rDV5Pg57o)

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
**App Image**<br />
![app_order](https://drive.google.com/uc?id=1j6K2iLfMHshcdmyhIemvAquEeQmBec2o)

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
**App Image**<br />
![app_transactions](https://drive.google.com/uc?id=1qZT-sAcCy4lfy6Wg79WgKEwtsrjxhT0P)

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
**App Image**<br />
![app_cancel](https://drive.google.com/uc?id=1dDympQTQzK383eer3Y67v5KxYbBSC11X)


## **Technical Summaries**
- When the application starts I use Room to pre-populate the local Database first so the list of motorcycles is ready when opening the application after Login Page.<br />
- I use Room Local Database for pre-populate the Database with motorcycle list, Wishlist feature (additional feature with CRUD functionality).<br />
- To seperate between business logic and presentation (UI) layer I use Dependency Injection (Hilt) with different module.
- I also implement the application with Firebase for Authentication (Register, Login), Firestore (for saving user data and order transactions), and Storage (saving user profile picture)).<br />


### **Additional Feature (Wishlist with CRUD Functionality)**
### **Example Narrative**
```
As a customer I want to be able to save motorcycle to wishlist so I can order it later
```
**Scenarios (Acceptance criteria)**<br />
```
Given the customer has save the motorcycle to wishlist
The customer can show the wishlist and also go to cart from the wishlist page
```
**App Image**<br />
![app_favorite](https://drive.google.com/uc?id=1m5isxvXFz3_Zt78ampuOSukyUd76xt-r)
