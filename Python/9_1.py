import errno
db = 0
for name, value in errno.errorcode.items():
 print(f"{name} → {value}")
 db += 1
print("Összesen: ", db, "db errno konstns van.")