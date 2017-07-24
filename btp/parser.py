# parser of "wbgt.txt"
def main():

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

if __name__ == '__main__':
	main()