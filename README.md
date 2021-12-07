## 環境說明
* Android Studio / tools.build:gradle ：4.1.3
* gradlew：gradle-6.5-bin.zip
* target version: 30 , mini version: 24
* 各個 library 版本整理於 dependency.gradle 檔案

## MVP 架構
* 單一 Activity 搭載多 Fragment 
* Library: 
  * Net: Retrofit, OkHttp,
  * Database: Room, SharedPreferences
  * UI: FragNav,
  * 其他: RxJava, Klog(多功能Log), Glide(圖片切割,讀取網路圖片)
  
## 功能說明
* Model:
  * MovieBean: 儲存 API 回傳的影片資料
  * MovieImgBean: 儲存圖片的 File 路徑，供 ImageView 使用
* 離線模式： 
  * 連線時抓取資料存於 Room ，並設定五分鐘的逾期時間。
  * 後續判斷 Room 是否有資料及是否逾期，決定是否重新取網路資料。
  * 以 Glide 下載圖片儲存於 File ，逾期清空。
* 列表頁面：搜尋、Success/Empty/Error判斷、連線/離線模式
* 細節頁面：離線模式、Success/Error判斷

