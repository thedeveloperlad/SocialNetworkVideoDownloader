## 🚀 JavaFX Troubleshooting, Setup, and Deployment Guide

This project is a **JavaFX** application built manually (without Maven or Gradle). Because JavaFX components are no longer bundled with standard Java distributions (Java 11 and newer), you might encounter the following error when running the application for the first time:
> *`Error: JavaFX runtime components are missing, and are required to run this application`*

Below are the solutions to fix this error in your development environment, followed by instructions on how to package and run the final application.

---

### 🔧 Fixing the Missing Runtime Components Error

You can choose one of the following two methods to resolve this issue in your local IDE:

#### Method 1: Use a JDK with Built-in JavaFX (Easiest)
The fastest way to fix the issue is to replace your current Java environment with one that already includes JavaFX.
1. Download and install the **JDK FX** version from distributors like [Azul Zulu JDK FX](https://azul.com) or [BellSoft Liberica JDK (Full Version)](https://bell-sw.com).
2. Change your IDE project settings to point to this new JDK. The application will compile and run instantly without any extra configurations.

#### Method 2: Manually Add VM Options to Your IDE
If you prefer to keep using a standard JDK, you must download the standalone [Gluon JavaFX SDK](https://gluonhq.com) and explicitly tell the Java Virtual Machine (JVM) where the libraries are located using VM arguments.

* **In IntelliJ IDEA:**
  1. Go to the top menu and select **Run** > **Edit Configurations...**.
  2. Select your application entry configuration and click **Modify options** > **Add VM options**.
  3. Paste the following line (replace `/path/to/javafx-sdk/lib` with the actual path to your downloaded JavaFX `lib` folder):
     ```bash
     --module-path "/path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
     ```
* **In VS Code:**
  1. Open your project's `.vscode/launch.json` file.
  2. Add the `vmArgs` property inside your launch configuration block:
     ```json
     "vmArgs": "--module-path \"/path/to/javafx-sdk/lib\" --add-modules javafx.controls,javafx.fxml"
     ```

---

### 📦 Building a Runnable JAR File

This project includes a custom `Launcher.java` class as an alternative entry point. This class triggers the JavaFX lifecycle without requiring global VM options, making it perfect for distributing a standalone `.jar` file. 

When exporting the project, you **must** set the application's entry point to the `Launcher` class instead of the main GUI class.

#### In IntelliJ IDEA:
1. Go to **File** > **Project Structure** > **Artifacts**.
2. Click the green **`+`** icon, select **JAR**, and choose **From modules with dependencies...**.
3. In the **Main Class** field, browse and select your **`Launcher`** class.
4. Click **Apply** and **OK**.
5. To generate the file, go to the top menu and select **Build** > **Build Artifacts...** > **Build**.

#### In Eclipse:
1. Right-click on your project and select **Export...**.
2. Expand the **Java** folder and select **Runnable JAR file**, then click **Next**.
3. Under **Launch configuration**, select the configuration that targets your **`Launcher`** class.
4. Choose your export destination and click **Finish**.

---

### 💻 Running the Packaged JAR (For Users)

Since JavaFX remains decoupled from standard Java Runtimes on the user's end, final users can launch your compiled `.jar` file using one of these options:

#### Option A: Using an FX-Bundled Environment (Recommended)
If the user installs an FX-bundled runtime (like **Azul Zulu FX** or **BellSoft Liberica Full** as mentioned in Method 1), they can open your application with a simple **double-click**.

#### Option B: Launching via Command Line
If using a standard JDK distribution, place the JavaFX SDK `lib` folder in the same directory as your `.jar` file and execute the following command in the terminal:

```bash
java --module-path "./javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -jar YourApplicationName.jar
```
