<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Android AdMob Ads and Update Integration</title>
 
</head>
<body>
  <div class="container">
    <h1>Android AdMob Ads and Update Integration</h1>
    <p>This project demonstrates the integration of Google AdMob Ads, GDPR compliance using the User Messaging Platform (UMP), and in-app updates using the Play Core library in an Android application. The project also includes additional libraries for UI enhancements and app management.</p>
    <h2>Dependencies</h2>
    <p>Ensure the following dependencies are added to your project's <code>build.gradle</code> file:</p>
    <pre><code>implementation 'androidx.lifecycle:lifecycle-process:2.7.0'
implementation 'com.google.android.gms:play-services-ads:23.0.0'
implementation 'com.facebook.shimmer:shimmer:0.5.0'
implementation 'androidx.multidex:multidex:2.0.1'
implementation 'com.github.ybq:Android-SpinKit:1.4.0'
implementation 'com.google.android.ump:user-messaging-platform:2.2.0'
implementation 'com.google.android.play:app-update:2.1.0'</code></pre>
    <h2>Contributors</h2>
    <p class="contributors">Rameen Hashmi<br>Urooj Zafar</p>
    <h2>Getting Started</h2>
    <ol>
      <li>Clone the Repository: Clone this repository to your local machine using the following command:
        <pre><code>git clone https://github.com/your-username/android-admob-ads-update.git</code></pre>
      </li>
      <li>Import Project: Import the project into Android Studio by selecting File &gt; Open and choosing the cloned directory.</li>
      <li>Set Up AdMob Account:
        <ol type="a">
          <li>Sign in to your AdMob account.</li>
          <li>Create an AdMob app and obtain your AdMob App ID.</li>
          <li>Configure your AdMob Ad Unit IDs for banner ads, interstitial ads, and rewarded ads in the appropriate files (<code>strings.xml</code> or Java/Kotlin files).</li>
        </ol>
      </li>
      <li>Integrate GDPR and User Messaging Platform (UMP): Follow the Google Developer documentation to integrate GDPR compliance using UMP. Update the UMP consent status based on user interactions and regulatory requirements.</li>
      <li>Implement App Updates: Utilize the Play Core library to implement in-app updates. Follow the Play Core library documentation for integrating update checks and flows.</li>
      <li>Test Ads and Updates: During development, use test AdMob Ad Unit IDs and testing devices provided by Google. Test in-app update flows on different devices to ensure compatibility and functionality.</li>
      <li>Run the App: Build and run your Android app on a device or emulator to test AdMob Ads, GDPR compliance, and in-app updates integration.</li>
    </ol>
    <h2>Contributing</h2>
    <p>If you have any suggestions, improvements, or encounter issues, please create a GitHub issue or submit a pull request.</p>
    <h2>License</h2>
    <p class="license-info">This project is licensed under the MIT License - see the <a href="LICENSE">LICENSE</a> file for details.</p>
  </div>
</body>
</html>
