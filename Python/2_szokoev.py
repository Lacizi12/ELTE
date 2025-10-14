ev = int(input("Adj meg egy evet"))
if ev % 4 != 0:
    print("normal")
elif ev % 100 != 0:
    print("szokoev")
elif ev % 400 != 0:
    print("normal")
else:
    print("szokoev")