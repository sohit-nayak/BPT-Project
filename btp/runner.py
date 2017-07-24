# import the necessary packages
import numpy as np
import argparse
import imutils
import glob
import cv2
import time
from mss import mss
from PIL import Image
import pyautogui
import time
import MySQLdb

# template of all buttons
start_btn = cv2.imread('start_button.jpg')
end_btn = cv2.imread('end_button.jpg')
save_btn = cv2.imread('save_button.jpg')

mon = {'top': 0, 'left': 0, 'width': 1366, 'height': 767}

sct = mss()

# database connection

def detect_and_click(image, gray):
	found = None

	(tH, tW) = gray.shape[:2]

	for scale in np.linspace(.2, 1.0, 20)[::-1]:
		resized = imutils.resize(gray, width=int(gray.shape[1]*scale))
		r = gray.shape[1] / float(resized.shape[1])

		if resized.shape[1] < tW or resized.shape[0] < tH:
			break

		edged = cv2.Canny(resized, 50, 200)
		result = cv2.matchTemplate(edged, image, cv2.TM_CCOEFF)
		(_, maxVal, _, maxLoc) = cv2.minMaxLoc(result)

		if found is None or maxVal > found[0]:
			found = (maxVal, maxLoc, r)

	(_, maxLoc, r) = found
	(startX, startY) = (int(maxLoc[0] * r), int(maxLoc[1] * r))
	(endX, endY) = (int((maxLoc[0] + tW) * r), int((maxLoc[1] + tH) * r))

	# draw a bounding box around the detected result and display the image
	# cv2.rectangle(image_r, (startX, startY), (endX, endY), (0, 0, 255), 2)
	xPos = int((startX + endX) / 2)
	yPos = int((startY + endY) / 2)
	x, y = pyautogui.position()
	positionStr = 'X: ' + str(x).rjust(4) + ' Y: ' + str(y).rjust(4)
	print positionStr
	pyautogui.click(xPos, yPos)

# parser of "wbgt.txt"
def parser():

	with open("wbgt.txt", "r") as f:
		lines = f.read().split('\n')
		lines = lines[1:]
		wbgt = []
		for line in lines:
			list_of = line.split()
			try:
				wbgt.append(float(list_of[3]))
			except:
				print 'line does\'nt exist'
		print sum(wbgt) / len(wbgt)


while 1:
	sct.get_pixels(mon)
	img = Image.frombytes('RGB', (sct.width, sct.height), sct.image)
	image_r = np.array(img)
	gray = cv2.cvtColor(image_r, cv2.COLOR_BGR2GRAY)

	# clicking the start button
	detect_and_click(start_btn, gray)
	time.sleep(55)

	# clicking the end button
	detect_and_click(end_btn, gray)

	#clicking the save button
	detect_and_click(save_btn, gray)
	pyautogui.typewrite("wbgt.txt")
	pyautogui.press("enter")

	#parse and send data
	parser()

	# conn = MySQLdb.connect(host= "http://sql43.hostinger.in/phpmyadmin/index.php?db=u162811053_data&token=e1541929764be5d499e9aa570f0de7be",
 #                  user="root",
 #                  passwd="newpassword",
 #                  db="engy1")
	# x = conn.cursor()
