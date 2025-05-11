from flask import Flask, request
from flask_cors import CORS
import json
from datetime import datetime
from termcolor import colored

app = Flask(__name__)
CORS(app)

# default prefix
DEFAULT_PREFIXES = [
    "com.android.",
    "com.google.android.",
    "android.",
    "com.qualcomm.",
    "com.miui.",
    "com.samsung.",
    "com.motorola.",
    "com.huawei.",
    "com.mediatek.",
    "com.google.",
    "android",
    "com.mi.",
    "com.xiaomi.",
    "com.android.internal.",
    "com.android.systemui",
    "com.sec"
]

def is_default(package_name):
    return any(package_name.startswith(prefix) for prefix in DEFAULT_PREFIXES)

@app.route('/packages', methods=['POST'])
def packages():
    data = request.get_json(force=True)

    device_id = data.get("id", "unknown_id")
    packages = data.get("packages", [])

    print(f"\n[Device ID]: {device_id}")
    print("[+] Packages received:")

    default_apps = []
    user_apps = []

    for pkg in packages:
        if is_default(pkg):
            print(colored(f"  [SYSTEM]  {pkg}", "green"))
            default_apps.append(pkg)
        else:
            print(colored(f"  [USER]    {pkg}", "red"))
            user_apps.append(pkg)

    # save files
    with open(f"{device_id}_defaultApps.txt", "w") as f:
        for app in default_apps:
            f.write(app + "\n")

    with open(f"{device_id}_installedApps.txt", "w") as f:
        for app in user_apps:
            f.write(app + "\n")

    print(colored(f"\nSaved files for ID: {device_id}", "cyan"))
    return '', 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=1337)
