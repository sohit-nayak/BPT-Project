import MySQLdb

# conn = MySQLdb.connect(host= "vinaysammangi.esy.es",
#                   user="u162811053_sn",
#                   passwd="password",
#                   db="u162811053_wbgt")
# x = conn.cursor()
# conn.close()

# import pyodbc
# cnxn = pyodbc.connect('DRIVER={SQL Server};SERVER=31.220.104.105;DATABASE=data;UID=user;PWD=password45')
# cursor = cnxn.cursor()

# cursor.execute("SELECT * FROM users")
# tables = cursor.fetchall()
# #cursor.execute("SELECT WORK_ORDER.TYPE,WORK_ORDER.STATUS, WORK_ORDER.BASE_ID, WORK_ORDER.LOT_ID FROM WORK_ORDER")

# for row in cursor.columns(table='WORK_ORDER'):
#     print row.column_name
#     for field in row:
#         print field


import mysql.connector
from mysql.connector import errorcode

config = {
  'user': 'u162811053_sn',
  'password': 'password',
  'host': 'vinaysammangi.esy.es',
  'database': 'u162811053_wbgt',
  'raise_on_warnings': True,
}
try:
  cnx = mysql.connector.connect(**config)
except mysql.connector.Error as err:
  if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
    print("Something is wrong with your user name or password")
  elif err.errno == errorcode.ER_BAD_DB_ERROR:
    print("Database does not exist")
  else:
    print(err)
else:
  cnx.close()
  print 'hey'