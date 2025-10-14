c0 = int(input("Adj meg egy  szamot! "))
i = 1
while c0 != 1:
    if c0 % 2 == 0:
        c0 = c0 // 2
else:
    c0 = 3*c0+1
print(f"{i}. lepes : c0 = {c0}")
