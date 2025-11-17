# 📱 Digital Queue App (Android – Kotlin + Firebase)

This is my **3rd Year Computer Engineering assignment project** for Mobile App Development.  
It’s a simple **Digital Queue / Token Management System** where users can generate tokens and admins can control the queue.

---

## 🚀 Features

### 👤 User
- Register with Email + Password  
- Login  
- Generate Queue Token  
- View Token Status  
- Auto-redirect to User/Admin dashboard based on role  

### 🛠 Admin
- View list of all tokens  
- Call Next Token  
- Update Token Status  
- Real-time queue updates  

### 🔥 Firebase Services Used
- Firebase Authentication  
- Cloud Firestore  

---

## 📂 Project Structure

app/
├── ui/
│ ├── login/ → Login & Register
│ ├── splash/ → Splash Screen
│ ├── dashboard/ → User & Admin Dashboard
│ ├── queue/ → Token / Queue Screens
│
├── db/ → FirebaseRefs.kt
├── model/ → Models (User, Token)



---

## 🛠 Technologies Used
- Kotlin  
- Android XML  
- Firebase Auth  
- Firebase Firestore  
- ViewBinding  

---

## ▶️ How to Run the App

1. Clone or download the project  
2. Open it in Android Studio  
3. Add your Firebase config file inside: app/google-services.json
4. In Firebase Console:
   - Enable **Email/Password Authentication**
   - Create **Firestore Database**
5. Sync Gradle  
6. Run the app on emulator or Android device  

---

## 🗄 Firestore Collections

### `users`
```json
{
  "name": "User Name",
  "email": "example@gmail.com",
  "role": "user" or "admin"
}
```
```tokens
{
  "userId": "UID123",
  "tokenString": "A101",
  "status": "waiting / serving / completed"
}
```


<img width="344" height="767" alt="Screenshot 2025-11-17 204133" src="https://github.com/user-attachments/assets/e8354e35-bda7-4bd7-a632-89708083c037" />
<img width="364" height="754" alt="Screenshot 2025-11-17 203748" src="https://github.com/user-attachments/assets/c17656f0-b2e3-4c45-ae5f-cd2c28c8826e" />
<img width="352" height="748" alt="Screenshot 2025-11-17 203825" src="https://github.com/user-attachments/assets/8d235e46-055b-43e6-9f3b-654a135a9e12" />
<img width="367" height="771" alt="Screenshot 2025-11-17 203918" src="https://github.com/user-attachments/assets/714e579e-7aa8-4e68-ad9f-b9e5636f1160" />
<img width="345" height="768" alt="Screenshot 2025-11-17 204202" src="https://github.com/user-attachments/assets/60948785-2e93-4afb-ad2e-000f73a8d131" />
<img width="362" height="761" alt="Screenshot 2025-11-17 204216" src="https://github.com/user-attachments/assets/185ecce9-6704-46fb-a262-b192484ee964" />


