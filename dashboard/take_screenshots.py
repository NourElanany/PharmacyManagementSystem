import os
import time
from selenium import webdriver
from selenium.webdriver.edge.service import Service
from selenium.webdriver.edge.options import Options

output_dir = r"D:\PharmacyManagementSystem\screenshots"
os.makedirs(output_dir, exist_ok=True)

options = Options()
options.add_argument("--headless")
options.add_argument("--disable-gpu")
options.add_argument("--window-size=1400,900")

driver = webdriver.Edge(options=options)

try:
    file_url = "file:///D:/PharmacyManagementSystem/dashboard/index.html"
    driver.get(file_url)
    time.sleep(1)

    # 1. Dashboard Window
    driver.execute_script("switchWindow('dashboard');")
    time.sleep(0.5)
    driver.save_screenshot(os.path.join(output_dir, "01_dashboard_window.png"))
    print("Saved 01_dashboard_window.png")

    # 2. Inventory & Medicines Window
    driver.execute_script("switchWindow('inventory');")
    time.sleep(0.5)
    driver.save_screenshot(os.path.join(output_dir, "02_inventory_medicines_window.png"))
    print("Saved 02_inventory_medicines_window.png")

    # 3. Add Medicine Modal
    driver.execute_script("openAddMedicineModal();")
    time.sleep(0.5)
    driver.save_screenshot(os.path.join(output_dir, "03_add_medicine_modal_window.png"))
    print("Saved 03_add_medicine_modal_window.png")
    driver.execute_script("closeAddMedicineModal();")

    # 4. POS & Invoicing Window (with items added)
    driver.execute_script("switchWindow('pos'); addToCart(1); addToCart(3);")
    time.sleep(0.5)
    driver.save_screenshot(os.path.join(output_dir, "04_pos_invoicing_window.png"))
    print("Saved 04_pos_invoicing_window.png")

    # 5. Safety & Expiry Alerts Window
    driver.execute_script("switchWindow('alerts');")
    time.sleep(0.5)
    driver.save_screenshot(os.path.join(output_dir, "05_safety_and_expiry_alerts_window.png"))
    print("Saved 05_safety_and_expiry_alerts_window.png")

    # 6. Staff Management Window
    driver.execute_script("switchWindow('staff');")
    time.sleep(0.5)
    driver.save_screenshot(os.path.join(output_dir, "06_staff_management_window.png"))
    print("Saved 06_staff_management_window.png")

finally:
    driver.quit()
    print("All screenshots successfully captured!")
