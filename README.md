# AppsRecon

**AppsRecon** is a lightweight Android reconnaissance tool designed to enumerate installed apps on a target device and send them to a remote Flask server for analysis. Ideal for red teaming, CTFs, and controlled offensive environments.

---

## Features

- Retrieves all installed package names from the device.
- Identifies and separates system (default) apps from user-installed apps.
- Sends results to a remote Flask server over HTTP.
- Includes unique device ID for per-device tracking.
- Flask server logs apps in color-coded format and stores them in separate files.

---

## Setup

### Android (Client)

1. Clone the project and open it in Android Studio.
2. Update the Flask server IP in `SendPackages.kt`:
   ```kotlin
   .baseUrl("http://<YOUR-IP>:1337/")
   ```
3. Build and run the APK on a real device or emulator (make sure it has the QUERY_ALL_PACKAGES permission).
> If using HTTP, ensure networkSecurityConfig allows cleartext traffic.

### Flask (Server)

1. Install dependencies:
```bash
pip3 install -r requirements.txt
```

2. Run the server:
```bash
python3 server.py
```

### Output (on server)

The Flask server will:
	- Print all received packages.
	- Color system apps in green, user-installed in red.
	- Save results to:
		- `<device_id>_defaultApps.txt`
		- `<device_id>_installedApps.txt`

## Example Payload

```json
{
  "id": "ea94a4c1f08b123f",
  "packages": [
    "com.android.settings",
    "com.whatsapp",
    "com.termux"
  ]
}
```

## Disclaimer

This tool is intended only for educational purposes, research, and ethical hacking in controlled environments. The author is not responsible for any misuse.
