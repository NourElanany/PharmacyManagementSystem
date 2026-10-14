import os
import time
from selenium import webdriver
from selenium.webdriver.edge.options import Options

out_dir = r"D:\PharmacyManagementSystem\screenshots"
os.makedirs(out_dir, exist_ok=True)

options = Options()
options.add_argument("--headless=new")
options.add_argument("--disable-gpu")
options.add_argument("--window-size=1500,980")

driver = webdriver.Edge(options=options)

try:
    file_url = "file:///D:/PharmacyManagementSystem/dashboard/clean_ui.html"
    driver.get(file_url)
    time.sleep(1)

    tabs = [
        ("dashboard", "01_executive_dashboard_clean.png"),
        ("inventory", "02_pharmaceutical_inventory_clean.png"),
        ("pos", "03_pos_billing_clean.png"),
        ("alerts", "04_safety_expiry_alerts_clean.png"),
        ("staff", "05_staff_roles_clean.png")
    ]

    for tab_id, filename in tabs:
        driver.execute_script(f"switchTab('{tab_id}');")
        time.sleep(0.4)
        target = os.path.join(out_dir, filename)
        driver.save_screenshot(target)
        print(f"Captured: {filename}")

    print("ALL_CLEAN_SCREENSHOTS_CAPTURED")
finally:
    driver.quit()
